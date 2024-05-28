// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.util;

import java.util.List;
import com.yojito.minima.gson.GsonDto;

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
