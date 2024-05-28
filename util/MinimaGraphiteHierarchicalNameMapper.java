// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.util;

import java.util.Iterator;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.NamingConvention;
import io.micrometer.core.instrument.Meter;
import java.util.Arrays;
import java.util.List;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;

public class MinimaGraphiteHierarchicalNameMapper implements HierarchicalNameMapper
{
    private final List<String> a;
    
    public MinimaGraphiteHierarchicalNameMapper(final String... tagsAsPrefix) {
        this.a = Arrays.asList(tagsAsPrefix);
    }
    
    public String toHierarchicalName(final Meter.Id id, final NamingConvention convention) {
        final String conventionName;
        if ((conventionName = id.getConventionName(convention)).startsWith("api") || conventionName.startsWith("db")) {
            final StringBuilder sb = new StringBuilder();
            final Iterator<String> iterator = this.a.iterator();
            while (iterator.hasNext()) {
                final String tag;
                if ((tag = id.getTag((String)iterator.next())) != null) {
                    sb.append(convention.tagValue(tag));
                }
            }
            for (final Tag tag2 : id.getTagsAsIterable()) {
                if (!this.a.contains(tag2.getKey())) {
                    sb.append('.').append(convention.tagKey(tag2.getKey())).append('.').append(convention.tagValue(tag2.getValue()));
                }
            }
            sb.append(".").append(id.getConventionName(convention));
            return sb.toString();
        }
        final StringBuilder sb2 = new StringBuilder();
        final Iterator<String> iterator3 = this.a.iterator();
        while (iterator3.hasNext()) {
            final String tag3;
            if ((tag3 = id.getTag((String)iterator3.next())) != null) {
                sb2.append(convention.tagValue(tag3)).append('.');
            }
        }
        sb2.append(id.getConventionName(convention));
        for (final Tag tag4 : id.getTagsAsIterable()) {
            if (!this.a.contains(tag4.getKey())) {
                sb2.append('.').append(convention.tagKey(tag4.getKey())).append('.').append(convention.tagValue(tag4.getValue()));
            }
        }
        return sb2.toString();
    }
}
