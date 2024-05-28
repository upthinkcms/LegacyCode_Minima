// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.domain;

import com.yojito.minima.gson.GsonDto;

public class AuthenticatedRequest extends GsonDto
{
    protected final TokenValidation id;
    
    public AuthenticatedRequest(final TokenValidation id) {
        this.id = id;
    }
    
    public TokenValidation getId() {
        return this.id;
    }
    
    public String getSubject() {
        return this.id.getSub();
    }
}
