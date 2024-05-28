// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.netty;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import java.util.concurrent.ConcurrentMap;
import com.yojito.minima.logging.MinimaLogger;

public class APIHandleMapper
{
    private static final MinimaLogger a;
    
    public static ApiHandle match(final HttpCall call, final ConcurrentMap<String, ApiHandle> handles) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/yojito/minima/netty/HttpCall.getUri:()Ljava/lang/String;
        //     4: astore_2       
        //     5: aload_1         /* handles */
        //     6: aload_0         /* call */
        //     7: invokevirtual   com/yojito/minima/netty/HttpCall.getUri:()Ljava/lang/String;
        //    10: invokeinterface java/util/concurrent/ConcurrentMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    15: checkcast       Lcom/yojito/minima/netty/ApiHandle;
        //    18: astore_3       
        //    19: new             Ljava/util/ArrayList;
        //    22: invokespecial   java/util/ArrayList.<init>:()V
        //    25: new             Ljava/util/ArrayList;
        //    28: invokespecial   java/util/ArrayList.<init>:()V
        //    31: aload_3        
        //    32: ifnonnull       530
        //    35: aload_1         /* handles */
        //    36: invokeinterface java/util/concurrent/ConcurrentMap.entrySet:()Ljava/util/Set;
        //    41: invokeinterface java/util/Set.stream:()Ljava/util/stream/Stream;
        //    46: aload_0         /* call */
        //    47: invokedynamic   BootstrapMethod #0, test:(Lcom/yojito/minima/netty/HttpCall;)Ljava/util/function/Predicate;
        //    52: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //    57: invokedynamic   BootstrapMethod #1, test:()Ljava/util/function/Predicate;
        //    62: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //    67: invokestatic    java/util/stream/Collectors.toList:()Ljava/util/stream/Collector;
        //    70: invokeinterface java/util/stream/Stream.collect:(Ljava/util/stream/Collector;)Ljava/lang/Object;
        //    75: checkcast       Ljava/util/List;
        //    78: astore_3       
        //    79: aload_1         /* handles */
        //    80: invokeinterface java/util/concurrent/ConcurrentMap.entrySet:()Ljava/util/Set;
        //    85: invokeinterface java/util/Set.stream:()Ljava/util/stream/Stream;
        //    90: aload_2        
        //    91: invokedynamic   BootstrapMethod #2, test:(Ljava/lang/String;)Ljava/util/function/Predicate;
        //    96: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //   101: invokedynamic   BootstrapMethod #3, test:()Ljava/util/function/Predicate;
        //   106: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //   111: invokestatic    java/util/stream/Collectors.toList:()Ljava/util/stream/Collector;
        //   114: invokeinterface java/util/stream/Stream.collect:(Ljava/util/stream/Collector;)Ljava/lang/Object;
        //   119: checkcast       Ljava/util/List;
        //   122: dup            
        //   123: astore_2       
        //   124: invokedynamic   BootstrapMethod #4, applyAsInt:()Ljava/util/function/ToIntFunction;
        //   129: invokestatic    java/util/Comparator.comparingInt:(Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;
        //   132: invokeinterface java/util/Comparator.reversed:()Ljava/util/Comparator;
        //   137: invokestatic    java/util/Collections.sort:(Ljava/util/List;Ljava/util/Comparator;)V
        //   140: getstatic       com/yojito/minima/netty/APIHandleMapper.a:Lcom/yojito/minima/logging/MinimaLogger;
        //   143: ldc             ">>> POSSIBLE PREFIX MATCHES %s -> %s"
        //   145: iconst_2       
        //   146: anewarray       Ljava/lang/Object;
        //   149: dup            
        //   150: iconst_0       
        //   151: aload_0         /* call */
        //   152: invokevirtual   com/yojito/minima/netty/HttpCall.getUri:()Ljava/lang/String;
        //   155: aastore        
        //   156: dup            
        //   157: iconst_1       
        //   158: aload_3        
        //   159: aastore        
        //   160: invokevirtual   com/yojito/minima/logging/MinimaLogger.trace:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   163: getstatic       com/yojito/minima/netty/APIHandleMapper.a:Lcom/yojito/minima/logging/MinimaLogger;
        //   166: ldc             ">>> POSSIBLE PATH MATCHES %s -> %s"
        //   168: iconst_2       
        //   169: anewarray       Ljava/lang/Object;
        //   172: dup            
        //   173: iconst_0       
        //   174: aload_0         /* call */
        //   175: invokevirtual   com/yojito/minima/netty/HttpCall.getUri:()Ljava/lang/String;
        //   178: aastore        
        //   179: dup            
        //   180: iconst_1       
        //   181: aload_2        
        //   182: aastore        
        //   183: invokevirtual   com/yojito/minima/logging/MinimaLogger.trace:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   186: iconst_0       
        //   187: istore          4
        //   189: iconst_0       
        //   190: istore          5
        //   192: iload           5
        //   194: aload_2        
        //   195: invokeinterface java/util/List.size:()I
        //   200: if_icmpge       242
        //   203: aload_2        
        //   204: iload           5
        //   206: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   211: checkcast       Ljava/util/Map$Entry;
        //   214: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
        //   219: checkcast       Lcom/yojito/minima/netty/ApiHandle;
        //   222: getfield        com/yojito/minima/netty/ApiHandle.a:Ljava/lang/String;
        //   225: ldc             "${"
        //   227: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   230: ifeq            236
        //   233: iconst_1       
        //   234: istore          4
        //   236: iinc            5, 1
        //   239: goto            192
        //   242: aload_3        
        //   243: invokeinterface java/util/List.isEmpty:()Z
        //   248: ifeq            256
        //   251: iload           4
        //   253: ifne            403
        //   256: aload_1         /* handles */
        //   257: invokeinterface java/util/concurrent/ConcurrentMap.entrySet:()Ljava/util/Set;
        //   262: invokeinterface java/util/Set.stream:()Ljava/util/stream/Stream;
        //   267: aload_0         /* call */
        //   268: invokedynamic   BootstrapMethod #5, test:(Lcom/yojito/minima/netty/HttpCall;)Ljava/util/function/Predicate;
        //   273: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //   278: invokestatic    java/util/stream/Collectors.toList:()Ljava/util/stream/Collector;
        //   281: invokeinterface java/util/stream/Stream.collect:(Ljava/util/stream/Collector;)Ljava/lang/Object;
        //   286: checkcast       Ljava/util/List;
        //   289: dup            
        //   290: astore_1       
        //   291: invokeinterface java/util/List.size:()I
        //   296: iconst_1       
        //   297: if_icmple       327
        //   300: aload_1        
        //   301: invokedynamic   BootstrapMethod #6, compare:()Ljava/util/Comparator;
        //   306: invokestatic    java/util/Collections.sort:(Ljava/util/List;Ljava/util/Comparator;)V
        //   309: aload_1        
        //   310: iconst_0       
        //   311: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   316: checkcast       Ljava/util/Map$Entry;
        //   319: invokestatic    java/util/Optional.of:(Ljava/lang/Object;)Ljava/util/Optional;
        //   322: astore          5
        //   324: goto            340
        //   327: aload_1        
        //   328: invokeinterface java/util/List.stream:()Ljava/util/stream/Stream;
        //   333: invokeinterface java/util/stream/Stream.findAny:()Ljava/util/Optional;
        //   338: astore          5
        //   340: aload           5
        //   342: invokevirtual   java/util/Optional.isPresent:()Z
        //   345: ifeq            400
        //   348: getstatic       com/yojito/minima/netty/APIHandleMapper.a:Lcom/yojito/minima/logging/MinimaLogger;
        //   351: ldc             "Using prefixed handle : incoming url : [%s] selected path : [%s]"
        //   353: iconst_2       
        //   354: anewarray       Ljava/lang/Object;
        //   357: dup            
        //   358: iconst_0       
        //   359: aload_0         /* call */
        //   360: invokevirtual   com/yojito/minima/netty/HttpCall.getUri:()Ljava/lang/String;
        //   363: aastore        
        //   364: dup            
        //   365: iconst_1       
        //   366: aload           5
        //   368: invokevirtual   java/util/Optional.get:()Ljava/lang/Object;
        //   371: checkcast       Ljava/util/Map$Entry;
        //   374: invokeinterface java/util/Map$Entry.getKey:()Ljava/lang/Object;
        //   379: aastore        
        //   380: invokevirtual   com/yojito/minima/logging/MinimaLogger.debug:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   383: aload           5
        //   385: invokevirtual   java/util/Optional.get:()Ljava/lang/Object;
        //   388: checkcast       Ljava/util/Map$Entry;
        //   391: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
        //   396: checkcast       Lcom/yojito/minima/netty/ApiHandle;
        //   399: areturn        
        //   400: goto            528
        //   403: iconst_0       
        //   404: istore          5
        //   406: iload           5
        //   408: aload_2        
        //   409: invokeinterface java/util/List.size:()I
        //   414: if_icmpge       528
        //   417: aload_0         /* call */
        //   418: invokevirtual   com/yojito/minima/netty/HttpCall.getUri:()Ljava/lang/String;
        //   421: aload_2        
        //   422: iload           5
        //   424: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   429: checkcast       Ljava/util/Map$Entry;
        //   432: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
        //   437: checkcast       Lcom/yojito/minima/netty/ApiHandle;
        //   440: getfield        com/yojito/minima/netty/ApiHandle.a:Ljava/lang/String;
        //   443: invokestatic    com/yojito/minima/netty/APIHandleMapper.a:(Ljava/lang/String;Ljava/lang/String;)Lorg/apache/commons/lang3/tuple/Pair;
        //   446: dup            
        //   447: astore_1       
        //   448: invokevirtual   org/apache/commons/lang3/tuple/Pair.getLeft:()Ljava/lang/Object;
        //   451: checkcast       Ljava/lang/Boolean;
        //   454: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   457: ifne            522
        //   460: getstatic       com/yojito/minima/netty/APIHandleMapper.a:Lcom/yojito/minima/logging/MinimaLogger;
        //   463: ldc             "Selected Path for PathParameter Matching - [%s]"
        //   465: iconst_1       
        //   466: anewarray       Ljava/lang/Object;
        //   469: dup            
        //   470: iconst_0       
        //   471: aload_2        
        //   472: iload           5
        //   474: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   479: checkcast       Ljava/util/Map$Entry;
        //   482: invokeinterface java/util/Map$Entry.getKey:()Ljava/lang/Object;
        //   487: aastore        
        //   488: invokevirtual   com/yojito/minima/logging/MinimaLogger.debug:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   491: aload_0         /* call */
        //   492: aload_1        
        //   493: invokevirtual   org/apache/commons/lang3/tuple/Pair.getRight:()Ljava/lang/Object;
        //   496: checkcast       Ljava/util/Map;
        //   499: invokevirtual   com/yojito/minima/netty/HttpCall.addPathParam:(Ljava/util/Map;)V
        //   502: aload_2        
        //   503: iload           5
        //   505: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   510: checkcast       Ljava/util/Map$Entry;
        //   513: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
        //   518: checkcast       Lcom/yojito/minima/netty/ApiHandle;
        //   521: areturn        
        //   522: iinc            5, 1
        //   525: goto            406
        //   528: aconst_null    
        //   529: areturn        
        //   530: getstatic       com/yojito/minima/netty/APIHandleMapper.a:Lcom/yojito/minima/logging/MinimaLogger;
        //   533: ldc             ">>> EXACT PATH MATCH %s -> %s"
        //   535: iconst_2       
        //   536: anewarray       Ljava/lang/Object;
        //   539: dup            
        //   540: iconst_0       
        //   541: aload_0         /* call */
        //   542: invokevirtual   com/yojito/minima/netty/HttpCall.getUri:()Ljava/lang/String;
        //   545: aastore        
        //   546: dup            
        //   547: iconst_1       
        //   548: aload_3        
        //   549: invokevirtual   com/yojito/minima/netty/ApiHandle.getPath:()Ljava/lang/String;
        //   552: aastore        
        //   553: invokevirtual   com/yojito/minima/logging/MinimaLogger.debug:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   556: aload_3        
        //   557: areturn        
        //    Signature:
        //  (Lcom/yojito/minima/netty/HttpCall;Ljava/util/concurrent/ConcurrentMap<Ljava/lang/String;Lcom/yojito/minima/netty/ApiHandle;>;)Lcom/yojito/minima/netty/ApiHandle;
        //    StackMapTable: 00 0C FF 00 C0 00 06 07 00 0E 07 00 1E 07 00 19 07 00 19 01 01 00 00 2B FA 00 05 F8 00 0D FF 00 46 00 02 07 00 0E 07 00 19 00 00 FF 00 0C 00 06 07 00 0E 00 00 00 00 07 00 1C 00 00 FF 00 3B 00 00 00 00 FE 00 02 07 00 0E 00 07 00 19 FE 00 02 00 00 01 FB 00 73 FF 00 05 00 00 00 00 FF 00 01 00 04 07 00 0E 00 00 07 00 0D 00 00
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:382)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:95)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:206)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:93)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:868)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:761)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:638)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:605)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:195)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:162)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:137)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:147)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static Pair<Boolean, Map<String, Optional<String>>> a(final String s, final String s2) {
        final String[] split = s.split("/");
        final String[] split2 = s2.split("/");
        final HashMap hashMap = new HashMap();
        boolean b = false;
        if (split.length >= split2.length) {
            for (int i = 0; i < split2.length; ++i) {
                if (split2[i].startsWith("${") && split2[i].endsWith("}")) {
                    final String substring = split2[i].substring(2, split2[i].length() - 1);
                    String value = split[i];
                    if (split.length > split2.length && i == split2.length - 1) {
                        final ArrayList list = new ArrayList();
                        for (int j = i; j < split.length; ++j) {
                            list.add(split[j]);
                        }
                        value = (String)list.stream().collect(Collectors.joining("/"));
                    }
                    hashMap.put(substring, Optional.of(value));
                }
                else if (!split2[i].equals(split[i])) {
                    b = true;
                    break;
                }
            }
        }
        APIHandleMapper.a.debug("path = %s pattern = %s -> Match Failed -> %b, Map - %s\n", s, s2, b, hashMap);
        return (Pair<Boolean, Map<String, Optional<String>>>)Pair.of((Object)b, (Object)hashMap);
    }
    
    static {
        a = MinimaLogger.getLog(APIHandleMapper.class);
    }
}
