// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.logging;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.HashMap;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Arrays;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.text.DateFormat;
import java.util.Map;

public class MinimaLogger
{
    private static Map<Class<?>, MinimaLogger> a;
    private static final DateFormat b;
    private Logger c;
    
    public MinimaLogger(final Class<?> cls) {
        this(cls.getName(), true);
    }
    
    private MinimaLogger(final String cls, final boolean initCMSLog) {
        this.c = LoggerFactory.getLogger(cls);
    }
    
    public boolean isTraceEnabled() {
        return this.c.isTraceEnabled();
    }
    
    public boolean isDebugEnabled() {
        return this.c.isDebugEnabled();
    }
    
    public boolean isInfoEnabled() {
        return this.c.isInfoEnabled();
    }
    
    public void trace(final String format, final Object... o) {
        this.c.trace(String.format(format, o));
    }
    
    public void _trace(final String format) {
        this.c.trace(format);
    }
    
    public void trace(final Throwable t, final String format, final Object... o) {
        this.c.trace(String.format(format, o), t);
    }
    
    public void debug(final String format, final Object... o) {
        this.c.debug(String.format(format, o));
    }
    
    public void debugNoFormat(final String format) {
        this.c.debug(format);
    }
    
    public void traceNoFormat(final String format) {
        this.c.trace(format);
    }
    
    public void debug(final Throwable t, final String format, final Object... o) {
        this.c.debug(String.format(format, o), t);
    }
    
    public void info(final String format, final Object... o) {
        this.c.info(String.format(format, o));
    }
    
    public void info(final Throwable t, final String format, final Object... o) {
        this.c.info(String.format(format, o), t);
    }
    
    public void warn(final String format, final Object... o) {
        this.c.warn(String.format(format, o));
    }
    
    public void warn(final Throwable t, final String format, final Object... args) {
        this.c.warn(String.format(format, args), t);
    }
    
    public void error(final String format, final Object... o) {
        this.c.error(String.format(format, o));
    }
    
    public void error(final Throwable t, final String format, final Object... args) {
        this.c.error(String.format(format, args), t);
    }
    
    public static String logString(Object... values) {
        final int length;
        if ((length = values.length) == 0) {
            return "";
        }
        if (length != 1) {
            final StringBuilder sb = new StringBuilder();
            int n = 1;
            boolean b = false;
            values = values;
            for (int length2 = values.length, i = 0; i < length2; ++i) {
                Object obj = values[i];
                if (n != 0) {
                    sb.append(", ");
                    n = 0;
                }
                else {
                    sb.append(" = ");
                    n = 1;
                }
                if (obj instanceof long[]) {
                    obj = Arrays.toString((long[])obj);
                }
                else if (obj instanceof String[]) {
                    obj = Arrays.toString((Object[])obj);
                }
                else if (obj instanceof int[]) {
                    obj = Arrays.toString((int[])obj);
                }
                else if (obj instanceof boolean[]) {
                    obj = Arrays.toString((boolean[])obj);
                }
                else if (obj instanceof byte[]) {
                    obj = ((byte[])obj).length + " bytes";
                }
                else if (obj instanceof String) {
                    if (b) {
                        obj = "\"" + obj;
                    }
                }
                else if (obj instanceof Date) {
                    final Date date;
                    obj = (((date = (Date)obj).getTime() == 0L) ? "0" : ("\"" + toStringDate(date)));
                }
                else if (obj instanceof InetSocketAddress) {
                    obj = logString((InetSocketAddress)obj);
                }
                if (obj == null) {
                    sb.append("<NULL>");
                }
                else {
                    sb.append(obj);
                }
                b = !b;
            }
            return sb.toString();
        }
        final Object o;
        if ((o = values[0]) == null) {
            return "<NULL>";
        }
        return ", " + o.toString();
    }
    
    public static String toStringDate(final Date date) {
        if (date.getTime() == 0L) {
            return "0";
        }
        return MinimaLogger.b.format(date);
    }
    
    public static String logString(final InetSocketAddress addr) {
        if (addr == null) {
            return "*";
        }
        return addr.getHostName() + ":" + addr.getPort();
    }
    
    public static MinimaLogger getLog(final Class<?> cls) {
        MinimaLogger minimaLogger;
        if ((minimaLogger = MinimaLogger.a.get(cls)) == null) {
            synchronized (MinimaLogger.a) {
                if ((minimaLogger = MinimaLogger.a.get(cls)) == null) {
                    minimaLogger = new MinimaLogger(cls);
                    MinimaLogger.a.put(cls, minimaLogger);
                }
            }
        }
        return minimaLogger;
    }
    
    static {
        MinimaLogger.a = new HashMap<Class<?>, MinimaLogger>(1024);
        b = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
    }
}
