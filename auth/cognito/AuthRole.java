// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.cognito;

public class AuthRole
{
    private final int a;
    private final String b;
    public static final AuthRole PUBLIC;
    public static final AuthRole AUTHENTICATED;
    public static final AuthRole SUPERADMIN;
    
    public AuthRole(final int l, final String name) {
        this.a = l;
        this.b = name;
    }
    
    public static AuthRole valueOf(final String role) {
        switch (role) {
            case "PUBLIC": {
                return AuthRole.PUBLIC;
            }
            case "AUTHENTICATED": {
                return AuthRole.AUTHENTICATED;
            }
            case "SUPERADMIN": {
                return AuthRole.SUPERADMIN;
            }
            default: {
                throw new RuntimeException("Unknown role " + role);
            }
        }
    }
    
    public boolean allowed(final AuthRole role) {
        return role.a >= this.a;
    }
    
    public int getLevel() {
        return this.a;
    }
    
    public String getName() {
        return this.b;
    }
    
    @Override
    public String toString() {
        return "AuthRole{level=" + this.a + ", name='" + this.b + "'}";
    }
    
    static {
        PUBLIC = new AuthRole(0, "PUBLIC");
        AUTHENTICATED = new AuthRole(100, "AUTHENTICATED");
        SUPERADMIN = new AuthRole(10000, "SUPERADMIN");
    }
}
