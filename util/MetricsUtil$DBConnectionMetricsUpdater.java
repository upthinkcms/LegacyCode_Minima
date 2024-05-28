// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.util;

import java.util.function.ToDoubleFunction;
import org.apache.commons.lang3.tuple.Triple;
import java.util.HashMap;
import java.util.HashSet;
import io.micrometer.core.instrument.Gauge;
import java.util.Map;
import java.util.Set;
import io.micrometer.core.instrument.MeterRegistry;
import com.zaxxer.hikari.HikariDataSource;
import java.util.List;

public static class DBConnectionMetricsUpdater
{
    private final List<HikariDataSource> a;
    private final MeterRegistry b;
    private Set<String> c;
    private Map<String, Gauge> d;
    
    public DBConnectionMetricsUpdater(final MeterRegistry registry, final List<HikariDataSource> connectionPools) {
        this.c = new HashSet<String>();
        this.d = new HashMap<String, Gauge>();
        this.a = connectionPools;
        this.b = registry;
        this.a.forEach(hikariDataSource -> this.a(hikariDataSource));
    }
    
    private Triple<Gauge, Gauge, Gauge> a(final HikariDataSource hikariDataSource) {
        final String poolName = hikariDataSource.getPoolName();
        synchronized (this.c) {
            if (this.c.contains(poolName)) {
                return (Triple<Gauge, Gauge, Gauge>)Triple.of((Object)this.d.get(String.format("db.%s.%s", poolName, "active")), (Object)this.d.get(String.format("db.%s.%s", poolName, "idle")), (Object)this.d.get(String.format("db.%s.%s", poolName, "total")));
            }
            final ToDoubleFunction toDoubleFunction = hikariDataSource2 -> hikariDataSource2.getHikariPoolMXBean().getActiveConnections();
            final ToDoubleFunction toDoubleFunction2 = hikariDataSource3 -> hikariDataSource3.getHikariPoolMXBean().getIdleConnections();
            final ToDoubleFunction toDoubleFunction3 = hikariDataSource4 -> hikariDataSource4.getHikariPoolMXBean().getTotalConnections();
            final ToDoubleFunction toDoubleFunction4 = hikariDataSource5 -> hikariDataSource5.getHikariPoolMXBean().getThreadsAwaitingConnection();
            final Gauge register = Gauge.builder(String.format("db.%s.%s", poolName, "active"), (Object)hikariDataSource, toDoubleFunction).description("Active connections in Pool " + poolName).tags(new String[] { "db", poolName }).register(this.b);
            final Gauge register2 = Gauge.builder(String.format("db.%s.%s", poolName, "idle"), (Object)hikariDataSource, toDoubleFunction2).description("Idle connections in Pool " + poolName).tags(new String[] { "db", poolName }).register(this.b);
            final Gauge register3 = Gauge.builder(String.format("db.%s.%s", poolName, "total"), (Object)hikariDataSource, toDoubleFunction3).description("Total connections in Pool " + poolName).tags(new String[] { "db", poolName }).register(this.b);
            final Gauge register4 = Gauge.builder(String.format("db.%s.%s", poolName, "waitingThreads"), (Object)hikariDataSource, toDoubleFunction4).description("Waiting threads for Pool " + poolName).tags(new String[] { "db", poolName }).register(this.b);
            this.d.put(String.format("%s.%s", poolName, "active"), register);
            this.d.put(String.format("%s.%s", poolName, "idle"), register2);
            this.d.put(String.format("%s.%s", poolName, "total"), register3);
            this.d.put(String.format("%s.%s", poolName, "waitingThreads"), register4);
            return (Triple<Gauge, Gauge, Gauge>)Triple.of((Object)register, (Object)register2, (Object)register3);
        }
    }
    
    public void add(final HikariDataSource ds) {
        this.a(ds);
    }
}
