// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.util;

import io.micrometer.core.instrument.Meter;

static final synthetic class MonitoringController$1 {
    static {
        a = new int[Meter.Type.values().length];
        try {
            MonitoringController$1.a[Meter.Type.COUNTER.ordinal()] = 1;
        }
        catch (final NoSuchFieldError noSuchFieldError) {}
        try {
            MonitoringController$1.a[Meter.Type.TIMER.ordinal()] = 2;
        }
        catch (final NoSuchFieldError noSuchFieldError2) {}
        try {
            MonitoringController$1.a[Meter.Type.GAUGE.ordinal()] = 3;
        }
        catch (final NoSuchFieldError noSuchFieldError3) {}
    }
}