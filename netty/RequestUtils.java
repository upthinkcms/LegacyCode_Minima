// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.netty;

import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpObject;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import io.netty.handler.codec.http.HttpContent;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.HttpRequest;

public class RequestUtils
{
    static StringBuilder a(final HttpRequest httpRequest) {
        final StringBuilder sb = new StringBuilder();
        final Map parameters;
        if (!(parameters = new QueryStringDecoder(httpRequest.uri()).parameters()).isEmpty()) {
            final Iterator iterator = parameters.entrySet().iterator();
            while (iterator.hasNext()) {
                final Map.Entry entry;
                final String s = (String)(entry = (Map.Entry)iterator.next()).getKey();
                final Iterator iterator2 = ((List)entry.getValue()).iterator();
                while (iterator2.hasNext()) {
                    sb.append("Parameter: ").append(s.toUpperCase()).append(" = ").append(((String)iterator2.next()).toUpperCase()).append("\r\n");
                }
            }
            sb.append("\r\n");
        }
        return sb;
    }
    
    static StringBuilder a(final HttpContent httpContent) {
        final StringBuilder sb = new StringBuilder();
        final ByteBuf content;
        if ((content = httpContent.content()).isReadable()) {
            sb.append(content.toString(CharsetUtil.UTF_8).toUpperCase());
            sb.append("\r\n");
        }
        return sb;
    }
    
    static StringBuilder a(final HttpObject httpObject) {
        final StringBuilder sb = new StringBuilder();
        final DecoderResult decoderResult;
        if (!(decoderResult = httpObject.decoderResult()).isSuccess()) {
            sb.append("..Decoder Failure: ");
            sb.append(decoderResult.cause());
            sb.append("\r\n");
        }
        return sb;
    }
    
    static StringBuilder a(final LastHttpContent lastHttpContent) {
        final StringBuilder sb;
        (sb = new StringBuilder()).append("Good Bye!\r\n");
        if (!lastHttpContent.trailingHeaders().isEmpty()) {
            sb.append("\r\n");
            for (final CharSequence s : lastHttpContent.trailingHeaders().names()) {
                for (final CharSequence s2 : lastHttpContent.trailingHeaders().getAll(s)) {
                    sb.append("P.S. Trailing Header: ");
                    sb.append(s).append(" = ").append(s2).append("\r\n");
                }
            }
            sb.append("\r\n");
        }
        return sb;
    }
}
