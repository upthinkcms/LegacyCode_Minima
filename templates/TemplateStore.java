// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.templates;

import com.mitchellbosecke.pebble.loader.DelegatingLoaderCacheKey;
import java.util.function.Function;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mitchellbosecke.pebble.cache.PebbleCache;
import com.mitchellbosecke.pebble.loader.FileLoader;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import java.util.HashMap;
import java.util.ArrayList;
import com.yojito.minima.api.Context;
import com.mitchellbosecke.pebble.loader.Loader;
import java.util.List;
import com.mitchellbosecke.pebble.loader.DelegatingLoader;
import com.yojito.minima.logging.MinimaLogger;

public class TemplateStore
{
    private static final MinimaLogger a;
    private final DelegatingLoader b;
    private final List<Loader<?>> c;
    private InvalidatingConcurrentMapTemplateCache d;
    private MinimaTemplateLoader e;
    
    public TemplateStore(final Context ctx, final MinimaTemplateLoader minimaTemplateLoader) {
        this.c = new ArrayList<Loader<?>>();
        new HashMap();
        this.d = new InvalidatingConcurrentMapTemplateCache();
        if (minimaTemplateLoader != null) {
            this.c.add((Loader<?>)minimaTemplateLoader);
            this.e = minimaTemplateLoader;
        }
        this.c.add((Loader<?>)new ClasspathLoader());
        this.c.add((Loader<?>)new FileLoader());
        this.b = new DelegatingLoader((List)this.c);
    }
    
    public DelegatingLoader getDelegatingLoader() {
        return this.b;
    }
    
    public InvalidatingConcurrentMapTemplateCache getTemplateCache() {
        return this.d;
    }
    
    public void invalidate(final String templateName) {
        this.d.invalidate(templateName, this.b.createCacheKey(templateName));
    }
    
    public void addTemplate(final String templateName, final String literal) {
        this.e.addTemplate(templateName, literal);
    }
    
    static {
        a = MinimaLogger.getLog(TemplateStore.class);
    }
    
    public static class InvalidatingConcurrentMapTemplateCache implements PebbleCache<Object, PebbleTemplate>
    {
        private final ConcurrentMap<Object, PebbleTemplate> a;
        
        public InvalidatingConcurrentMapTemplateCache() {
            this.a = new ConcurrentHashMap<Object, PebbleTemplate>(200);
        }
        
        public PebbleTemplate computeIfAbsent(final Object key, final Function<? super Object, ? extends PebbleTemplate> mappingFunction) {
            if (key instanceof DelegatingLoaderCacheKey) {
                final DelegatingLoaderCacheKey delegatingLoaderCacheKey = (DelegatingLoaderCacheKey)key;
                TemplateStore.a.debug("Cache Hit ? " + key + " = " + this.a.containsKey(key) + " Cache Size = " + this.a.size() + " key = %s -> %s", delegatingLoaderCacheKey.getTemplateName(), delegatingLoaderCacheKey.getDelegatingCacheKeys());
            }
            else {
                TemplateStore.a.debug("Cache Hit ? " + key + " = " + this.a.containsKey(key) + " Cache Size = " + this.a.size(), new Object[0]);
            }
            return this.a.computeIfAbsent(key, mappingFunction);
        }
        
        public void invalidateAll() {
            TemplateStore.a.info("Invalidating entire template cache", new Object[0]);
            this.a.clear();
        }
        
        public void invalidate(final String templateName, final DelegatingLoaderCacheKey cacheKey) {
            this.a.remove(cacheKey);
            TemplateStore.a.info("Invalidation : " + templateName + " Remaining - " + this.a.keySet() + " Size = " + this.a.size(), new Object[0]);
        }
    }
}
