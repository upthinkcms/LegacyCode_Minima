// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.netty;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.util.CharsetUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.channel.SimpleChannelInboundHandler;

public class _APIHandler extends SimpleChannelInboundHandler<Object>
{
    private HttpRequest a;
    private StringBuilder b;
    
    public _APIHandler() {
        this.b = new StringBuilder();
    }
    
    public void channelReadComplete(final ChannelHandlerContext ctx) {
        ctx.flush();
    }
    
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {
            final HttpRequest a = (HttpRequest)msg;
            this.a = a;
            final HttpMessage httpMessage = (HttpMessage)a;
            if (HttpUtil.is100ContinueExpected((HttpMessage)a)) {
                ctx.write((Object)new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE, Unpooled.EMPTY_BUFFER));
            }
            this.b.setLength();
            this.b.append((CharSequence)RequestUtils.a((HttpRequest)httpMessage));
        }
        final StringBuilder a2 = RequestUtils.a((HttpObject)this.a);
        System.out.printf(">>>>>>>>>> s1 => %s\n", a2);
        this.b.append((CharSequence)a2);
        if (msg instanceof HttpContent) {
            final StringBuilder a3 = RequestUtils.a((HttpContent)msg);
            System.out.printf(">>>>>>>>>> body => %s\n", a3);
            this.b.append((CharSequence)a3);
            final StringBuilder a4 = RequestUtils.a((HttpObject)this.a);
            System.out.printf(">>>>>>>>>> decoderResult => %s\n", a4);
            this.b.append((CharSequence)a4);
            if (msg instanceof LastHttpContent) {
                final LastHttpContent lastHttpContent;
                final StringBuilder a5 = RequestUtils.a(lastHttpContent = (LastHttpContent)msg);
                System.out.printf(">>>>>>>>>> lastResponse => %s\n", a5);
                this.b.append((CharSequence)a5);
                final HttpObject httpObject = (HttpObject)lastHttpContent;
                final StringBuilder b = this.b;
                final HttpObject httpObject2 = httpObject;
                ctx = ctx;
                final boolean keepAlive = HttpUtil.isKeepAlive((HttpMessage)this.a);
                ((FullHttpResponse)(msg = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, httpObject2.decoderResult().isSuccess() ? HttpResponseStatus.OK : HttpResponseStatus.BAD_REQUEST, Unpooled.copiedBuffer((CharSequence)b.toString(), CharsetUtil.UTF_8)))).headers().set((CharSequence)HttpHeaderNames.CONTENT_TYPE, (Object)"text/plain; charset=UTF-8");
                if (keepAlive) {
                    ((FullHttpResponse)msg).headers().setInt((CharSequence)HttpHeaderNames.CONTENT_LENGTH, ((FullHttpResponse)msg).content().readableBytes());
                    ((FullHttpResponse)msg).headers().set((CharSequence)HttpHeaderNames.CONNECTION, (Object)HttpHeaderValues.KEEP_ALIVE);
                }
                ctx.write(msg);
                if (!keepAlive) {
                    ctx.writeAndFlush((Object)Unpooled.EMPTY_BUFFER).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
                }
            }
        }
    }
    
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
