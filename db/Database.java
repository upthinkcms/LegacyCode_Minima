// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.db;

import org.apache.commons.lang3.tuple.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Consumer;
import org.jdbi.v3.core.Handle;
import com.yojito.minima.util.MetricsUtil;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import org.postgresql.ds.PGSimpleDataSource;
import org.jdbi.v3.core.Jdbi;
import java.util.Properties;
import com.yojito.minima.api.Context;

public class Database
{
    public Jdbi start(final Context context, final Properties properties) {
        final String property = properties.getProperty("dbHost", "localhost");
        final String property2 = properties.getProperty("dbUser", "postgres");
        final String property3 = properties.getProperty("dbPassword", "postgres");
        final String property4 = properties.getProperty("dbName", "huddles");
        final String format = String.format("jdbc:postgresql://%s/%s", property, property4);
        final int int1 = Integer.parseInt(properties.getProperty("poolSize", "6"));
        final PGSimpleDataSource dataSource;
        (dataSource = new PGSimpleDataSource()).setUrl(format);
        dataSource.setUser(property2);
        dataSource.setPassword(property3);
        final HikariConfig hikariConfig;
        (hikariConfig = new HikariConfig()).setDataSource((DataSource)dataSource);
        hikariConfig.setMaximumPoolSize(int1);
        hikariConfig.setPoolName(property4 + "Pool");
        final HikariDataSource ds;
        final Jdbi create = Jdbi.create((DataSource)(ds = new HikariDataSource(hikariConfig)));
        context.get("dbMetricsUpdater").add(ds);
        return create;
    }
    
    public Jdbi start(final Context context, final String driver, final String url, final String dbUser, final String dbPassword) {
        final HikariConfig hikariConfig;
        (hikariConfig = new HikariConfig()).setUsername(dbUser);
        hikariConfig.setPassword(dbPassword);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setPoolName(dbUser + "Pool");
        hikariConfig.setMaximumPoolSize(6);
        final HikariDataSource ds;
        final Jdbi create = Jdbi.create((DataSource)(ds = new HikariDataSource(hikariConfig)));
        context.get("dbMetricsUpdater").add(ds);
        return create;
    }
    
    public Jdbi start(final Context context, final String driver, final String url, final String dbUser, final String dbPassword, final int poolSize) {
        final HikariConfig hikariConfig;
        (hikariConfig = new HikariConfig()).setUsername(dbUser);
        hikariConfig.setPassword(dbPassword);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setPoolName(dbUser + "Pool");
        hikariConfig.setMaximumPoolSize(poolSize);
        final HikariDataSource ds;
        final Jdbi create = Jdbi.create((DataSource)(ds = new HikariDataSource(hikariConfig)));
        context.get("dbMetricsUpdater").add(ds);
        return create;
    }
    
    public Handle startTx(final Jdbi dbInterface) {
        final Handle open;
        (open = dbInterface.open()).begin();
        return open;
    }
    
    public void endTx(final Handle handle) {
        handle.commit();
        handle.close();
    }
    
    public static void doWithinTx(Jdbi dbInterface, final Consumer<Handle> function) {
        var_0_04 = (Jdbi)dbInterface.open();
        try {
            ((Handle)var_0_04).begin();
            function.accept((Handle)var_0_04);
            ((Handle)var_0_04).commit();
            ((Handle)var_0_04).close();
        }
        catch (final Exception cause) {
            ((Handle)var_0_04).rollback();
            ((Handle)var_0_04).close();
            throw new RuntimeException(cause);
        }
    }
    
    public static void doWithinTx(final Handle dbHandle, final Consumer<Handle> function) {
        try {
            function.accept(dbHandle);
            dbHandle.commit();
            dbHandle.close();
        }
        catch (final Exception cause) {
            dbHandle.rollback();
            dbHandle.close();
            throw new RuntimeException(cause);
        }
    }
    
    public static <T> T withinTx(final Handle dbHandle, final Function<Handle, T> function) {
        try {
            final T apply = function.apply(dbHandle);
            dbHandle.commit();
            dbHandle.close();
            return apply;
        }
        catch (final Exception cause) {
            dbHandle.rollback();
            dbHandle.close();
            throw new RuntimeException(cause);
        }
    }
    
    public static <T> T withinTx(Jdbi dbInterface, final Function<Handle, T> function) {
        var_0_04 = (Jdbi)dbInterface.open();
        try {
            ((Handle)var_0_04).begin();
            final T apply = function.apply((Handle)var_0_04);
            ((Handle)var_0_04).commit();
            ((Handle)var_0_04).close();
            return apply;
        }
        catch (final Exception cause) {
            ((Handle)var_0_04).rollback();
            ((Handle)var_0_04).close();
            throw new RuntimeException(cause);
        }
    }
    
    public static <T> T withinTx(Jdbi dbInterface, final Function<Handle, T> function, final Consumer<List<Exception>> finallyConsume) {
        var_0_04 = (Jdbi)dbInterface.open();
        final ArrayList list = new ArrayList();
        try {
            ((Handle)var_0_04).begin();
            final T apply = function.apply((Handle)var_0_04);
            ((Handle)var_0_04).commit();
            ((Handle)var_0_04).close();
            return apply;
        }
        catch (final Exception cause) {
            list.add(cause);
            ((Handle)var_0_04).rollback();
            ((Handle)var_0_04).close();
            throw new RuntimeException(cause);
        }
        finally {
            finallyConsume.accept(list);
        }
    }
    
    public static <T> T withinTx(final Handle dbHandle, final Function<Handle, T> function, final Consumer<List<Exception>> finallyConsume) {
        final ArrayList list = new ArrayList();
        try {
            dbHandle.begin();
            final T apply = function.apply(dbHandle);
            dbHandle.commit();
            dbHandle.close();
            return apply;
        }
        catch (final Exception cause) {
            list.add(cause);
            dbHandle.rollback();
            dbHandle.close();
            throw new RuntimeException(cause);
        }
        finally {
            finallyConsume.accept(list);
        }
    }
    
    public static void withinTx2(Jdbi dbInterface, final Consumer<Handle> function, final Consumer<List<Exception>> finallyConsume) {
        var_0_04 = (Jdbi)dbInterface.open();
        final ArrayList list = new ArrayList();
        try {
            ((Handle)var_0_04).begin();
            function.accept((Handle)var_0_04);
            ((Handle)var_0_04).commit();
            ((Handle)var_0_04).close();
        }
        catch (final Exception cause) {
            list.add(cause);
            ((Handle)var_0_04).rollback();
            ((Handle)var_0_04).close();
            throw new RuntimeException(cause);
        }
        finally {
            finallyConsume.accept(list);
        }
    }
    
    public static <T> void withinTx3(Jdbi dbInterface, final Function<Handle, T> function, final Consumer<Pair<T, Throwable>> finallyConsume) {
        var_0_04 = (Jdbi)dbInterface.open();
        new ArrayList();
        Pair pair = null;
        try {
            ((Handle)var_0_04).begin();
            pair = Pair.of((Object)function.apply((Handle)var_0_04), (Object)null);
            ((Handle)var_0_04).commit();
            ((Handle)var_0_04).close();
        }
        catch (final Exception ex) {
            pair = Pair.of((Object)null, (Object)ex);
            ((Handle)var_0_04).rollback();
            ((Handle)var_0_04).close();
        }
        finally {
            finallyConsume.accept((Pair<T, Throwable>)pair);
        }
    }
}
