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
public @interface API {
    String path();
    
    String method() default "";
    
    boolean corsEnabled() default false;
    
    boolean dbTx() default false;
    
    boolean authenticated() default false;
    
    String authRole() default "";
    
    String debugLevel() default "DEBUG";
    
    String htmlTemplate() default "";
    
    long timeout() default 5000L;
    
    boolean skipJsonSerialization() default false;
    
    boolean corsCredsEnabled() default false;
}
