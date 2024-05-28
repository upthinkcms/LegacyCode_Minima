// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.util;

import com.yojito.minima.gson.GsonDto;
import io.micrometer.core.instrument.Tag;
import com.yojito.minima.api.API;
import java.util.List;
import java.util.Collections;
import io.micrometer.core.instrument.Gauge;
import java.util.concurrent.TimeUnit;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Counter;
import java.util.ArrayList;
import java.util.Date;
import io.micrometer.core.instrument.MeterRegistry;
import com.yojito.minima.requests.EmptyRequest;
import com.yojito.minima.netty.HttpCall;
import com.yojito.minima.api.Context;
import com.yojito.minima.logging.MinimaLogger;
import com.yojito.minima.api.ApiController;

public class MonitoringController extends ApiController
{
    private static final MinimaLogger a;
    
    public MonitoringController(final Context context) {
        super(context);
    }
    
    @API(path = "/_status", htmlTemplate = "_minima/templates/metrics.html", debugLevel = "TRACE")
    public MonitorResponse status(final HttpCall call, final EmptyRequest request) {
        this.context.get("appName");
        final MeterRegistry meterRegistry = this.context.get("meterRegistry");
        final Date date = new Date();
        final ArrayList list = new ArrayList();
        meterRegistry.forEachMeter(meter -> {
            switch (MonitoringController$1.a[meter.getId().getType().ordinal()]) {
                case 1: {
                    final Counter counter = (Counter)meter;
                    counter.getId().getName();
                    final String s;
                    String errorDetails = null;
                    final String label;
                    if (s.startsWith("exceptionsig.")) {
                        errorDetails = counter.getId().getDescription();
                    }
                    else if (label.startsWith("exception.")) {
                        errorDetails = counter.getId().getDescription();
                    }
                    final String type;
                    list2.add(new MonitorPoint(label, type, 0.0f, 0.0f, 0.0f, (long)counter.count(), errorDetails, a((Meter)counter)));
                    return;
                }
                case 2: {
                    final Timer timer = (Timer)meter;
                    list2.add(new MonitorPoint(timer.getId().getName(), "timer", (float)timer.mean(TimeUnit.MILLISECONDS), (float)timer.max(TimeUnit.MILLISECONDS), (float)timer.totalTime(TimeUnit.MILLISECONDS), 0L, timer.getId().getDescription(), a((Meter)timer)));
                    return;
                }
                case 3: {
                    final Gauge gauge = (Gauge)meter;
                    String label2;
                    if (gauge.getId().getName().endsWith(".percentile")) {
                        final String s2 = (String)gauge.getId().getTags().stream().filter(tag -> tag.getKey().equals("phi")).map(tag2 -> tag2.getValue()).findFirst().get();
                        label2 = String.format("%s.%s", gauge.getId().getName(), s2);
                    }
                    else {
                        label2 = gauge.getId().getName();
                    }
                    list2.add(new MonitorPoint(label2, "gauge", 0.0f, 0.0f, 0.0f, (int)gauge.value(), gauge.getId().getDescription(), a((Meter)gauge)));
                    return;
                }
                default: {
                    MonitoringController.a.warn("Meter found of Type %s - %s", meter.getId().getType(), meter.getId().toString());
                    return;
                }
            }
        });
        Collections.sort((List<Object>)list, (monitorPoint, monitorPoint2) -> {
            if (monitorPoint.b.equals(monitorPoint2.b)) {
                return monitorPoint.a.compareTo(monitorPoint2.a);
            }
            else {
                return monitorPoint.b.compareTo(monitorPoint2.b);
            }
        });
        return new MonitorResponse(date.toString(), list);
    }
    
    private static String a(final Meter meter) {
        final StringBuilder sb = new StringBuilder();
        final List tags;
        if ((tags = meter.getId().getTags()) != null) {
            tags.forEach(tag -> sb2.append(" ").append(tag.getKey()).append("=").append(tag.getValue()));
        }
        return sb.toString();
    }
    
    static {
        a = MinimaLogger.getLog(MonitoringController.class);
    }
    
    public static class MonitorPoint extends GsonDto
    {
        private final String a;
        private final String b;
        private final float c;
        private final float d;
        private final float e;
        private final long f;
        private final String g;
        private final String h;
        
        public MonitorPoint(final String label, final String type, final float mean, final float max, final float total, final long count, final String errorDetails, final String tags) {
            this.a = label;
            this.b = type;
            this.c = mean;
            this.d = max;
            this.e = total;
            this.f = count;
            this.g = errorDetails;
            this.h = tags;
        }
        
        public String getLabel() {
            return this.a;
        }
        
        public String getType() {
            return this.b;
        }
        
        public float getMean() {
            return this.c;
        }
        
        public float getMax() {
            return this.d;
        }
        
        public float getTotal() {
            return this.e;
        }
        
        public long getCount() {
            return this.f;
        }
        
        public String getErrorDetails() {
            return this.g;
        }
        
        public String getTags() {
            return this.h;
        }
    }
    
    public static class MonitorResponse extends GsonDto
    {
        private final String a;
        private final List<MonitorPoint> b;
        
        public MonitorResponse(final String date, final List<MonitorPoint> data) {
            this.a = date;
            this.b = data;
        }
        
        public String getDate() {
            return this.a;
        }
        
        public List<MonitorPoint> getData() {
            return this.b;
        }
    }
}
