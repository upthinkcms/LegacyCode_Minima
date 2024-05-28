// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.gson;

import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonSerializer;
import java.util.Date;
import com.google.gson.JsonDeserializer;

private static final class c implements JsonDeserializer<Date>, JsonSerializer<Date>
{
}
