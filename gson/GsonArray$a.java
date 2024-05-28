// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.gson;

import java.util.NoSuchElementException;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import java.util.Iterator;

private static final class a implements Iterator<GsonObject>
{
    private final JsonArray a;
    private int b;
    private GsonObject c;
    
    public a(final JsonArray a) {
        this.a = a;
        this.a();
    }
    
    @Override
    public final boolean hasNext() {
        return this.c != null;
    }
    
    private void a() {
        this.c = null;
        for (int i = this.b; i < this.a.size(); ++i) {
            final JsonElement value;
            if ((value = this.a.get(i)) != null && value.isJsonObject()) {
                this.c = new GsonObject(value.getAsJsonObject());
                this.b = i + 1;
                return;
            }
        }
        this.b = this.a.size();
    }
}
