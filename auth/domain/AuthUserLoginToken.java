// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.domain;

import com.yojito.minima.gson.GsonDto;

public class AuthUserLoginToken extends GsonDto
{
    private final String accessToken;
    private final long expiresIn;
    private final String tokenType;
    private final String refreshToken;
    private final String idToken;
    private final String payload;
    
    public AuthUserLoginToken(final String accessToken, final long expiresIn, final String tokenType, final String refreshToken, final String idToken, final String payload) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.idToken = idToken;
        this.payload = payload;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public String getAccessToken() {
        return this.accessToken;
    }
    
    public String getRefreshToken() {
        return this.refreshToken;
    }
    
    public String getIdToken() {
        return this.idToken;
    }
    
    public String getPayload() {
        return this.payload;
    }
    
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
}
