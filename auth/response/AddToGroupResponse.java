// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.response;

import com.yojito.minima.gson.GsonDto;

public class AddToGroupResponse extends GsonDto
{
    private final boolean success;
    
    public AddToGroupResponse(final boolean success) {
        this.success = success;
    }
}
