// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.netty;

import com.yojito.minima.gson.GsonObject;
import java.util.function.BiFunction;
import com.yojito.minima.gson.GsonDto;

public class ApiHandle<Req extends GsonDto, Resp extends GsonDto>
{
    final String a;
    final Class<Req> b;
    final Class<Resp> c;
    final BiFunction<HttpCall, Req, Resp> d;
    final BiFunction<HttpCall, GsonObject, GsonObject> e;
    final String f;
    final boolean g;
    final boolean h;
    final String i;
    final boolean j;
    final String k;
    final boolean l;
    
    public ApiHandle(final String path, final Class<Req> requestClass, final Class<Resp> responseClass, final BiFunction<HttpCall, Req, Resp> handler, final BiFunction<HttpCall, GsonObject, GsonObject> handlerRaw, final String method, final boolean corsEnabled, final boolean authenticated, final String authRole, final String htmlTemplate, final boolean skipJsonSerialization, final String debugLevel, final boolean corsCredsEnabled) {
        this.a = path;
        this.b = requestClass;
        this.c = responseClass;
        this.d = handler;
        this.e = handlerRaw;
        this.f = method;
        this.g = corsEnabled;
        this.h = authenticated;
        this.i = htmlTemplate;
        this.j = skipJsonSerialization;
        this.k = debugLevel;
        this.l = corsCredsEnabled;
    }
    
    public String getPath() {
        return this.a;
    }
    
    public Class<Req> getRequestClass() {
        return this.b;
    }
    
    public Class<Resp> getResponseClass() {
        return this.c;
    }
    
    public BiFunction<HttpCall, Req, Resp> getHandler() {
        return this.d;
    }
    
    public String getMethod() {
        return this.f;
    }
}
