// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima;

public class MinimaException extends RuntimeException
{
    public MinimaException(final Throwable e) {
        super(e);
    }
    
    public MinimaException(final String error) {
        super(error);
    }
}
