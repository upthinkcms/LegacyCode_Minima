// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.netty;

import io.netty.handler.codec.http.FullHttpResponse;
import java.util.Set;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import java.util.Iterator;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;
import com.yojito.minima.gson.GsonObject;
import java.util.HashMap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpContentCompressor;
import java.io.InputStream;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpMethod;
import java.util.Optional;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import java.util.Map;
import io.netty.handler.codec.http.HttpRequest;
import com.yojito.minima.logging.MinimaLogger;

public class HttpCall
{
    private static final MinimaLogger a;
    private HttpRequest b;
    private String c;
    private Iterable<Map.Entry<String, String>> d;
    private Map<String, List<String>> e;
    private StringBuilder f;
    private ChannelHandlerContext g;
    private LastHttpContent h;
    private Map<String, Optional<String>> i;
    
    public HttpCall() {
        this.f = new StringBuilder();
    }
    
    public LastHttpContent getTrailer() {
        return this.h;
    }
    
    public void setTrailer(final LastHttpContent trailer) {
        this.h = trailer;
    }
    
    public ChannelHandlerContext getCtx() {
        return this.g;
    }
    
    public void setCtx(final ChannelHandlerContext ctx) {
        this.g = ctx;
    }
    
    public HttpRequest getRequest() {
        return this.b;
    }
    
    public void setRequest(final HttpRequest request) {
        this.b = request;
    }
    
    public String getUri() {
        return this.c;
    }
    
    public void setUri(final String uri) {
        this.c = uri;
    }
    
    public Iterable<Map.Entry<String, String>> getHeaders() {
        return this.d;
    }
    
    public void setHeaders(final Iterable<Map.Entry<String, String>> headers) {
        this.d = headers;
    }
    
    public Map<String, List<String>> getQueryParams() {
        return this.e;
    }
    
    public void setQueryParams(final Map<String, List<String>> queryParams) {
        this.e = queryParams;
    }
    
    public String getContent() {
        return this.f.toString();
    }
    
    public void appendContent(final String httpContentString) {
        this.f.append(httpContentString);
    }
    
    public HttpMethod getMethod() {
        return this.b.method();
    }
    
    public boolean writeResponse(final ChannelHandlerContext ctx, final String responseString, final String contentType, final HttpResponseStatus status, final Optional<Map<String, String>> headers) {
        final boolean keepAlive = HttpUtil.isKeepAlive((HttpMessage)this.b);
        final DefaultFullHttpResponse defaultFullHttpResponse;
        ((FullHttpResponse)(defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer((CharSequence)responseString, CharsetUtil.UTF_8)))).headers().set((CharSequence)HttpHeaderNames.CONTENT_TYPE, (Object)contentType);
        if (headers.isPresent()) {
            headers.get().forEach((s, s2) -> fullHttpResponse.headers().set(s, (Object)s2));
        }
        if (keepAlive) {
            ((FullHttpResponse)defaultFullHttpResponse).headers().setInt((CharSequence)HttpHeaderNames.CONTENT_LENGTH, ((FullHttpResponse)defaultFullHttpResponse).content().readableBytes());
            ((FullHttpResponse)defaultFullHttpResponse).headers().set((CharSequence)HttpHeaderNames.CONNECTION, (Object)HttpHeaderValues.KEEP_ALIVE);
        }
        ctx.write((Object)defaultFullHttpResponse);
        return keepAlive;
    }
    
    public boolean writeResponseStream(final ChannelHandlerContext ctx, final InputStream dataStream, final String contentType, final HttpResponseStatus status, final Optional<Map<String, String>> headers, final int length) {
        if (ctx.pipeline().get((Class)HttpContentCompressor.class) != null) {
            ctx.pipeline().remove((Class)HttpContentCompressor.class);
        }
        final boolean keepAlive = HttpUtil.isKeepAlive((HttpMessage)this.b);
        final DefaultHttpResponse defaultHttpResponse;
        (defaultHttpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status)).headers().set((CharSequence)HttpHeaderNames.CONTENT_TYPE, (Object)contentType);
        if (headers.isPresent()) {
            headers.get().forEach((s, s2) -> defaultHttpResponse2.headers().set(s, (Object)s2));
        }
        defaultHttpResponse.headers().setInt((CharSequence)HttpHeaderNames.CONTENT_LENGTH, length);
        if (keepAlive) {
            defaultHttpResponse.headers().set((CharSequence)HttpHeaderNames.CONNECTION, (Object)HttpHeaderValues.KEEP_ALIVE);
        }
        else {
            defaultHttpResponse.headers().set((CharSequence)HttpHeaderNames.CONNECTION, (Object)"close");
        }
        ctx.write((Object)defaultHttpResponse);
        ctx.write((Object)new ChunkedStream(dataStream, length)).addListener((GenericFutureListener)new GenericFutureListener(this) {
            private /* synthetic */ boolean a = HttpCall.a.isTraceEnabled();
            
            public final void operationComplete(final Future future) throws Exception {
                if (future.isSuccess()) {
                    if (this.a) {
                        HttpCall.a.trace("Chunked Streamed is written successfully", new Object[0]);
                    }
                }
                else {
                    HttpCall.a.error("Stream writing has failed", new Object[0]);
                    future.cause().printStackTrace();
                }
            }
        });
        final ChannelFuture write = ctx.write((Object)LastHttpContent.EMPTY_LAST_CONTENT);
        if (!HttpUtil.isKeepAlive((HttpMessage)this.b)) {
            write.addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
        }
        return keepAlive;
    }
    
    public void sendJSON(final String response, final boolean corsEnabled, final boolean corsCredsEnabled, final Optional<Map<String, String>> extraHeaders, final HttpResponseStatus responseStatus) {
        final HashMap value = new HashMap();
        if (corsEnabled) {
            this.a(corsCredsEnabled).get().forEach((s, s2) -> map.put(s, s2));
        }
        if (extraHeaders.isPresent()) {
            extraHeaders.get().forEach((s3, s4) -> map2.put(s3, s4));
        }
        if (!this.writeResponse(this.g, response, "application/json", responseStatus, (Optional<Map<String, String>>)((value.size() > 0) ? Optional.of(value) : Optional.empty()))) {
            this.g.writeAndFlush((Object)Unpooled.EMPTY_BUFFER).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
        }
    }
    
    public void sendHTML(final String response, final Optional<Map<String, String>> extraHeaders, final HttpResponseStatus responseStatus, final boolean corsEnabled, final boolean corsCredsEnabled) {
        final HashMap value = new HashMap();
        if (corsEnabled) {
            this.a(corsCredsEnabled).get().forEach((s, s2) -> map.put(s, s2));
        }
        if (extraHeaders.isPresent()) {
            extraHeaders.get().forEach((s3, s4) -> map2.put(s3, s4));
        }
        if (!this.writeResponse(this.g, response, "text/html", responseStatus, (Optional<Map<String, String>>)Optional.of(value))) {
            this.g.writeAndFlush((Object)Unpooled.EMPTY_BUFFER).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
        }
    }
    
    public void sendStream(final InputStream stream, final Optional<Map<String, String>> extraHeaders, final HttpResponseStatus responseStatus, final String contentType, final int contentLength) {
        this.writeResponseStream(this.g, stream, contentType, responseStatus, extraHeaders, contentLength);
    }
    
    public void sendError(final Exception e, final boolean corsEnabled, final boolean corsCredsEnabled) {
        final GsonObject gsonObject = new GsonObject();
        Object headers = Optional.empty();
        if (corsEnabled) {
            headers = this.a(corsCredsEnabled);
        }
        gsonObject.put("error", String.format("Error %s", e.getMessage()));
        if (!this.writeResponse(this.g, gsonObject.toString(), "application/json", HttpResponseStatus.INTERNAL_SERVER_ERROR, (Optional<Map<String, String>>)headers)) {
            this.g.writeAndFlush((Object)Unpooled.EMPTY_BUFFER).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
        }
    }
    
    public void sendAuthError(final boolean corsEnabled, final boolean corsCredsEnabled, final String validationError) {
        final GsonObject gsonObject = new GsonObject();
        Object headers = Optional.empty();
        if (corsEnabled) {
            headers = this.a(corsCredsEnabled);
        }
        gsonObject.put("error", String.format("Not Authorized - %s", validationError));
        if (!this.writeResponse(this.g, gsonObject.toString(), "application/json", HttpResponseStatus.UNAUTHORIZED, (Optional<Map<String, String>>)headers)) {
            this.g.writeAndFlush((Object)Unpooled.EMPTY_BUFFER).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
        }
    }
    
    public void sendCorsOptions(final boolean corsCredsEnabled) {
        if (!this.writeResponse(this.g, "", "application/json", HttpResponseStatus.OK, this.a(corsCredsEnabled))) {
            this.g.writeAndFlush((Object)Unpooled.EMPTY_BUFFER).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
        }
    }
    
    private Optional<Map<String, String>> a(final boolean b) {
        final HashMap value = new HashMap();
        final String property;
        if (!(property = System.getProperty("minima.corsAllowOrigin", "*")).equals("*")) {
            final HashSet set;
            (set = new HashSet()).addAll(Arrays.asList(property.split(",")));
            boolean b2 = false;
            Object anObject = null;
            final Iterator<Map.Entry<String, String>> iterator = this.d.iterator();
            while (iterator.hasNext()) {
                final Map.Entry entry;
                if (((String)(entry = iterator.next()).getKey()).equalsIgnoreCase("origin")) {
                    anObject = entry.getValue();
                }
            }
            HttpCall.a.debug("HTTP CALL ORIGIN HOST - %s", anObject);
            if (anObject != null) {
                final Iterator iterator2 = set.iterator();
                while (iterator2.hasNext()) {
                    final String s;
                    if ((s = (String)iterator2.next()).equals(anObject)) {
                        b2 = true;
                        value.put("Access-Control-Allow-Origin", s);
                        break;
                    }
                }
            }
            if (!b2) {
                value.put("Access-Control-Allow-Origin", "null");
            }
        }
        else {
            value.put("Access-Control-Allow-Origin", "*");
        }
        value.put("Access-Control-Allow-Methods", "POST");
        value.put("Access-Control-Allow-Headers", "accept, content-type, authorization, x-minima-c");
        if (b) {
            value.put("Access-Control-Allow-Credentials", "true");
        }
        return (Optional<Map<String, String>>)Optional.of(value);
    }
    
    public Map<String, String> getCookies() {
        Map map = null;
        final String value;
        if ((value = this.b.headers().get((CharSequence)HttpHeaderNames.COOKIE)) != null) {
            try {
                final Set decode;
                final int size;
                if ((size = (decode = CookieDecoder.decode(value)).size()) > 0) {
                    map = new HashMap(size);
                    for (final Cookie cookie : decode) {
                        map.put(cookie.getName(), cookie.getValue());
                    }
                }
                else {
                    map = new HashMap(0);
                }
            }
            catch (final Exception ex) {}
        }
        return map;
    }
    
    public void addPathParam(final Map<String, Optional<String>> right) {
        this.i = right;
    }
    
    public Map<String, Optional<String>> getPathParamMap() {
        return this.i;
    }
    
    static {
        a = MinimaLogger.getLog(HttpCall.class);
    }
}
