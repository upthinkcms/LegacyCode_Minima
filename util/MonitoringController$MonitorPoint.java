// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.util;

import com.yojito.minima.gson.GsonDto;

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
