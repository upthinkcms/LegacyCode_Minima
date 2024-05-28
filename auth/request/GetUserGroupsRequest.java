// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.request;

import com.yojito.minima.gson.GsonDto;

public class GetUserGroupsRequest extends GsonDto
{
    private final String email;
    private final String userName;
    
    public GetUserGroupsRequest(final String email, final String userName) {
        this.email = email;
        this.userName = userName;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getUserName() {
        return this.userName;
    }
}
