// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.response;

import com.yojito.minima.gson.GsonDto;

public class RemoveFromGroupResponse extends GsonDto
{
    private final boolean success;
    
    public RemoveFromGroupResponse(final boolean success) {
        this.success = success;
    }
}
