// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.gson;

import com.google.gson.JsonPrimitive;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import java.util.HashMap;
import com.google.gson.JsonElement;
import java.util.Iterator;
import com.google.gson.TypeAdapterFactory;
import java.lang.reflect.Type;
import java.util.Date;
import com.google.gson.Gson;
import java.util.Map;
import com.google.gson.JsonParser;

public class GsonBuilder
{
    private static final JsonParser a;
    private static Map<String, WF_RuntimeTypeAdapterFactory> b;
    private static Gson c;
    private static Gson d;
    
    public static void registerTypeFactory(final String name, final WF_RuntimeTypeAdapterFactory factory) {
        GsonBuilder.b.put(name, factory);
        GsonBuilder.c = a(false);
        GsonBuilder.d = a(true);
    }
    
    private static Gson a(final boolean b) {
        final com.google.gson.GsonBuilder registerTypeAdapter = new com.google.gson.GsonBuilder().registerTypeAdapter((Type)Date.class, (Object)new c()).registerTypeAdapter((Type)java.sql.Date.class, (Object)new c()).registerTypeAdapter((Type)GsonObject.class, (Object)new d()).registerTypeAdapter((Type)GsonArray.class, (Object)new b()).registerTypeAdapter((Type)Class.class, (Object)new a());
        final Iterator<WF_RuntimeTypeAdapterFactory> iterator = GsonBuilder.b.values().iterator();
        while (iterator.hasNext()) {
            registerTypeAdapter.registerTypeAdapterFactory((TypeAdapterFactory)iterator.next());
        }
        if (b) {
            registerTypeAdapter.setPrettyPrinting();
        }
        return registerTypeAdapter.create();
    }
    
    static JsonElement a(final String s) {
        try {
            return GsonBuilder.a.parse(s);
        }
        catch (final Exception e) {
            throw new GsonException(e);
        }
    }
    
    public static String prettyPrint(final GsonDto gsonDto) {
        return GsonBuilder.d.toJson((Object)gsonDto);
    }
    
    static String a(final Object o) {
        return GsonBuilder.c.toJson(o);
    }
    
    static GsonObject b(final Object o) {
        return new GsonObject(GsonBuilder.c.toJsonTree(o).getAsJsonObject());
    }
    
    static <T> T a(final GsonObject gsonObject, final Class<T> clazz) {
        if (gsonObject == null) {
            return null;
        }
        return (T)GsonBuilder.c.fromJson((JsonElement)gsonObject.a, (Class)clazz);
    }
    
    static <T> T a(final String s, final Class<T> clazz) {
        return (T)GsonBuilder.c.fromJson(s, (Class)clazz);
    }
    
    static {
        a = new JsonParser();
        GsonBuilder.b = new HashMap<String, WF_RuntimeTypeAdapterFactory>();
        GsonBuilder.c = a(false);
        GsonBuilder.d = a(true);
    }
    
    private static final class d implements JsonDeserializer<GsonObject>, JsonSerializer<GsonObject>
    {
    }
    
    private static final class b implements JsonDeserializer<GsonArray>, JsonSerializer<GsonArray>
    {
    }
    
    private static final class c implements JsonDeserializer<Date>, JsonSerializer<Date>
    {
    }
    
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
}
