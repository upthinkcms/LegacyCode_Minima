// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.akka;

import java.util.List;
import java.lang.reflect.Method;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import java.lang.reflect.InvocationTargetException;
import com.yojito.minima.api.APIScan;
import java.util.Optional;
import com.yojito.minima.netty.HttpCall;
import java.util.function.BiFunction;
import com.yojito.minima.netty.GsonHandler;
import com.yojito.minima.gson.GsonDto;
import com.yojito.minima.api.APIRegistrations;
import com.yojito.minima.api.ServiceResponseType;
import com.yojito.minima.MinimaException;
import com.yojito.minima.api.API;
import io.github.classgraph.ClassGraph;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.ArrayList;
import com.yojito.minima.api.Context;
import com.yojito.minima.logging.MinimaLogger;

public class AkkaServiceScan
{
    private static final MinimaLogger a;
    
    public static void scan(final Context context) {
        final String[] split = context.get("servicePackage").split(",");
        final ArrayList list = new ArrayList();
        ((Stream)Arrays.stream(split).sequential()).forEach(s -> list3.add(s));
        list.add("com.yojito.minima");
        AkkaServiceScan.a.info("Scanning packages - %s", list);
        final ScanResult scan = new ClassGraph().enableAllInfo().enableSystemJarsAndModules().whitelistPackages((String[])list.toArray(new String[0])).scan();
        try {
            final ClassInfoList subclasses = scan.getSubclasses(MinimaAkkaService.class.getCanonicalName());
            final ArrayList list2 = new ArrayList();
            subclasses.forEach(classInfo -> {
                AkkaServiceScan.a.debug("Scanning %s", classInfo);
                try {
                    final MinimaAkkaService minimaAkkaService = classInfo.loadClass().getConstructor(Context.class).newInstance(context2);
                    final MinimaAkkaService minimaAkkaService2;
                    minimaAkkaService2.getServiceName();
                    minimaAkkaService.init();
                    final String s2;
                    context2.addCached(String.format("%sService", s2), p1 -> minimaAkkaService5);
                    context2.addCached(String.format("%sServiceClient", s2), p1 -> minimaAkkaService6.getClient());
                    final MinimaAkkaService minimaAkkaService3;
                    minimaAkkaService3.getServiceDescriptor();
                    final Class clazz;
                    clazz.getMethods();
                    final Object o;
                    final int length = o.length;
                    int i = 0;
                    while (i < length) {
                        final Method[] array;
                        final Method method = array[i];
                        AkkaServiceScan.a.debug("Adding service methods %s", method.getName());
                        final API api = method.getAnnotation(API.class);
                        final API api2;
                        if ((api2 = api) == null) {
                            throw new MinimaException("API annotation is missing");
                        }
                        else if (api2.dbTx()) {
                            throw new MinimaException("Service API declarations cant have transaction enabled as API level");
                        }
                        else {
                            final ServiceResponseType serviceResponseType = method.getAnnotation(ServiceResponseType.class);
                            final ServiceResponseType serviceResponseType2;
                            if ((serviceResponseType2 = serviceResponseType) == null) {
                                throw new MinimaException("ServiceResponseType annotation is missing");
                            }
                            else {
                                AkkaServiceScan.a.debug("Found API registration %s on method %s", api2.path(), api2.method());
                                method.getParameterTypes();
                                final Object o2;
                                final Class clazz2 = o2[0];
                                serviceResponseType2.klass();
                                final Class[] array2;
                                AkkaServiceScan.a.debug("Request Class %s", array2[0].getCanonicalName());
                                AkkaServiceScan.a.debug("Response Class [%s]", serviceResponseType2.klass());
                                final Class clazz3;
                                final Class responseClass;
                                AkkaServiceScan.a.info("Registering API on path %s from method %s of %s RequestType %s ReturnType %s Transactional = %b", api2.path(), method.getName(), clazz3.getName(), clazz2.getName(), responseClass.getName(), api2.dbTx());
                                final MinimaAkkaService minimaAkkaService4;
                                GsonHandler.registerGenericAPI(api2.path(), clazz2, responseClass, APIRegistrations.wrapService(minimaAkkaService4.getClient(), method, api2, clazz2, context2, api2.timeout()), method.getName(), api2);
                                ++i;
                            }
                        }
                    }
                    minimaAkkaService.getServiceControllers();
                    final Object o3;
                    final List list6;
                    if (o3 != null && list6.size() > 0) {
                        AkkaServiceScan.a.info("Registering service controllers : %s", list6);
                        minimaAkkaService.getServiceName();
                        context2.get("servicePackage");
                        new ClassGraph().enableAllInfo().enableSystemJarsAndModules().whitelistPackages((String[])list4.toArray(new String[0])).scan();
                        try {
                            final List list7;
                            list7.forEach(clazz4 -> {
                                scanResult2.getClassInfo(clazz4.getCanonicalName());
                                final ClassInfo klass;
                                AkkaServiceScan.a.info("Registering service controller : %s", klass);
                                APIScan.registerApiController(context3, klass, Optional.of("/" + s3));
                                return;
                            });
                            final ScanResult scanResult;
                            if (scanResult != null) {
                                scanResult.close();
                            }
                        }
                        catch (final Throwable t4) {
                            final ScanResult scanResult;
                            if (scanResult != null) {
                                try {
                                    scanResult.close();
                                }
                                catch (final Throwable exception2) {
                                    t4.addSuppressed(exception2);
                                }
                            }
                            throw t4;
                        }
                    }
                    list5.add(minimaAkkaService);
                    return;
                }
                catch (final NoSuchMethodException e) {
                    throw new MinimaException(e);
                }
                catch (final IllegalAccessException e2) {
                    throw new MinimaException(e2);
                }
                catch (final InstantiationException e3) {
                    throw new MinimaException(e3);
                }
                catch (final InvocationTargetException e4) {
                    throw new MinimaException(e4);
                }
            });
            list2.forEach(minimaAkkaService7 -> minimaAkkaService7.postInit());
            if (scan != null) {
                scan.close();
            }
        }
        catch (final Throwable t) {
            if (scan != null) {
                try {
                    scan.close();
                }
                catch (final Throwable exception) {
                    t.addSuppressed(exception);
                }
            }
            throw t;
        }
    }
    
    static {
        a = MinimaLogger.getLog(AkkaServiceScan.class);
    }
}
