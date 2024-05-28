// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.util;

import java.util.function.ToDoubleFunction;
import org.apache.commons.lang3.tuple.Triple;
import java.util.HashSet;
import java.util.Set;
import com.zaxxer.hikari.HikariDataSource;
import java.util.List;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Gauge;
import java.util.concurrent.TimeUnit;
import io.micrometer.core.instrument.Timer;
import java.util.Date;
import java.util.HashMap;
import com.yojito.minima.logging.MinimaLogger;
import java.util.Optional;
import java.util.UUID;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.commons.lang3.tuple.Pair;
import com.yojito.minima.api.Context;
import io.micrometer.core.instrument.Counter;
import java.util.Map;

public class MetricsUtil
{
    private static final Map<String, String> a;
    private static final Map<String, String> b;
    private static final Map<String, Counter> c;
    private static final Map<String, Counter> d;
    
    public static void reportError(final Context context, final Pair<String, String> errorSig, Throwable var_2_18) {
        context.get("appName");
        final MeterRegistry meterRegistry = context.get("meterRegistry");
        var_2_18 = (Throwable)errorSig.getRight();
        final String s = (String)errorSig.getLeft();
        synchronized (MetricsUtil.a) {
            if (MetricsUtil.a.containsKey(var_2_18)) {
                var_2_18 = (Throwable)MetricsUtil.c.get(MetricsUtil.a.get(var_2_18));
            }
            else {
                final String string = UUID.randomUUID().toString();
                MetricsUtil.a.put((String)var_2_18, string);
                var_2_18 = (Throwable)Counter.builder("exception." + string).baseUnit("counts").description((String)var_2_18).tags(new String[] { "exceptions", "label" }).register(meterRegistry);
                MetricsUtil.c.put(string, (Counter)var_2_18);
            }
        }
        ((Counter)var_2_18).increment();
        synchronized (MetricsUtil.b) {
            if (MetricsUtil.b.containsKey(s)) {
                final Counter counter = MetricsUtil.d.get(MetricsUtil.b.get(s));
            }
            else {
                final String string2 = UUID.randomUUID().toString();
                MetricsUtil.b.put(s, string2);
                final MeterRegistry meterRegistry2;
                MetricsUtil.d.put(string2, Counter.builder("exceptionsig." + string2).baseUnit("counts").description(s).tags(new String[] { "exceptions", "signatures" }).register(meterRegistry2));
            }
        }
        final Counter counter2;
        counter2.increment();
    }
    
    public static String getSignature(final String label) {
        System.out.println(">> A : " + label.split("\\.")[1]);
        final Optional<Object> first;
        if ((first = (Optional<Object>)MetricsUtil.b.entrySet().stream().filter(entry -> entry.getValue().equals(array[1])).map(entry2 -> entry2.getKey()).findFirst()).isPresent()) {
            return first.get();
        }
        return null;
    }
    
    public static String getLabel(final String label) {
        System.out.println(">> A : " + label.split("\\.")[1]);
        final Optional<Object> first;
        if ((first = (Optional<Object>)MetricsUtil.a.entrySet().stream().filter(entry -> entry.getValue().equals(array[1])).map(entry2 -> entry2.getKey()).findFirst()).isPresent()) {
            return first.get();
        }
        return null;
    }
    
    public static String pathToMetricName(final String path) {
        final String[] split = path.split("/");
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; ++i) {
            if (split[i].length() > 0) {
                sb.append(split[i]);
            }
            if (split[i].length() > 0 && i != split.length - 1) {
                sb.append("_");
            }
        }
        return sb.toString();
    }
    
    static {
        MinimaLogger.getLog(MetricsUtil.class);
        a = new HashMap<String, String>();
        b = new HashMap<String, String>();
        c = new HashMap<String, Counter>();
        d = new HashMap<String, Counter>();
    }
    
    public static class MetricsPrinter implements Runnable
    {
        private static final MinimaLogger a;
        private final MeterRegistry b;
        
        public MetricsPrinter(final MeterRegistry registry) {
            this.b = registry;
        }
        
        @Override
        public void run() {
            final Date date2 = new Date();
            this.b.forEachMeter(meter -> {
                final Date date2;
                date2.toString();
                switch (MetricsUtil$1.a[meter.getId().getType().ordinal()]) {
                    case 1: {
                        final Counter counter = (Counter)meter;
                        MetricsPrinter.a.warn("%s Counter id %s value %d", s, counter.getId().getName(), (int)counter.count());
                        return;
                    }
                    case 2: {
                        final Timer timer = (Timer)meter;
                        MetricsPrinter.a.warn("%s Counter id %s mean %f max %f total %f ", s, timer.getId().getName(), timer.max(TimeUnit.MILLISECONDS), timer.max(TimeUnit.MILLISECONDS), timer.totalTime(TimeUnit.MILLISECONDS));
                        return;
                    }
                    case 3: {
                        final Gauge gauge = (Gauge)meter;
                        final Object o;
                        if (!((Gauge)o).getId().getName().endsWith(".percentile")) {
                            MetricsPrinter.a.warn("%s Gauge id %s value %f", s, gauge.getId().getName(), gauge.value());
                        }
                        return;
                    }
                    default: {
                        MetricsPrinter.a.warn("Meter found of Type %s - %s", meter.getId().getType(), meter.getId().toString());
                    }
                }
            });
        }
        
        static {
            a = MinimaLogger.getLog(MetricsPrinter.class);
        }
    }
    
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
}
