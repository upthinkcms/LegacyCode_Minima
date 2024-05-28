// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.request;

import com.yojito.minima.auth.domain.TokenValidation;
import com.yojito.minima.auth.domain.AuthenticatedRequest;

public class AddToGroupRequest extends AuthenticatedRequest
{
    private final String email;
    private final String userName;
    private final String groupName;
    
    public AddToGroupRequest(final TokenValidation validation, final String email, final String groupName) {
        super(validation);
        this.email = email;
        this.userName = null;
        this.groupName = groupName;
    }
    
    public AddToGroupRequest(final TokenValidation validation, final String email, final String userName, final String groupName) {
        super(validation);
        this.email = email;
        this.userName = userName;
        this.groupName = groupName;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getGroupName() {
        return this.groupName;
    }
    
    public String getUserName() {
        return this.userName;
    }
}
