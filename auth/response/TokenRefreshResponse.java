// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.response;

import com.yojito.minima.auth.domain.AuthUserLoginToken;
import com.yojito.minima.gson.GsonDto;

public class TokenRefreshResponse extends GsonDto
{
    private final boolean error;
    private final String erroMessage;
    private final AuthUserLoginToken token;
    
    public TokenRefreshResponse(final boolean error, final String erroMessage, final AuthUserLoginToken token) {
        this.error = error;
        this.erroMessage = erroMessage;
        this.token = token;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public AuthUserLoginToken getToken() {
        return this.token;
    }
    
    public static final class Builder
    {
        private boolean error;
        private String erroMessage;
        private AuthUserLoginToken token;
        
        private Builder() {
        }
        
        public static Builder aTokenRefreshResponse() {
            return new Builder();
        }
        
        public final Builder setError(final boolean error) {
            this.error = error;
            return this;
        }
        
        public final Builder setErroMessage(final String erroMessage) {
            this.erroMessage = erroMessage;
            return this;
        }
        
        public final Builder setToken(final AuthUserLoginToken token) {
            this.token = token;
            return this;
        }
        
        public final TokenRefreshResponse build() {
            return new TokenRefreshResponse(this.error, this.erroMessage, this.token);
        }
    }
}
