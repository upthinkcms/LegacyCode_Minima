// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.netty;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelHandler;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.EventLoopGroup;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyHttpServer
{
    private final int a;
    
    public NettyHttpServer(final int port) {
        this.a = port;
    }
    
    public void run() throws Exception {
        this.run(null);
    }
    
    public void run(final ChannelInboundHandlerAdapter handlerAdapter) throws Exception {
        final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(1);
        final NioEventLoopGroup nioEventLoopGroup2 = new NioEventLoopGroup();
        try {
            final ServerBootstrap serverBootstrap;
            ((ServerBootstrap)((ServerBootstrap)(serverBootstrap = new ServerBootstrap()).group((EventLoopGroup)nioEventLoopGroup, (EventLoopGroup)nioEventLoopGroup2).channel((Class)NioServerSocketChannel.class)).handler((ChannelHandler)new LoggingHandler(LogLevel.INFO))).childHandler((ChannelHandler)new ChannelInitializer<SocketChannel>(this) {});
            serverBootstrap.bind(this.a).sync().channel().closeFuture().sync();
        }
        finally {
            ((EventLoopGroup)nioEventLoopGroup).shutdownGracefully();
            ((EventLoopGroup)nioEventLoopGroup2).shutdownGracefully();
        }
    }
}
