// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.akka;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.ExecutionException;
import com.yojito.minima.MinimaException;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;
import java.util.concurrent.TimeUnit;
import com.yojito.minima.logging.MinimaLogger;

public class ClientHelper
{
    private static final MinimaLogger a;
    private final long b;
    private final TimeUnit c;
    
    public ClientHelper(final long timeout, final TimeUnit unit) {
        this.b = timeout;
        this.c = unit;
    }
    
    public <T, U> T call(final Supplier<CompletionStage<U>> producer) {
        try {
            return (T)producer.get().toCompletableFuture().get(this.b, this.c);
        }
        catch (final InterruptedException ex) {
            ClientHelper.a.error(ex, "Error", new Object[0]);
            throw new MinimaException(ex);
        }
        catch (final ExecutionException e) {
            throw new MinimaException(e);
        }
        catch (final TimeoutException e2) {
            throw new MinimaException(e2);
        }
    }
    
    static {
        a = MinimaLogger.getLog(ClientHelper.class);
    }
}
