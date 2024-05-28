// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.cognito;

import com.yojito.minima.gson.GsonDto;
import com.yojito.minima.gson.GsonObject;
import java.util.function.BiConsumer;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.Map;
import java.util.Optional;
import com.yojito.minima.netty.HttpCall;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.List;
import com.yojito.minima.logging.MinimaLogger;
import com.yojito.minima.auth.domain.TokenValidation;
import com.yojito.minima.auth.Authenticator;

public class CognitoAuthenticator implements Authenticator<TokenValidation, AuthRole>
{
    private static final MinimaLogger a;
    private final Cognito b;
    
    public CognitoAuthenticator(final Cognito cognito) {
        this.b = cognito;
    }
    
    @Override
    public TokenValidation defaultToken() {
        return TokenValidation.defaultToken();
    }
    
    @Override
    public TokenValidation failedToken() {
        return TokenValidation.failedToken();
    }
    
    @Override
    public AuthRole publicRole() {
        return AuthRole.PUBLIC;
    }
    
    @Override
    public AuthRole getTokenRole(final TokenValidation tokenValidation) {
        if (!tokenValidation.isValidated()) {
            return AuthRole.PUBLIC;
        }
        final List<String> groups;
        if ((groups = tokenValidation.getGroups()) == null) {
            return AuthRole.AUTHENTICATED;
        }
        final List<? super Object> list = groups.stream().map(role -> AuthRole.valueOf(role)).sorted(Comparator.comparingInt(AuthRole::getLevel).reversed()).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
        if (CognitoAuthenticator.a.isDebugEnabled()) {
            CognitoAuthenticator.a.debug("Sorted Roles %s", list);
        }
        return (AuthRole)list.get(0);
    }
    
    @Override
    public AuthRole getRole(final String role) {
        return AuthRole.valueOf(role);
    }
    
    @Override
    public boolean allowed(final AuthRole requiredRole, final AuthRole requestRole) {
        return requiredRole.allowed(requestRole);
    }
    
    @Override
    public TokenValidation authenticate(final HttpCall call) {
        Optional<Object> optional = Optional.empty();
        final boolean debugEnabled = CognitoAuthenticator.a.isDebugEnabled();
        final boolean traceEnabled = CognitoAuthenticator.a.isTraceEnabled();
        for (Map.Entry entry : call.getHeaders()) {
            if (traceEnabled) {
                CognitoAuthenticator.a.trace("Header %s", (String)entry.getKey() + "=" + (String)entry.getValue());
            }
            if (((String)entry.getKey()).equalsIgnoreCase("authorization")) {
                optional = (Optional<Object>)Optional.of(entry.getValue());
            }
        }
        if (optional.isEmpty()) {
            return this.failedToken();
        }
        if (traceEnabled) {
            CognitoAuthenticator.a.trace("AuthHeader %s", optional.get());
        }
        final String[] split;
        if ((split = optional.get().split(" ")).length <= 0) {
            return this.failedToken();
        }
        final String s = split[0];
        final String tokenInput;
        if ((tokenInput = split[1]) == null || tokenInput.length() <= 0) {
            return this.failedToken();
        }
        if (debugEnabled) {
            CognitoAuthenticator.a.trace("tokenType %s, token %s", s, tokenInput);
        }
        try {
            return this.b.validateToken(tokenInput);
        }
        catch (final ExecutionException cause) {
            throw new RuntimeException(cause);
        }
    }
    
    @Override
    public Pair<Boolean, BiConsumer<HttpCall, GsonObject>> authorize(TokenValidation tokenValidation, final AuthRole requiredROLE, final AuthRole requestRole) {
        final boolean allowed = requiredROLE.allowed(requestRole);
        if (CognitoAuthenticator.a.isDebugEnabled()) {
            CognitoAuthenticator.a.debug("Authenticator:: Request Role [%s] Required Role [%s] Authorized %b", requestRole, requiredROLE, allowed);
        }
        if (!tokenValidation.isValidated() || !allowed) {
            CognitoAuthenticator.a.warn("Token Validation failed. UnAuthorized", new Object[0]);
            return (Pair<Boolean, BiConsumer<HttpCall, GsonObject>>)Pair.of((Object)Boolean.FALSE, (httpCall, p2) -> httpCall.sendAuthError(true, false, tokenValidation2.getValidationError()));
        }
        if (!requiredROLE.equals(AuthRole.PUBLIC)) {
            tokenValidation = (TokenValidation)((p1, gsonObject) -> gsonObject.put("id", value));
            return (Pair<Boolean, BiConsumer<HttpCall, GsonObject>>)Pair.of((Object)Boolean.TRUE, (Object)tokenValidation);
        }
        return (Pair<Boolean, BiConsumer<HttpCall, GsonObject>>)Pair.of((Object)Boolean.TRUE, (p0, p1) -> {});
    }
    
    static {
        a = MinimaLogger.getLog(CognitoAuthenticator.class);
    }
}
