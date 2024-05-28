// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.api;

public abstract class ApiController
{
    protected Context context;
    
    public ApiController(final Context context) {
        this.context = context;
    }
    
    public boolean isServiceController() {
        return false;
    }
}
