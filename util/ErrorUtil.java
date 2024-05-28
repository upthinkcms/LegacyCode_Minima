// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.util;

import org.apache.commons.lang3.tuple.Pair;

public class ErrorUtil
{
    public static Pair<String, String> getErrorSignature(Throwable e) {
        final StringBuilder sb = new StringBuilder();
        String string = null;
        int i = 0;
        e = e;
        while (i == 0) {
            if ((e = e.getCause()) == null) {
                i = 1;
            }
            else {
                string = e.getStackTrace()[0].toString();
                sb.append(string).append("|");
            }
        }
        return (Pair<String, String>)Pair.of((Object)sb.toString(), (Object)string);
    }
}
