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
import java.util.Map;
import com.google.gson.TypeAdapter;

final class WF_RuntimeTypeAdapterFactory$1 extends TypeAdapter<R> {
    private /* synthetic */ Map a;
    private /* synthetic */ Map b;
    
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
        if ((typeAdapter = this.a.get(asString)) == null) {
            throw new JsonParseException("cannot deserialize " + WF_RuntimeTypeAdapterFactory.this.a + " subtype named " + asString + "; did you forget to register a subtype?");
        }
        return (R)typeAdapter.fromJsonTree(parse);
    }
    
    public final void write(final JsonWriter out, final R value) throws IOException {
        final Class<?> class1 = value.getClass();
        final String s = WF_RuntimeTypeAdapterFactory.this.d.get(class1);
        final TypeAdapter typeAdapter;
        if ((typeAdapter = this.b.get(class1)) == null) {
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
}