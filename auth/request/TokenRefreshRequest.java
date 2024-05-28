// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.request;

import com.yojito.minima.gson.GsonDto;

public class TokenRefreshRequest extends GsonDto
{
    private final String userName;
    private final String refreshToken;
    
    public TokenRefreshRequest(final String userName, final String refreshToken) {
        this.userName = userName;
        this.refreshToken = refreshToken;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public String getRefreshToken() {
        return this.refreshToken;
    }
}
