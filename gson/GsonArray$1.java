// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.gson;

import java.util.Iterator;

final class GsonArray$1 implements Iterable<GsonObject> {
    @Override
    public final Iterator<GsonObject> iterator() {
        return new a(GsonArray.this.a);
    }
}