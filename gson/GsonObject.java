// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.gson;

import java.util.Collection;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonElement;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import com.google.gson.JsonObject;

public final class GsonObject extends GsonDto
{
    JsonObject a;
    
    public GsonObject() {
        this(new JsonObject());
    }
    
    public GsonObject(final String json) {
        this(GsonBuilder.a(json).getAsJsonObject());
    }
    
    public GsonObject(final JsonObject jsonObj) {
        if (jsonObj == null) {
            throw new IllegalArgumentException();
        }
        this.a = jsonObj;
    }
    
    public GsonObject(final Map<String, String> stringMap) {
        this();
        if (stringMap != null) {
            for (final Map.Entry entry : stringMap.entrySet()) {
                this.a.addProperty((String)entry.getKey(), (String)entry.getValue());
            }
        }
    }
    
    public final JsonObject getJsonObject() {
        return this.a;
    }
    
    public final GsonArray keyArray() {
        final GsonArray gsonArray = new GsonArray();
        final Iterator<String> iterator = this.keySet().iterator();
        while (iterator.hasNext()) {
            gsonArray.add(iterator.next());
        }
        return gsonArray;
    }
    
    public final boolean has(final String key) {
        return !this.isNull(key);
    }
    
    public final int length() {
        return this.a.size();
    }
    
    public final boolean isNull(final String key) {
        return isNull(this.a.get(key));
    }
    
    public final Set<String> keySet() {
        return this.a.keySet();
    }
    
    public final void remove(final String key) {
        this.a.remove(key);
    }
    
    public final boolean isPrimitive(final String key) {
        final JsonElement value;
        return (value = this.a.get(key)) != null && !value.isJsonNull() && value.isJsonPrimitive();
    }
    
    public final boolean isArray(final String key) {
        final JsonElement value;
        return (value = this.a.get(key)) != null && !value.isJsonNull() && value.isJsonArray();
    }
    
    public final boolean isJsonObj(final String key) {
        final JsonElement value;
        return (value = this.a.get(key)) != null && !value.isJsonNull() && value.isJsonObject();
    }
    
    public final Object getPrimitive(final String key) {
        final JsonPrimitive asJsonPrimitive;
        if ((asJsonPrimitive = this.a(key).getAsJsonPrimitive()).isBoolean()) {
            return asJsonPrimitive.getAsBoolean();
        }
        if (asJsonPrimitive.isString()) {
            return asJsonPrimitive.getAsString();
        }
        if (asJsonPrimitive.isNumber()) {
            return asJsonPrimitive.getAsNumber();
        }
        throw new RuntimeException("Invalid type");
    }
    
    public final String getString(final String key) {
        return this.a(key).getAsString();
    }
    
    public final GsonObject getJson(final String key) {
        return new GsonObject(this.a(key).getAsJsonObject());
    }
    
    public final boolean getBoolean(final String key) {
        return this.a(key).getAsBoolean();
    }
    
    public final int getInt(final String key) {
        return this.a(key).getAsInt();
    }
    
    public final long getLong(final String key) {
        return this.a(key).getAsLong();
    }
    
    public final byte getByte(final String key) {
        return this.a(key).getAsByte();
    }
    
    public final float getFloat(final String key) {
        return this.a(key).getAsFloat();
    }
    
    public final double getDouble(final String key) {
        return this.a(key).getAsDouble();
    }
    
    public final String[] getStringArray(final String key) {
        final GsonArray array2;
        final String[] array = new String[(array2 = this.getArray(key)).size()];
        for (int i = 0; i < array2.size(); ++i) {
            array[i] = array2.getString(i);
        }
        return array;
    }
    
    public final GsonArray getArray(final String key) {
        return new GsonArray(this.a(key).getAsJsonArray());
    }
    
    public final boolean optBoolean(final String key) {
        return this.optBoolean(key, false);
    }
    
    public final boolean optBoolean(final String key, final boolean defaultValue) {
        final JsonElement value;
        if (isNull(value = this.a.get(key))) {
            return defaultValue;
        }
        return value.getAsBoolean();
    }
    
    public final byte optByte(final String key) {
        return this.optByte(key, (byte)0);
    }
    
    public final byte optByte(final String key, final byte defaultValue) {
        final JsonElement value;
        if (isNull(value = this.a.get(key))) {
            return defaultValue;
        }
        return value.getAsByte();
    }
    
    public final int optInt(final String key) {
        return this.optInt(key, 0);
    }
    
    public final int optInt(final String key, final int defaultValue) {
        final JsonElement value;
        if (isNull(value = this.a.get(key))) {
            return defaultValue;
        }
        return value.getAsInt();
    }
    
    public final long optLong(final String key) {
        return this.optLong(key, 0L);
    }
    
    public final long optLong(final String key, final long defaultValue) {
        final JsonElement value;
        if (isNull(value = this.a.get(key))) {
            return defaultValue;
        }
        return value.getAsLong();
    }
    
    public final String optString(final String key) {
        return this.optString(key, null);
    }
    
    public final String optString(final String key, final String defaultValue) {
        final JsonElement value;
        if (isNull(value = this.a.get(key))) {
            return defaultValue;
        }
        return value.getAsString();
    }
    
    public final float optFloat(final String key) {
        return this.optFloat(key, 0.0f);
    }
    
    public final float optFloat(final String key, final float defaultValue) {
        final JsonElement value;
        if (isNull(value = this.a.get(key))) {
            return defaultValue;
        }
        return value.getAsFloat();
    }
    
    public final double optDouble(final String key) {
        return this.optDouble(key, Double.NaN);
    }
    
    public final double optDouble(final String key, final double defaultValue) {
        final JsonElement value;
        if (isNull(value = this.a.get(key))) {
            return defaultValue;
        }
        return value.getAsDouble();
    }
    
    public final GsonObject optJson(final String key) {
        final JsonElement value;
        if (isNull(value = this.a.get(key))) {
            return null;
        }
        return new GsonObject(value.getAsJsonObject());
    }
    
    public final String[] optStringArray(final String key) {
        final GsonArray optArray;
        if ((optArray = this.optArray(key)) == null) {
            return null;
        }
        final String[] array = new String[optArray.size()];
        for (int i = 0; i < optArray.size(); ++i) {
            array[i] = optArray.optString(i);
        }
        return array;
    }
    
    public final GsonArray optArray(final String key) {
        final JsonElement value;
        if (isNull(value = this.a.get(key))) {
            return null;
        }
        return new GsonArray(value.getAsJsonArray());
    }
    
    public final GsonObject put(final String key, final GsonDto value) {
        return this.put(key, (value == null) ? null : value.toGson());
    }
    
    public final void put(final String name, final JsonElement jsonElement) {
        this.a.add(name, jsonElement);
    }
    
    public final GsonObject put(final String key, final GsonObject value) {
        if (value == null) {
            this.a.remove(key);
        }
        else {
            this.a.add(key, (JsonElement)value.a);
        }
        return this;
    }
    
    public final GsonObject putEvenIfNull(final String key, final GsonObject value) {
        this.a.add(key, (JsonElement)((value == null) ? null : value.a));
        return this;
    }
    
    public final GsonObject put(final String key, final Boolean value) {
        this.a.addProperty(key, value);
        return this;
    }
    
    public final GsonObject put(final String key, final Byte value) {
        this.a.addProperty(key, (Number)value);
        return this;
    }
    
    public final GsonObject put(final String key, final Integer value) {
        this.a.addProperty(key, (Number)value);
        return this;
    }
    
    public final GsonObject put(final String key, final Long value) {
        this.a.addProperty(key, (Number)value);
        return this;
    }
    
    public final GsonObject put(final String key, final Float value) {
        this.a.addProperty(key, (Number)value);
        return this;
    }
    
    public final GsonObject put(final String key, final Double value) {
        this.a.addProperty(key, (Number)value);
        return this;
    }
    
    public final GsonObject put(final String key, final String value) {
        if (value == null) {
            this.a.remove(key);
        }
        else {
            this.a.addProperty(key, value);
        }
        return this;
    }
    
    public final GsonObject putEvenIfNull(final String key, final String value) {
        this.a.addProperty(key, value);
        return this;
    }
    
    public final GsonObject put(final String key, final GsonArray value) {
        if (value == null) {
            this.a.remove(key);
        }
        else {
            this.a.add(key, (JsonElement)value.a);
        }
        return this;
    }
    
    public final GsonObject put(final String key, final Collection<String> values) {
        if (values == null) {
            this.a.remove(key);
        }
        else {
            this.a.add(key, (JsonElement)new GsonArray(values).a);
        }
        return this;
    }
    
    public final GsonObject put(final String key, final String[] values) {
        if (values == null) {
            this.a.remove(key);
        }
        else {
            this.a.add(key, (JsonElement)new GsonArray(values).a);
        }
        return this;
    }
    
    public final GsonObject put(final String key, final int[] values) {
        if (values == null) {
            this.a.remove(key);
        }
        else {
            this.a.add(key, (JsonElement)new GsonArray(values).a);
        }
        return this;
    }
    
    public final <T extends GsonDto> T toDto(final Class<T> classOfT) {
        return GsonBuilder.a(this, classOfT);
    }
    
    @Override
    public final String toString() {
        return this.a.toString();
    }
    
    private JsonElement a(final String s) {
        final JsonElement value;
        if (isNull(value = this.a.get(s))) {
            throw new GsonException("Key, " + s + ", not found in json");
        }
        return value;
    }
    
    public static boolean isNull(final JsonElement jsonElement) {
        return jsonElement == null || jsonElement.isJsonNull();
    }
    
    public static boolean isNull(JsonElement... jsonElements) {
        jsonElements = jsonElements;
        for (int length = jsonElements.length, i = 0; i < length; ++i) {
            if (isNull(jsonElements[i])) {
                return true;
            }
        }
        return false;
    }
    
    public final JsonElement getElement(final String element) {
        return this.a.get(element);
    }
}
