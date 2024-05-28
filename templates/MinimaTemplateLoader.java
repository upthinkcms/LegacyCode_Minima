// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.templates;

import java.util.List;
import com.mitchellbosecke.pebble.loader.Loader;

public interface MinimaTemplateLoader<T> extends Loader<T>
{
    void addTemplate(final String p0, final String p1);
    
    List<String> getTemplateList();
}
