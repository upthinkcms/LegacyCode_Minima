// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.netty;

import java.util.concurrent.ConcurrentHashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import com.yojito.minima.templates.TemplateEngine;
import java.util.Iterator;
import io.netty.util.Attribute;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.ServerCookieEncoder;
import com.yojito.minima.responses.WrappedDto;
import com.yojito.minima.auth.domain.UnAuthenitcatedDTO;
import java.util.Collection;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.HttpHeaderNames;
import java.util.ArrayList;
import io.netty.handler.codec.http.HttpMethod;
import java.util.HashMap;
import java.util.Optional;
import java.util.Map;
import com.yojito.minima.responses.StreamDto;
import java.util.UUID;
import com.yojito.minima.api.API;
import com.yojito.minima.gson.GsonObject;
import java.util.function.BiFunction;
import io.netty.util.AttributeKey;
import com.yojito.minima.gson.GsonDto;
import java.util.concurrent.ConcurrentMap;
import java.util.Set;
import com.yojito.minima.logging.MinimaLogger;

public class GsonHandler
{
    private static final MinimaLogger b;
    private static final Set<String> c;
    private static final ConcurrentMap<String, ApiHandle> d;
    private static final ApiHandle<GsonDto, GsonDto> e;
    static AttributeKey<Boolean> a;
    
    public static void dontPrintResponse(final String path) {
        GsonHandler.c.add(path);
    }
    
    public static <Req extends GsonDto, Resp extends GsonDto> void register(String path, Class<Req> requestClass, Class<Resp> responseClass, BiFunction<HttpCall, Req, Resp> handler, String method) {
        final String s = "";
        final String s2 = "";
        final String debugLevel = "DEBUG";
        final String htmlTemplate = s2;
        final String authRole = s;
        method = method;
        handler = handler;
        responseClass = responseClass;
        requestClass = requestClass;
        path = path;
        GsonHandler.d.put(path, new ApiHandle(path, (Class<GsonDto>)requestClass, (Class<GsonDto>)responseClass, (BiFunction<HttpCall, GsonDto, GsonDto>)handler, null, method, false, false, authRole, htmlTemplate, false, debugLevel, false));
    }
    
    public static void registerGenericAPI(final String path, final Class<? extends GsonDto> requestClass, final Class<? extends GsonDto> responseClass, final BiFunction<HttpCall, ? extends GsonDto, ? extends GsonDto> handler, final String method, final API api) {
        GsonHandler.d.put(path, new ApiHandle(path, requestClass, responseClass, handler, null, method, api.corsEnabled(), api.authenticated(), api.authRole(), api.htmlTemplate(), api.skipJsonSerialization(), api.debugLevel(), api.corsCredsEnabled()));
    }
    
    private static final GsonDto a(final HttpCall httpCall, final ApiHandle apiHandle, GsonObject gsonObject) {
        final String a = apiHandle.a;
        final Class<Req> b = apiHandle.b;
        final BiFunction<HttpCall, Req, Resp> d = apiHandle.d;
        final BiFunction<HttpCall, GsonObject, GsonObject> e = apiHandle.e;
        final String f = apiHandle.f;
        final String string = UUID.randomUUID().toString();
        try {
            gsonObject.put("_type", b.getCanonicalName());
            if (a(apiHandle.k)) {
                GsonHandler.b.debug("Request %s %s Rc=%s - %s", a, string, gsonObject.getClass(), gsonObject.toStringPretty());
                if (gsonObject.has("id")) {
                    GsonHandler.b.debug(">>TDK>>>> Request object has Id property - %s", gsonObject.getJsonObject().get("id"));
                    gsonObject.remove("id");
                }
            }
            if (d == null) {
                if (GsonHandler.b.isDebugEnabled()) {
                    GsonHandler.b.debug("Executing method " + f + " on class " + apiHandle.e.getClass() + " RequestClass %s", b);
                }
                final GsonObject gsonObject2 = e.apply(httpCall, gsonObject);
                final String s = a;
                final GsonObject gsonObject3 = gsonObject2;
                final String k = apiHandle.k;
                final String s2 = string;
                final String s3 = k;
                gsonObject = gsonObject3;
                final String s4 = s;
                final boolean a2 = a(s3);
                final boolean b2;
                if (b2 = !GsonHandler.c.contains(s4)) {
                    if (a2) {
                        GsonHandler.b.debug("Response %s %s - %s", s4, s2, gsonObject.toStringPretty());
                    }
                }
                else if (GsonHandler.b.isTraceEnabled()) {
                    GsonHandler.b.trace("Response %s %s - %s", s4, s2, gsonObject.toStringPretty());
                }
                return gsonObject2;
            }
            if (GsonHandler.b.isDebugEnabled()) {
                GsonHandler.b.debug("Executing method " + f + " on class " + apiHandle.d.getClass() + " RequestClass %s", b);
            }
            final GsonDto gsonDto;
            if ((gsonDto = d.apply(httpCall, (Req)gsonObject)) != null) {
                final String s5 = a;
                final GsonDto gsonDto2 = gsonDto;
                final String i = apiHandle.k;
                final String s6 = string;
                final String s7 = i;
                final GsonDto gsonDto3 = gsonDto2;
                final String s8 = s5;
                if (!(gsonDto3 instanceof StreamDto)) {
                    final boolean a3 = a(s7);
                    final boolean b3;
                    if (b3 = !GsonHandler.c.contains(s8)) {
                        if (a3) {
                            GsonHandler.b.debug("Response %s %s :: %s", s8, s6, gsonDto3.toStringPretty());
                        }
                    }
                    else if (GsonHandler.b.isTraceEnabled()) {
                        GsonHandler.b.trace("Response %s %s :: %s", s8, s6, gsonDto3.toStringPretty());
                    }
                }
                return gsonDto;
            }
            GsonHandler.b.warn("NULL Response from Controller", new Object[0]);
            throw new RuntimeException("NULL Response from Controller");
        }
        catch (final Exception cause) {
            throw new RuntimeException(cause);
        }
    }
    
    private static boolean a(String s) {
        s = s;
        switch (s) {
            case "TRACE": {
                return GsonHandler.b.isTraceEnabled();
            }
            case "DEBUG": {
                return GsonHandler.b.isDebugEnabled();
            }
            case "INFO": {
                return GsonHandler.b.isInfoEnabled();
            }
            default: {
                return GsonHandler.b.isDebugEnabled();
            }
        }
    }
    
    private static Optional<Map<String, String>> a(final Map<String, String> map) {
        Object o = Optional.empty();
        if (map.size() > 0) {
            final HashMap value = new HashMap();
            map.forEach((s, s2) -> map2.put(s, s2));
            o = Optional.of(value);
        }
        return (Optional<Map<String, String>>)o;
    }
    
    public static void handle(final HttpCall call) {
        GsonHandler.b.debug("Pipeline Handlers :: %s", call.getCtx().pipeline().names());
        final Attribute attr;
        (attr = call.getCtx().attr((AttributeKey)GsonHandler.a)).set((Object)Boolean.FALSE);
        ApiHandle<GsonDto, GsonDto> apiHandle = APIHandleMapper.match(call, GsonHandler.d);
        if (GsonHandler.b.isDebugEnabled()) {
            GsonHandler.b.debug("URI [%s] Method [%s]", call.getUri(), call.getMethod());
            GsonHandler.b.debug("ApiHandle : authEnabled = [%b] corsEnabled [%b] path [%s]", apiHandle != null && apiHandle.h, apiHandle != null && apiHandle.g, (apiHandle != null) ? apiHandle.a : "NULL");
        }
        try {
            if (apiHandle == null) {
                apiHandle = GsonHandler.e;
            }
            if (call.getMethod().equals((Object)HttpMethod.OPTIONS) && apiHandle.g) {
                call.sendCorsOptions(apiHandle.l);
            }
            else {
                final ApiHandle<GsonDto, GsonDto> apiHandle2 = apiHandle;
                GsonObject gsonObject = null;
                Label_0454: {
                    if (apiHandle.j) {
                        gsonObject = new GsonObject();
                    }
                    else {
                        final ArrayList list = new ArrayList();
                        call.getHeaders().forEach(entry -> {
                            final String s3 = entry.getKey();
                            entry.getValue();
                            if (s3.equalsIgnoreCase(HttpHeaderNames.CONTENT_TYPE.toString())) {
                                final String s4;
                                list2.add(s4);
                            }
                            return;
                        });
                        if (list.size() > 0) {
                            final String s = (String)list.get(0);
                            switch (s) {
                                case "application/x-www-form-urlencoded": {
                                    final Map parameters = new QueryStringDecoder("?" + call.getContent()).parameters();
                                    final GsonObject gsonObject2 = new GsonObject();
                                    parameters.forEach((s5, values) -> {
                                        if (values.size() == 1) {
                                            gsonObject3.put(s5, values.get(0));
                                            return;
                                        }
                                        else {
                                            if (values.size() > 1) {
                                                gsonObject3.put(s5, values);
                                            }
                                            return;
                                        }
                                    });
                                    gsonObject = gsonObject2;
                                    break Label_0454;
                                }
                            }
                        }
                        final String content = call.getContent();
                        if (GsonHandler.b.isDebugEnabled()) {
                            GsonHandler.b.debug("JSON Content1 [%s]", content);
                        }
                        gsonObject = ((content == null || content.isEmpty() || content.isBlank()) ? new GsonObject() : new GsonObject(call.getContent()));
                    }
                }
                final GsonDto a;
                if ((a = a(call, apiHandle2, gsonObject)) instanceof UnAuthenitcatedDTO) {
                    return;
                }
                if (a instanceof WrappedDto) {
                    final WrappedDto wrappedDto = (WrappedDto)a;
                    final HashMap hashMap = new HashMap();
                    if (wrappedDto.getExtraHeaders() != null) {
                        hashMap.putAll(wrappedDto.getExtraHeaders());
                    }
                    if (wrappedDto.getCookies() != null) {
                        for (final String s2 : wrappedDto.getCookies().keySet()) {
                            hashMap.put("Set-Cookie", ServerCookieEncoder.encode(s2, (String)wrappedDto.getCookies().get(s2)));
                        }
                    }
                    a(call, apiHandle, wrappedDto.getResponse(), a(hashMap), wrappedDto.getResponseStatus(), wrappedDto.getCustomTemplate());
                }
                else {
                    if (!(a instanceof StreamDto)) {
                        a(call, apiHandle, a, Optional.empty(), HttpResponseStatus.OK, null);
                        return;
                    }
                    attr.set((Object)Boolean.TRUE);
                    final StreamDto streamDto = (StreamDto)a;
                    call.sendStream(streamDto.getInputStream(), a(streamDto.getExtraHeaders()), streamDto.getResponseStatus(), streamDto.getContentType(), streamDto.getContentLength());
                }
            }
        }
        catch (final Exception ex) {
            GsonHandler.b.error(ex, "Error handle", new Object[0]);
            call.sendError(ex, apiHandle.g, apiHandle.l);
        }
    }
    
    private static void a(final HttpCall httpCall, final ApiHandle apiHandle, final GsonDto dto, final Optional<Map<String, String>> extraHeaders, final HttpResponseStatus responseStatus, final String s) {
        String i;
        if ((i = apiHandle.i) == null || (i.isBlank() && i.isEmpty())) {
            i = s;
        }
        if (i != null && !i.isBlank() && !i.isEmpty()) {
            try {
                httpCall.sendHTML(TemplateEngine.getHtml(i, dto), extraHeaders, responseStatus, apiHandle.g, apiHandle.l);
                return;
            }
            catch (final Exception ex) {
                GsonHandler.b.error(ex, "Error while preparing view templates", new Object[0]);
                httpCall.sendHTML(TemplateEngine.getErrorHtml(ex), extraHeaders, responseStatus, apiHandle.g, apiHandle.l);
                return;
            }
        }
        httpCall.sendJSON(dto.toJson(), apiHandle.g, apiHandle.l, extraHeaders, responseStatus);
    }
    
    static {
        b = MinimaLogger.getLog(GsonHandler.class);
        c = new HashSet<String>(Arrays.asList("/doc/equations", "/doc/images", "/doc", "/doc/configOptions", "/upload/details", "/doc/preview", "/doc/download", "/doc/query", "/upload/progress", "/upload/list"));
        d = new ConcurrentHashMap<String, ApiHandle>();
        e = new ApiHandle<GsonDto, GsonDto>("", GsonDto.class, GsonDto.class, (BiFunction<HttpCall, GsonDto, GsonDto>)null, (p0, gsonObject) -> {
            GsonHandler.b.debug("ECHO HANDLER :: %s", gsonObject.toStringPretty());
            return gsonObject;
        }, "DEFAULT", false, false, "", "", false, "DEBUG", false);
        GsonHandler.a = (AttributeKey<Boolean>)AttributeKey.newInstance("isStreamRequest");
    }
}
