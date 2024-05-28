// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ServiceResponseType {
    Class klass();
}
