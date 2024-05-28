// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.util;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Gauge;
import java.util.concurrent.TimeUnit;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Counter;
import java.util.Date;
import io.micrometer.core.instrument.MeterRegistry;
import com.yojito.minima.logging.MinimaLogger;

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
