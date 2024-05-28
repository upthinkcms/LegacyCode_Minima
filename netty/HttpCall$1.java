// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.netty;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

final class HttpCall$1 implements GenericFutureListener {
    private /* synthetic */ boolean a;
    
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
}