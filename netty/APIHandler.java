// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.netty;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;
import io.netty.handler.codec.http.HttpContent;
import java.util.List;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.util.Map;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpUtil;
import java.util.NoSuchElementException;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.channel.ChannelHandlerContext;
import com.yojito.minima.logging.MinimaLogger;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.channel.SimpleChannelInboundHandler;

public class APIHandler extends SimpleChannelInboundHandler<Object>
{
    private HttpRequest a;
    private HttpCall b;
    private static final MinimaLogger c;
    
    public APIHandler() {
        this.b = new HttpCall();
    }
    
    public void channelReadComplete(final ChannelHandlerContext ctx) {
        final Attribute attr = ctx.attr((AttributeKey)GsonHandler.a);
        boolean booleanValue = false;
        try {
            booleanValue = (boolean)attr.get();
        }
        catch (final NullPointerException ex) {}
        if (APIHandler.c.isTraceEnabled()) {
            APIHandler.c.trace("Called :: channelReadComplete :: isStream : %b", booleanValue);
        }
        ctx.flush();
    }
    
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        boolean b = true;
        final Attribute attr = ctx.attr((AttributeKey)GsonHandler.a);
        boolean booleanValue = false;
        try {
            booleanValue = (boolean)attr.get();
        }
        catch (final NullPointerException t) {
            APIHandler.c.error(t, "Error while reading netty context attribute", new Object[0]);
        }
        if (booleanValue && cause instanceof NoSuchElementException && cause.getStackTrace()[7].getClassName().equals(APIHandler.class.getCanonicalName())) {
            APIHandler.c.trace("SKIPPING ERROR PRINTING FOR KNOWN BUG SCENARIO", new Object[0]);
            b = false;
        }
        if (b) {
            APIHandler.c.error(cause, "Error at Netty Layer", new Object[0]);
        }
        ctx.close();
    }
    
    protected void channelRead0(final ChannelHandlerContext ctx, Object msg) throws Exception {
        if (APIHandler.c.isTraceEnabled()) {
            APIHandler.c.trace("channelRead0::Message >>>> " + msg, new Object[0]);
        }
        if (msg instanceof HttpRequest) {
            final HttpRequest a = (HttpRequest)msg;
            this.a = a;
            final HttpRequest request = a;
            this.b.setRequest(request);
            this.b.setCtx(ctx);
            if (HttpUtil.is100ContinueExpected((HttpMessage)request)) {
                ctx.write((Object)new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE, Unpooled.EMPTY_BUFFER));
            }
            this.b.setHeaders((Iterable<Map.Entry<String, String>>)request.headers());
            final Map parameters = ((QueryStringDecoder)(msg = new QueryStringDecoder(request.uri()))).parameters();
            this.b.setUri(((QueryStringDecoder)msg).path());
            this.b.setQueryParams(parameters);
        }
        if (msg instanceof HttpContent) {
            final ByteBuf content;
            if ((content = ((HttpContent)msg).content()).isReadable()) {
                this.b.appendContent(content.toString(CharsetUtil.UTF_8));
            }
            if (msg instanceof LastHttpContent) {
                this.b.setTrailer((LastHttpContent)msg);
                GsonHandler.handle(this.b);
                final boolean keepAlive;
                if (keepAlive = HttpUtil.isKeepAlive((HttpMessage)this.a)) {
                    this.b = new HttpCall();
                }
            }
        }
    }
    
    static {
        c = MinimaLogger.getLog(APIHandler.class);
    }
}
