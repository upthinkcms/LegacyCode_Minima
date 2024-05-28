// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.akka;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;

final class a implements InvocationHandler
{
    private OutsideClient<REQ, RESP> a;
    
    public a(final MinimaAkkaService minimaAkkaService, final OutsideClient<REQ, RESP> a) {
        this.a = a;
    }
    
    @Override
    public final Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        MinimaAkkaService.a.info("Invoked method: {%s}", method.getName());
        return this.a.call((REQ)args[0]);
    }
}
