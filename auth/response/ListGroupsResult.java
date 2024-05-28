// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.response;

import java.util.List;
import com.yojito.minima.gson.GsonDto;

public class ListGroupsResult extends GsonDto
{
    private final List<String> groups;
    
    public ListGroupsResult(final List<String> groups) {
        this.groups = groups;
    }
    
    public List<String> getGroups() {
        return this.groups;
    }
}
