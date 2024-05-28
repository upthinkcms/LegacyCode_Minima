// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.gson;

import java.util.Iterator;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.util.LinkedHashMap;
import java.util.Map;
import com.google.gson.TypeAdapterFactory;

public final class WF_RuntimeTypeAdapterFactory<T> implements TypeAdapterFactory
{
    private final Class<?> a;
    private final String b;
    private final Map<String, Class<?>> c;
    private final Map<Class<?>, String> d;
    private final boolean e;
    
    private WF_RuntimeTypeAdapterFactory(final Class<?> baseType, final String typeFieldName, final boolean maintainType) {
        this.c = new LinkedHashMap<String, Class<?>>();
        this.d = new LinkedHashMap<Class<?>, String>();
        if (typeFieldName == null || baseType == null) {
            throw new NullPointerException();
        }
        this.a = baseType;
        this.b = typeFieldName;
        this.e = maintainType;
    }
    
    public static <T> WF_RuntimeTypeAdapterFactory<T> of(final Class<T> baseType, final String typeFieldName, final boolean maintainType) {
        return new WF_RuntimeTypeAdapterFactory<T>(baseType, typeFieldName, maintainType);
    }
    
    public static <T> WF_RuntimeTypeAdapterFactory<T> of(final Class<T> baseType, final String typeFieldName) {
        return new WF_RuntimeTypeAdapterFactory<T>(baseType, typeFieldName, false);
    }
    
    public static <T> WF_RuntimeTypeAdapterFactory<T> of(final Class<T> baseType) {
        return new WF_RuntimeTypeAdapterFactory<T>(baseType, "type", false);
    }
    
    public final WF_RuntimeTypeAdapterFactory<T> registerSubtype(final Class<? extends T> type, final String label) {
        if (type == null || label == null) {
            throw new NullPointerException();
        }
        if (this.d.containsKey(type) || this.c.containsKey(label)) {
            throw new IllegalArgumentException("types and labels must be unique");
        }
        this.c.put(label, type);
        this.d.put(type, label);
        return this;
    }
    
    public final WF_RuntimeTypeAdapterFactory<T> registerSubtype(final Class<? extends T> type) {
        return this.registerSubtype(type, type.getSimpleName());
    }
    
    public final <R> TypeAdapter<R> create(final Gson gson, final TypeToken<R> type) {
        if (type.getRawType() != this.a) {
            return null;
        }
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        final LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (final Map.Entry entry : this.c.entrySet()) {
            final TypeAdapter delegateAdapter = gson.getDelegateAdapter((TypeAdapterFactory)this, TypeToken.get((Class)entry.getValue()));
            linkedHashMap.put(entry.getKey(), delegateAdapter);
            linkedHashMap2.put(entry.getValue(), delegateAdapter);
        }
        return (TypeAdapter<R>)new TypeAdapter<R>() {
            public final R read(final JsonReader in) throws IOException {
                final JsonElement parse = Streams.parse(in);
                JsonElement jsonElement;
                if (WF_RuntimeTypeAdapterFactory.this.e) {
                    jsonElement = parse.getAsJsonObject().get(WF_RuntimeTypeAdapterFactory.this.b);
                }
                else {
                    jsonElement = parse.getAsJsonObject().remove(WF_RuntimeTypeAdapterFactory.this.b);
                }
                if (jsonElement == null) {
                    throw new JsonParseException("cannot deserialize " + WF_RuntimeTypeAdapterFactory.this.a + " because it does not define a field named " + WF_RuntimeTypeAdapterFactory.this.b);
                }
                final String asString = jsonElement.getAsString();
                final TypeAdapter typeAdapter;
                if ((typeAdapter = linkedHashMap.get(asString)) == null) {
                    throw new JsonParseException("cannot deserialize " + WF_RuntimeTypeAdapterFactory.this.a + " subtype named " + asString + "; did you forget to register a subtype?");
                }
                return (R)typeAdapter.fromJsonTree(parse);
            }
            
            public final void write(final JsonWriter out, final R value) throws IOException {
                final Class<?> class1 = value.getClass();
                final String s = WF_RuntimeTypeAdapterFactory.this.d.get(class1);
                final TypeAdapter typeAdapter;
                if ((typeAdapter = linkedHashMap2.get(class1)) == null) {
                    throw new JsonParseException("cannot serialize " + class1.getName() + "; did you forget to register a subtype?");
                }
                final JsonObject asJsonObject = typeAdapter.toJsonTree((Object)value).getAsJsonObject();
                if (WF_RuntimeTypeAdapterFactory.this.e) {
                    Streams.write((JsonElement)asJsonObject, out);
                    return;
                }
                final JsonObject jsonObject = new JsonObject();
                if (asJsonObject.has(WF_RuntimeTypeAdapterFactory.this.b)) {
                    throw new JsonParseException("cannot serialize " + class1.getName() + " because it already defines a field named " + WF_RuntimeTypeAdapterFactory.this.b);
                }
                jsonObject.add(WF_RuntimeTypeAdapterFactory.this.b, (JsonElement)new JsonPrimitive(s));
                for (final Map.Entry entry : asJsonObject.entrySet()) {
                    jsonObject.add((String)entry.getKey(), (JsonElement)entry.getValue());
                }
                Streams.write((JsonElement)jsonObject, out);
            }
        }.nullSafe();
    }
}
