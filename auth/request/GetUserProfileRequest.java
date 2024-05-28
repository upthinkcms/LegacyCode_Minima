// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.request;

import com.yojito.minima.auth.domain.TokenValidation;
import com.yojito.minima.auth.domain.AuthenticatedRequest;

public class GetUserProfileRequest extends AuthenticatedRequest
{
    private final String userName;
    
    public GetUserProfileRequest(final TokenValidation id, final String userName) {
        super(id);
        this.userName = userName;
    }
    
    public String getUserName() {
        return this.userName;
    }
}
