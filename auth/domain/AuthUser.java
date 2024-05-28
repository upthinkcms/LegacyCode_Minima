// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.yojito.minima.gson.GsonDto;

public class AuthUser extends GsonDto
{
    private final String phone_number;
    private final String email;
    private final String username;
    private final String password;
    private final String name;
    private final String sub;
    private final List<String> groups;
    private final Map<String, String> customAttributes;
    
    public AuthUser(final String phone_number, final String email, final String username, final String password, final String name, final String sub, final List<String> groups, final Map<String, String> customAttributes) {
        this.phone_number = phone_number;
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.sub = sub;
        this.groups = groups;
        this.customAttributes = customAttributes;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public String getPhone_number() {
        return this.phone_number;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getName() {
        return this.name;
    }
    
    public static final class Builder
    {
        private String phone_number;
        private String email;
        private String username;
        private String password;
        private String name;
        private String sub;
        private List<String> groups;
        private Map<String, String> customAttributes;
        
        private Builder() {
            this.customAttributes = new HashMap<String, String>();
        }
        
        public static Builder anAuthUser() {
            return new Builder();
        }
        
        public final Builder setPhone_number(final String phone_number) {
            this.phone_number = phone_number;
            return this;
        }
        
        public final Builder setEmail(final String email) {
            this.email = email;
            return this;
        }
        
        public final Builder setUsername(final String username) {
            this.username = username;
            return this;
        }
        
        public final Builder setPassword(final String password) {
            this.password = password;
            return this;
        }
        
        public final Builder setName(final String name) {
            this.name = name;
            return this;
        }
        
        public final Builder setSub(final String sub) {
            this.sub = sub;
            return this;
        }
        
        public final Builder setGroups(final List<String> groups) {
            this.groups = groups;
            return this;
        }
        
        public final Builder addGroup(final String g) {
            if (this.groups == null) {
                this.groups = new ArrayList<String>();
            }
            if (!this.groups.contains(g)) {
                this.groups.add(g);
            }
            return this;
        }
        
        public final void addCustomAttribute(final String name, final String value) {
            this.customAttributes.put(name, value);
        }
        
        public final AuthUser build() {
            return new AuthUser(this.phone_number, this.email, this.username, this.password, this.name, this.sub, this.groups, this.customAttributes);
        }
    }
}
