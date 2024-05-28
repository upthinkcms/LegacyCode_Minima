// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonParseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;

private static final class a implements JsonDeserializer<Class<?>>, JsonSerializer<Class<?>>
{
    private static Class<?> a(final JsonElement jsonElement) throws JsonParseException {
        if (jsonElement.isJsonNull()) {
            return null;
        }
        try {
            return Class.forName(jsonElement.getAsString());
        }
        catch (final ClassNotFoundException ex) {
            return null;
        }
    }
}
