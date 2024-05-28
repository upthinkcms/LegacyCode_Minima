// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.domain;

public static final class Builder
{
    private String accessToken;
    private long expiresIn;
    private String tokenType;
    private String refreshToken;
    private String idToken;
    private String payload;
    
    private Builder() {
    }
    
    public static Builder anAuthUserLoginToken() {
        return new Builder();
    }
    
    public final Builder setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
    
    public final Builder setExpiresIn(final long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
    
    public final Builder setTokenType(final String tokenType) {
        this.tokenType = tokenType;
        return this;
    }
    
    public final Builder setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }
    
    public final Builder setIdToken(final String idToken) {
        this.idToken = idToken;
        return this;
    }
    
    public final Builder setPayload(final String payload) {
        this.payload = payload;
        return this;
    }
    
    public final AuthUserLoginToken build() {
        return new AuthUserLoginToken(this.accessToken, this.expiresIn, this.tokenType, this.refreshToken, this.idToken, this.payload);
    }
}
