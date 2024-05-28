// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.templates;

import java.io.IOException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import java.util.Map;
import java.io.Writer;
import java.io.StringWriter;
import java.util.HashMap;
import com.yojito.minima.gson.GsonDto;
import com.mitchellbosecke.pebble.PebbleEngine;

public class TemplateEngine
{
    private static PebbleEngine a;
    
    public static void reconfigureEngine(final PebbleEngine engine) {
        TemplateEngine.a = engine;
    }
    
    public static PebbleEngine getInstance() {
        return TemplateEngine.a;
    }
    
    public static String getHtml(String template, final GsonDto dto) throws IOException {
        final PebbleTemplate template2 = TemplateEngine.a.getTemplate(template);
        final HashMap hashMap;
        (hashMap = new HashMap()).put("response", dto);
        final StringWriter stringWriter = new StringWriter();
        template2.evaluate((Writer)stringWriter, (Map)hashMap);
        return template = stringWriter.toString();
    }
    
    public static String getErrorHtml(final Throwable throwable) {
        final PebbleTemplate template = TemplateEngine.a.getTemplate("_minima/templates/500.html");
        final HashMap hashMap;
        (hashMap = new HashMap()).put("error", throwable.getMessage());
        final StringWriter stringWriter = new StringWriter();
        try {
            template.evaluate((Writer)stringWriter, (Map)hashMap);
        }
        catch (final IOException cause) {
            throw new RuntimeException(cause);
        }
        return stringWriter.toString();
    }
    
    static {
        TemplateEngine.a = new PebbleEngine.Builder().build();
    }
}
