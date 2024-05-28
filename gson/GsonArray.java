// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.gson;

import java.util.NoSuchElementException;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonElement;
import java.util.Iterator;
import java.util.Collection;
import com.google.gson.JsonArray;

public class GsonArray
{
    final JsonArray a;
    
    public GsonArray() {
        this(new JsonArray());
    }
    
    public GsonArray(final JsonArray jsonArray) {
        if (jsonArray == null) {
            throw new IllegalArgumentException();
        }
        this.a = jsonArray;
    }
    
    public GsonArray(final String json) {
        this(GsonBuilder.a(json).getAsJsonArray());
    }
    
    public GsonArray(final Collection<String> values) {
        this();
        final Iterator<String> iterator = values.iterator();
        while (iterator.hasNext()) {
            this.a.add((String)iterator.next());
        }
    }
    
    public GsonArray(String[] values) {
        this();
        values = values;
        for (int length = values.length, i = 0; i < length; ++i) {
            this.a.add(values[i]);
        }
    }
    
    public GsonArray(int[] values) {
        this();
        values = values;
        for (int length = values.length, i = 0; i < length; ++i) {
            this.a.add((Number)values[i]);
        }
    }
    
    public String getString(final int index) {
        return this.a(index).getAsString();
    }
    
    public int getInt(final int index) {
        return this.a(index).getAsInt();
    }
    
    public long getLong(final int index) {
        return this.a(index).getAsLong();
    }
    
    public double getDouble(final int index) {
        return this.a(index).getAsDouble();
    }
    
    public boolean getBoolean(final int index) {
        return this.a(index).getAsBoolean();
    }
    
    public byte getByte(final int index) {
        return this.a(index).getAsByte();
    }
    
    public float getFloat(final int index) {
        return this.a(index).getAsFloat();
    }
    
    public char getChar(final int index) {
        return this.a(index).getAsCharacter();
    }
    
    public GsonObject getJson(final int index) {
        return new GsonObject(this.a(index).getAsJsonObject());
    }
    
    public GsonArray getArray(final int index) {
        return new GsonArray(this.a(index).getAsJsonArray());
    }
    
    public GsonObject optJson(final int index) {
        final JsonElement optElem;
        if (GsonObject.isNull(optElem = this.optElem(index))) {
            return null;
        }
        return new GsonObject(optElem.getAsJsonObject());
    }
    
    public String optString(final int index) {
        return this.optString(index, null);
    }
    
    public byte optByte(final int index, final byte defaultValue) {
        final JsonElement optElem;
        if (GsonObject.isNull(optElem = this.optElem(index))) {
            return defaultValue;
        }
        return optElem.getAsByte();
    }
    
    public int optInteger(final int index, final int defaultValue) {
        final JsonElement optElem;
        if (GsonObject.isNull(optElem = this.optElem(index))) {
            return defaultValue;
        }
        return optElem.getAsInt();
    }
    
    public long optLong(final int index, final long defaultValue) {
        final JsonElement optElem;
        if (GsonObject.isNull(optElem = this.optElem(index))) {
            return defaultValue;
        }
        return optElem.getAsLong();
    }
    
    public float optFloat(final int index, final float defaultValue) {
        final JsonElement optElem;
        if (GsonObject.isNull(optElem = this.optElem(index))) {
            return defaultValue;
        }
        return optElem.getAsFloat();
    }
    
    public double optDouble(final int index, final double defaultValue) {
        final JsonElement optElem;
        if (GsonObject.isNull(optElem = this.optElem(index))) {
            return defaultValue;
        }
        return optElem.getAsDouble();
    }
    
    public boolean optBoolean(final int index, final boolean defaultValue) {
        final JsonElement optElem;
        if (GsonObject.isNull(optElem = this.optElem(index))) {
            return defaultValue;
        }
        return optElem.getAsBoolean();
    }
    
    public String optString(final int index, final String defaultValue) {
        final JsonElement optElem;
        if (GsonObject.isNull(optElem = this.optElem(index))) {
            return defaultValue;
        }
        return optElem.getAsString();
    }
    
    public GsonArray set(final int index, final String value) {
        this.a.set(index, (JsonElement)new JsonPrimitive(value));
        return this;
    }
    
    public GsonArray set(final int index, final byte value) {
        this.a.set(index, (JsonElement)new JsonPrimitive((Number)value));
        return this;
    }
    
    public GsonArray set(final int index, final int value) {
        this.a.set(index, (JsonElement)new JsonPrimitive((Number)value));
        return this;
    }
    
    public GsonArray set(final int index, final long value) {
        this.a.set(index, (JsonElement)new JsonPrimitive((Number)value));
        return this;
    }
    
    public GsonArray set(final int index, final boolean value) {
        this.a.set(index, (JsonElement)new JsonPrimitive(Boolean.valueOf(value)));
        return this;
    }
    
    public GsonArray set(final int index, final float value) {
        this.a.set(index, (JsonElement)new JsonPrimitive((Number)value));
        return this;
    }
    
    public GsonArray set(final int index, final double value) {
        this.a.set(index, (JsonElement)new JsonPrimitive((Number)value));
        return this;
    }
    
    public GsonArray set(final int index, final GsonArray value) {
        this.a.set(index, (JsonElement)((value == null) ? JsonNull.INSTANCE : value.a));
        return this;
    }
    
    public GsonArray set(final int index, final GsonObject value) {
        this.a.set(index, (JsonElement)((value == null) ? JsonNull.INSTANCE : value.a));
        return this;
    }
    
    public GsonArray add(final String value) {
        this.a.add(value);
        return this;
    }
    
    public GsonArray add(final int value) {
        this.a.add((Number)value);
        return this;
    }
    
    public GsonArray add(final long value) {
        this.a.add((Number)value);
        return this;
    }
    
    public GsonArray add(final double value) {
        this.a.add((Number)value);
        return this;
    }
    
    public GsonArray add(final float value) {
        this.a.add((Number)value);
        return this;
    }
    
    public GsonArray add(final boolean value) {
        this.a.add(Boolean.valueOf(value));
        return this;
    }
    
    public GsonArray add(final byte value) {
        this.a.add((Number)value);
        return this;
    }
    
    public GsonArray add(final char value) {
        this.a.add(Character.valueOf(value));
        return this;
    }
    
    public GsonArray add(final GsonDto json) {
        return this.add((json == null) ? null : json.toGson());
    }
    
    public GsonArray add(final GsonObject json) {
        this.a.add((JsonElement)((json == null) ? JsonNull.INSTANCE : json.a));
        return this;
    }
    
    public GsonArray add(final GsonArray array) {
        this.a.add((JsonElement)((array == null) ? JsonNull.INSTANCE : array.a));
        return this;
    }
    
    public GsonArray add(final int index, final GsonObject json) {
        if (index < 0) {
            throw new GsonException("GsonArray[" + index + "] not found.");
        }
        final Object o = (json == null) ? JsonNull.INSTANCE : json.a;
        if (index < this.size()) {
            this.a.set(index, (JsonElement)o);
        }
        else {
            while (index != this.size()) {
                this.a.add((JsonElement)JsonNull.INSTANCE);
            }
            this.a.add((JsonElement)o);
        }
        return this;
    }
    
    public void remove(final int index) {
        this.a.remove(index);
    }
    
    public Iterable<GsonObject> getAllObjects() {
        return new Iterable<GsonObject>() {
            @Override
            public final Iterator<GsonObject> iterator() {
                return new a(GsonArray.this.a);
            }
        };
    }
    
    public int size() {
        return this.a.size();
    }
    
    public boolean isNull(final int index) {
        return index < 0 || index >= this.a.size() || GsonObject.isNull(this.a.get(index));
    }
    
    @Override
    public String toString() {
        return this.a.toString();
    }
    
    private JsonElement a(final int index) {
        final JsonElement optElem;
        if (GsonObject.isNull(optElem = this.optElem(index))) {
            throw new GsonException("Index, " + index + ", element is null");
        }
        return optElem;
    }
    
    public JsonElement optElem(final int index) {
        final JsonElement value;
        if ((index >= 0 || index < this.a.size()) && !GsonObject.isNull(value = this.a.get(index))) {
            return value;
        }
        return null;
    }
    
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
}
