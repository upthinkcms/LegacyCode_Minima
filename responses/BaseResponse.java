// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.responses;

import com.yojito.minima.gson.GsonDto;

public class BaseResponse extends GsonDto
{
    private final boolean success;
    private final String error;
    
    public BaseResponse(final boolean success, final String error) {
        this.success = success;
        this.error = error;
    }
    
    public boolean isSuccess() {
        return this.success;
    }
    
    public String getError() {
        return this.error;
    }
}
