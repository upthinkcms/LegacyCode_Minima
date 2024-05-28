// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.gson;

public class GsonDto
{
    public final GsonObject toGson() {
        return GsonBuilder.b(this);
    }
    
    public final String toJson() {
        return GsonBuilder.a(this);
    }
    
    public boolean isError() {
        return false;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " ** " + this.toJson();
    }
    
    public String toStringPretty() {
        return GsonBuilder.prettyPrint(this);
    }
    
    public static <T extends GsonDto> T fromGson(final GsonObject json, final String classOfTName) {
        try {
            return fromGson(json, Class.forName(classOfTName));
        }
        catch (final ClassNotFoundException cause) {
            throw new RuntimeException(cause);
        }
    }
    
    public static <T extends GsonDto> T fromGson(final GsonObject json, final Class<T> classOfT) {
        return GsonBuilder.a(json, classOfT);
    }
    
    public static <T extends GsonDto> T fromJson(final String json, final String classOfT) {
        try {
            return fromJson(json, Class.forName(classOfT));
        }
        catch (final ClassNotFoundException cause) {
            throw new RuntimeException(cause);
        }
    }
    
    public static <T extends GsonDto> T fromJson(final String json, final Class<T> classOfT) {
        return GsonBuilder.a(json, classOfT);
    }
}
