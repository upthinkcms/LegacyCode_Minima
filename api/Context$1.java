// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.api;

import java.util.Optional;
import io.micrometer.graphite.GraphiteConfig;

final class Context$1 implements GraphiteConfig {
    private /* synthetic */ Optional a;
    private /* synthetic */ Optional b;
    
    public final String host() {
        return this.a.orElse("localhost");
    }
    
    public final int port() {
        return Integer.parseInt(this.b.orElse("2004"));
    }
    
    public final String get(final String k) {
        return null;
    }
    
    public final boolean graphiteTagsEnabled() {
        return false;
    }
}