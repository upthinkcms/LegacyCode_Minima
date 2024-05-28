// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.api;

import java.util.List;
import io.github.classgraph.MethodParameterInfo;
import io.github.classgraph.AnnotationInfo;
import java.util.function.BiFunction;
import com.yojito.minima.netty.GsonHandler;
import com.yojito.minima.gson.GsonDto;
import com.yojito.minima.netty.HttpCall;
import io.github.classgraph.ClassRefTypeSignature;
import io.github.classgraph.MethodInfo;
import java.util.Optional;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.github.classgraph.ClassGraph;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.ArrayList;
import com.yojito.minima.logging.MinimaLogger;

public class APIScan
{
    private static final MinimaLogger a;
    
    public static void scan(final Context context) {
        final String[] split = context.get("apiPackage").split(",");
        final ArrayList list = new ArrayList();
        ((Stream)Arrays.stream(split).sequential()).forEach(s -> list2.add(s));
        list.add("com.yojito.minima");
        APIScan.a.info("Scanning packages - %s", list);
        try (final ScanResult scan = new ClassGraph().enableAllInfo().enableSystemJarsAndModules().whitelistPackages((String[])list.toArray(new String[0])).scan()) {
            scan.getSubclasses(ApiController.class.getCanonicalName()).forEach(klass -> registerApiController(context2, klass));
        }
    }
    
    public static void registerApiController(final Context context, final ClassInfo klass) {
        registerApiController(context, klass, Optional.empty());
    }
    
    public static void registerApiController(final Context context, final ClassInfo klass, final Optional<String> servicePrefix) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   io/github/classgraph/ClassInfo.loadClass:()Ljava/lang/Class;
        //     4: invokevirtual   java/lang/Class.getConstructors:()[Ljava/lang/reflect/Constructor;
        //     7: iconst_0       
        //     8: aaload         
        //     9: iconst_1       
        //    10: anewarray       Ljava/lang/Object;
        //    13: dup            
        //    14: iconst_0       
        //    15: aload_0         /* context */
        //    16: aastore        
        //    17: invokevirtual   java/lang/reflect/Constructor.newInstance:([Ljava/lang/Object;)Ljava/lang/Object;
        //    20: checkcast       Lcom/yojito/minima/api/ApiController;
        //    23: astore_3       
        //    24: goto            63
        //    27: astore          4
        //    29: new             Ljava/lang/RuntimeException;
        //    32: dup            
        //    33: aload           4
        //    35: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/Throwable;)V
        //    38: athrow         
        //    39: astore          4
        //    41: new             Ljava/lang/RuntimeException;
        //    44: dup            
        //    45: aload           4
        //    47: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/Throwable;)V
        //    50: athrow         
        //    51: astore          4
        //    53: new             Ljava/lang/RuntimeException;
        //    56: dup            
        //    57: aload           4
        //    59: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/Throwable;)V
        //    62: athrow         
        //    63: aload_2         /* servicePrefix */
        //    64: invokevirtual   java/util/Optional.isPresent:()Z
        //    67: ifeq            77
        //    70: aload_3        
        //    71: invokevirtual   com/yojito/minima/api/ApiController.isServiceController:()Z
        //    74: ifne            91
        //    77: aload_2         /* servicePrefix */
        //    78: invokevirtual   java/util/Optional.isEmpty:()Z
        //    81: ifeq            95
        //    84: aload_3        
        //    85: invokevirtual   com/yojito/minima/api/ApiController.isServiceController:()Z
        //    88: ifne            95
        //    91: iconst_1       
        //    92: goto            96
        //    95: iconst_0       
        //    96: dup            
        //    97: istore          4
        //    99: ifeq            119
        //   102: aload_1         /* klass */
        //   103: invokevirtual   io/github/classgraph/ClassInfo.getMethodInfo:()Lio/github/classgraph/MethodInfoList;
        //   106: aload_2         /* servicePrefix */
        //   107: aload_1         /* klass */
        //   108: aload_3        
        //   109: aload_0         /* context */
        //   110: invokedynamic   BootstrapMethod #2, accept:(Ljava/util/Optional;Lio/github/classgraph/ClassInfo;Lcom/yojito/minima/api/ApiController;Lcom/yojito/minima/api/Context;)Ljava/util/function/Consumer;
        //   115: invokevirtual   io/github/classgraph/MethodInfoList.forEach:(Ljava/util/function/Consumer;)V
        //   118: return         
        //   119: getstatic       com/yojito/minima/api/APIScan.a:Lcom/yojito/minima/logging/MinimaLogger;
        //   122: ldc             "Skipping controller %s servicePrefix = %s, isServiceController = %b"
        //   124: iconst_3       
        //   125: anewarray       Ljava/lang/Object;
        //   128: dup            
        //   129: iconst_0       
        //   130: aload_1         /* klass */
        //   131: invokevirtual   io/github/classgraph/ClassInfo.getName:()Ljava/lang/String;
        //   134: aastore        
        //   135: dup            
        //   136: iconst_1       
        //   137: aload_2         /* servicePrefix */
        //   138: aastore        
        //   139: dup            
        //   140: iconst_2       
        //   141: aload_3        
        //   142: invokevirtual   com/yojito/minima/api/ApiController.isServiceController:()Z
        //   145: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   148: aastore        
        //   149: invokevirtual   com/yojito/minima/logging/MinimaLogger.warn:(Ljava/lang/String;[Ljava/lang/Object;)V
        //   152: return         
        //    Signature:
        //  (Lcom/yojito/minima/api/Context;Lio/github/classgraph/ClassInfo;Ljava/util/Optional<Ljava/lang/String;>;)V
        //    StackMapTable: 00 09 FF 00 1B 00 00 00 01 07 00 25 4B 07 00 30 4B 07 00 26 FF 00 0B 00 04 07 00 14 07 00 1A 07 00 34 07 00 13 00 00 0D 0D 03 40 01 FF 00 16 00 04 00 07 00 1A 07 00 34 07 00 13 00 00
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                         
        //  -----  -----  -----  -----  ---------------------------------------------
        //  0      24     27     39     Ljava/lang/IllegalAccessException;
        //  0      24     39     51     Ljava/lang/reflect/InvocationTargetException;
        //  0      24     51     63     Ljava/lang/InstantiationException;
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
    
    static {
        a = MinimaLogger.getLog(APIScan.class);
    }
}
