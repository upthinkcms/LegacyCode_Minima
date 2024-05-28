// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth;

import com.yojito.minima.gson.GsonObject;
import java.util.function.BiConsumer;
import org.apache.commons.lang3.tuple.Pair;
import com.yojito.minima.netty.HttpCall;

public interface Authenticator<T, R>
{
    T defaultToken();
    
    T failedToken();
    
    R publicRole();
    
    R getTokenRole(final T p0);
    
    R getRole(final String p0);
    
    boolean allowed(final R p0, final R p1);
    
    T authenticate(final HttpCall p0);
    
    Pair<Boolean, BiConsumer<HttpCall, GsonObject>> authorize(final T p0, final R p1, final R p2);
}
