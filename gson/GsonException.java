// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.gson;

public class GsonException extends RuntimeException
{
    public GsonException(final String msg) {
        super(msg);
    }
    
    public GsonException(final Exception e) {
        super(e);
    }
}
