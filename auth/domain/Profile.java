// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.domain;

import java.util.List;
import com.yojito.minima.gson.GsonDto;

public class Profile extends GsonDto
{
    private final String email;
    private final String name;
    private final List<String> groups;
    private final String sub;
    
    public Profile(final String email, final String name, final List<String> groups, final String sub) {
        this.email = email;
        this.name = name;
        this.groups = groups;
        this.sub = sub;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public List<String> getGroups() {
        return this.groups;
    }
    
    public String getSub() {
        return this.sub;
    }
    
    public String getName() {
        return this.name;
    }
}
