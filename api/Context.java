// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.api;

import java.util.function.ToDoubleFunction;
import io.micrometer.core.instrument.Gauge;
import java.util.concurrent.ForkJoinPool;
import java.lang.reflect.InvocationTargetException;
import com.yojito.minima.auth.cognito.CognitoAuthenticator;
import com.yojito.minima.netty.GsonHandler;
import java.util.concurrent.TimeUnit;
import com.yojito.minima.util.MetricsUtil;
import java.util.concurrent.Executors;
import io.micrometer.core.instrument.binder.jvm.JvmInfoMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;
import io.micrometer.graphite.GraphiteMeterRegistry;
import com.yojito.minima.util.MinimaGraphiteHierarchicalNameMapper;
import io.micrometer.core.instrument.Clock;
import io.micrometer.graphite.GraphiteConfig;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.BiFunction;
import com.zaxxer.hikari.HikariDataSource;
import java.util.List;
import com.yojito.minima.auth.cognito.AuthRole;
import com.yojito.minima.auth.domain.TokenValidation;
import com.yojito.minima.auth.Authenticator;
import com.yojito.minima.auth.cognito.Cognito;
import java.util.function.Supplier;
import java.util.Map;
import org.jdbi.v3.core.Jdbi;
import com.yojito.minima.db.Database;
import java.util.Properties;

public class Context
{
    private final Properties a;
    private Database b;
    private Jdbi c;
    private Map<String, Supplier> d;
    private Map<String, Object> e;
    private Cognito f;
    private Authenticator<TokenValidation, AuthRole> g;
    private List<HikariDataSource> h;
    
    public <T> void add(final String key, final Supplier<T> t) {
        this.d.put(key, t);
    }
    
    public <T> void addCached(final String key, final BiFunction<Properties, Context, T> fn) {
        this.e.put(key, fn.apply(this.a, this));
        this.d.put(key, () -> this.e.get(s));
    }
    
    public <T> void addCached(final String key, final Function<Context, T> fn) {
        this.e.put(key, fn.apply(this));
        this.d.put(key, () -> this.e.get(s));
    }
    
    public <T> T get(final String key) {
        final Supplier supplier;
        if ((supplier = this.d.get(key)) != null) {
            return (T)supplier.get();
        }
        return null;
    }
    
    public <T> Optional<T> getOpt(final String key) {
        final Supplier supplier;
        if ((supplier = this.d.get(key)) != null) {
            try {
                final Object value;
                if ((value = supplier.get()) != null) {
                    return Optional.of(value);
                }
                return Optional.empty();
            }
            catch (final NumberFormatException ex) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
    
    public <T> void declareProperty(final String key, final Class<T> klass) {
        this.declareProperty(key, key, klass);
    }
    
    public <T> void declareProperty(final String propertyKey, final String key, final Class<T> klass) {
        this.d.put(key, () -> {
            if (clazz.equals(String.class)) {
                return (Boolean)this.a.getProperty(key2);
            }
            else if (clazz.equals(Integer.class)) {
                return (Boolean)(Object)Integer.valueOf(Integer.parseInt(this.a.getProperty(key2)));
            }
            else if (clazz.equals(Integer.TYPE)) {
                return (Boolean)(Object)Integer.valueOf(Integer.parseInt(this.a.getProperty(key2)));
            }
            else if (clazz.equals(Float.class)) {
                return (Boolean)(Object)Float.valueOf(Float.parseFloat(this.a.getProperty(key2)));
            }
            else if (clazz.equals(Float.TYPE)) {
                return (Boolean)(Object)Float.valueOf(Float.parseFloat(this.a.getProperty(key2)));
            }
            else if (clazz.equals(Double.class)) {
                return (Boolean)(Object)Double.valueOf(Double.parseDouble(this.a.getProperty(key2)));
            }
            else if (clazz.equals(Float.TYPE)) {
                return (Boolean)(Object)Double.valueOf(Double.parseDouble(this.a.getProperty(key2)));
            }
            else if (clazz.equals(Boolean.class)) {
                return Boolean.valueOf(Boolean.parseBoolean(this.a.getProperty(key2)));
            }
            else if (clazz.equals(Float.TYPE)) {
                return Boolean.valueOf(Boolean.parseBoolean(this.a.getProperty(key2)));
            }
            else {
                return null;
            }
        });
    }
    
    public Context(Properties var_1_271) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        this.d = new HashMap<String, Supplier>();
        this.e = new HashMap<String, Object>();
        this.a = properties;
        this.h = new ArrayList<HikariDataSource>();
        this.declareProperty("dbEnabled", Boolean.class);
        this.declareProperty("authEnabled", Boolean.class);
        this.declareProperty("apiPackage", String.class);
        this.declareProperty("servicePackage", String.class);
        this.declareProperty("deployEnvironment", String.class);
        this.declareProperty("requestLogging", Boolean.class);
        System.setProperty("minima.corsAllowOrigin", properties.getProperty("corsAllowOrigin", "*"));
        this.declareProperty("appName", String.class);
        this.declareProperty("metricsReportingFrequency", Integer.class);
        this.declareProperty("metricsRegistry", String.class);
        this.declareProperty("graphiteHost", String.class);
        this.declareProperty("graphitePort", String.class);
        final Optional<Object> opt = this.getOpt("metricsReportingFrequency");
        final String s = this.get("metricsRegistry");
        final Optional<Object> opt2 = this.getOpt("graphiteHost");
        final Optional<Object> opt3 = this.getOpt("graphitePort");
        final String s2;
        if ((s2 = this.get("appName")) == null || s2.isEmpty()) {
            throw new RuntimeException("App Name is missing. Its required to store monitoring data");
        }
        if (s == null || s.isEmpty()) {
            throw new RuntimeException("MetricsRegistry Name is missing. Its required to store monitoring data");
        }
        Object registry;
        if ("simple".equals(s)) {
            registry = new SimpleMeterRegistry();
        }
        else {
            if (!"graphite".equals(s)) {
                throw new RuntimeException("Unsupported MetricsRegistry Name is specified. Allowed values - [simple, graphite]");
            }
            registry = new GraphiteMeterRegistry((GraphiteConfig)new GraphiteConfig(this) {
                public final String host() {
                    return opt2.orElse("localhost");
                }
                
                public final int port() {
                    return Integer.parseInt(opt3.orElse("2004"));
                }
                
                public final String get(final String k) {
                    return null;
                }
                
                public final boolean graphiteTagsEnabled() {
                    return false;
                }
            }, Clock.SYSTEM, (HierarchicalNameMapper)new MinimaGraphiteHierarchicalNameMapper(new String[] { "application" }));
        }
        ((MeterRegistry)registry).config().commonTags(new String[] { "application", s2 });
        this.add("meterRegistry", () -> meterRegistry);
        new JvmMemoryMetrics().bindTo((MeterRegistry)registry);
        new JvmGcMetrics().bindTo((MeterRegistry)registry);
        new JvmThreadMetrics().bindTo((MeterRegistry)registry);
        new JvmInfoMetrics().bindTo((MeterRegistry)registry);
        if (opt.orElse(-1) > 0) {
            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new MetricsUtil.MetricsPrinter((MeterRegistry)registry), 2L, opt.get(), TimeUnit.SECONDS);
        }
        GsonHandler.dontPrintResponse("_status");
        this.add("dbMetricsUpdater", () -> new MetricsUtil.DBConnectionMetricsUpdater(registry2, this.h));
        if (this.get("dbEnabled")) {
            this.b = new Database();
            this.c = this.b.start(this, properties);
        }
        if (this.get("authEnabled")) {
            this.declareProperty("authenticator", String.class);
            this.declareProperty("cognitoProfile", String.class);
            final String s3 = this.get("authenticator");
            if ("cognito".equals(s3)) {
                this.f = a(properties);
                this.g = new CognitoAuthenticator(this.f);
                return;
            }
            if (s3 == null || s3.isEmpty()) {
                throw new RuntimeException(String.format("Unsupported Authenticator Configuration : Missing value for authenticator", new Object[0]));
            }
            this.f = a(properties);
            var_1_271 = (Properties)Class.forName(s3);
            try {
                this.g = ((Class<Authenticator<TokenValidation, AuthRole>>)var_1_271).getConstructor(Context.class).newInstance(this);
            }
            catch (final NoSuchMethodException ex) {
                this.g = ((Class<Authenticator<TokenValidation, AuthRole>>)var_1_271).getConstructor(Cognito.class).newInstance(this.f);
            }
        }
    }
    
    private static Cognito a(final Properties properties) {
        final String property = properties.getProperty("clientId");
        final String property2 = properties.getProperty("userPoolId");
        final String property3 = properties.getProperty("idPoolId");
        final String property4 = properties.getProperty("region");
        final String property5 = properties.getProperty("groupRoleArn");
        final String property6;
        if ((property6 = properties.getProperty("cognitoProfile")) != null && !property6.isEmpty()) {
            return new Cognito(property, property2, property3, property4, property5, Optional.of(property6));
        }
        return new Cognito(property, property2, property3, property4, property5);
    }
    
    public void monitorForkJoinPool(final String name, final ForkJoinPool pool) {
        final MeterRegistry meterRegistry = this.get("meterRegistry");
        final ToDoubleFunction toDoubleFunction = forkJoinPool -> forkJoinPool.getActiveThreadCount();
        final ToDoubleFunction toDoubleFunction2 = forkJoinPool2 -> forkJoinPool2.getRunningThreadCount();
        final ToDoubleFunction toDoubleFunction3 = forkJoinPool3 -> forkJoinPool3.getQueuedSubmissionCount();
        final ToDoubleFunction toDoubleFunction4 = forkJoinPool4 -> (double)forkJoinPool4.getQueuedTaskCount();
        final ToDoubleFunction toDoubleFunction5 = forkJoinPool5 -> forkJoinPool5.getPoolSize();
        Gauge.builder(String.format("threadPool.%s.%s", name, "activeThreadCount"), (Object)pool, toDoubleFunction).description("Returns an estimate of the number of threads that are currently stealing or executing tasks. This method may overestimate the number of active threads").tags(new String[] { "threadPool", name }).register(meterRegistry);
        Gauge.builder(String.format("threadPool.%s.%s", name, "runningThreadCount"), (Object)pool, toDoubleFunction2).description("Returns an estimate of the number of worker threads that are not blocked waiting to join tasks or for other managed synchronization").tags(new String[] { "threadPool", name }).register(meterRegistry);
        Gauge.builder(String.format("threadPool.%s.%s", name, "queuedSubmissionCount"), (Object)pool, toDoubleFunction3).description("Returns an estimate of the number of tasks submitted to this pool that have not yet begun executing. This method may take time proportional to the number of submissions").tags(new String[] { "threadPool", name }).register(meterRegistry);
        Gauge.builder(String.format("threadPool.%s.%s", name, "queuedTaskCount"), (Object)pool, toDoubleFunction4).description("Returns an estimate of the total number of tasks currently held in queues by worker threads (but not including tasks submitted to the pool that have not begun executing").tags(new String[] { "threadPool", name }).register(meterRegistry);
        Gauge.builder(String.format("threadPool.%s.%s", name, "poolSizeCount"), (Object)pool, toDoubleFunction5).description("Returns the number of worker threads that have started but not yet terminated").tags(new String[] { "threadPool", name }).register(meterRegistry);
    }
    
    public Jdbi getDatabase() {
        return this.c;
    }
    
    public Authenticator<TokenValidation, AuthRole> getAuthenticator() {
        return this.g;
    }
    
    public Cognito getCognito() {
        return this.f;
    }
    
    public void shutdown() {
    }
}
