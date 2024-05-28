// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.domain;

import java.util.Map;
import java.util.List;
import com.yojito.minima.gson.GsonDto;

public class TokenValidation extends GsonDto
{
    private final boolean validated;
    private final String sub;
    private final List<String> groups;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final String userName;
    private final String validationError;
    private final Map<String, String> customAttributes;
    
    public TokenValidation(final boolean validated, final String sub, final List<String> groups, final String name, final String email, final String phoneNumber, final String userName, final String validationError, final Map<String, String> customAttributes) {
        this.validated = validated;
        this.sub = sub;
        this.groups = groups;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.validationError = validationError;
        this.customAttributes = customAttributes;
    }
    
    public boolean isValidated() {
        return this.validated;
    }
    
    public String getSub() {
        return this.sub;
    }
    
    public List<String> getGroups() {
        return this.groups;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public String getValidationError() {
        return this.validationError;
    }
    
    public Map<String, String> getCustomAttributes() {
        return this.customAttributes;
    }
    
    public static TokenValidation defaultToken() {
        return new TokenValidation(true, null, null, null, null, null, null, null, null);
    }
    
    public static TokenValidation failedToken() {
        return new TokenValidation(false, null, null, null, null, null, null, null, null);
    }
    
    public TokenValidation withGroups(final List<String> groups) {
        return new TokenValidation(this.validated, this.sub, groups, this.name, this.email, this.phoneNumber, this.userName, this.validationError, this.customAttributes);
    }
    
    public TokenValidation withName(final String name) {
        return new TokenValidation(this.validated, this.sub, this.groups, name, this.email, this.phoneNumber, this.userName, this.validationError, this.customAttributes);
    }
}
