// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.domain;

import com.yojito.minima.gson.GsonDto;

public class UnAuthenitcatedDTO extends GsonDto
{
    public static UnAuthenitcatedDTO INSTANCE;
    
    static {
        UnAuthenitcatedDTO.INSTANCE = new UnAuthenitcatedDTO();
    }
}
