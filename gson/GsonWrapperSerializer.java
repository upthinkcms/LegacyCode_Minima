// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonSerializer;

public abstract class GsonWrapperSerializer<T> extends GsonWrapperDeserializer<T> implements JsonSerializer<T>
{
    public final JsonElement serialize(final T obj, final Type type, final JsonSerializationContext context) {
        return (JsonElement)this.toGson(obj).a;
    }
    
    public abstract GsonObject toGson(final T p0);
}
