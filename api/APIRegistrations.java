// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.api;

import org.jdbi.v3.core.Handle;
import java.util.List;
import com.yojito.minima.auth.cognito.AuthRole;
import com.yojito.minima.auth.Authenticator;
import com.yojito.minima.auth.domain.UnAuthenitcatedDTO;
import java.util.function.BiConsumer;
import com.yojito.minima.auth.domain.TokenValidation;
import org.apache.commons.lang3.tuple.Pair;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import com.yojito.minima.util.ErrorUtil;
import com.yojito.minima.db.Database;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Optional;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import com.yojito.minima.util.MetricsUtil;
import io.micrometer.core.instrument.MeterRegistry;
import com.yojito.minima.gson.GsonObject;
import com.yojito.minima.netty.HttpCall;
import java.util.function.BiFunction;
import com.yojito.minima.gson.GsonDto;
import java.lang.reflect.Method;
import com.yojito.minima.logging.MinimaLogger;

public class APIRegistrations
{
    private static final MinimaLogger a;
    
    public static BiFunction<HttpCall, GsonObject, GsonDto> wrapController(final ApiController controller, final Method method, final API api, final Class<? extends GsonDto> requestClass, final Context context) {
        return callObjectMethod(controller, method, api, requestClass, context, true, 0L);
    }
    
    public static BiFunction<HttpCall, GsonObject, GsonDto> wrapService(final Object service, final Method method, final API api, final Class<? extends GsonDto> requestClass, final Context context, final long timeoutInMillis) {
        return callObjectMethod(service, method, api, requestClass, context, false, timeoutInMillis);
    }
    
    public static BiFunction<HttpCall, GsonObject, GsonDto> callObjectMethod(final Object service, final Method method, final API api, final Class<? extends GsonDto> requestClass, final Context context, final boolean isController, final long timeoutInMillis) {
        api.authenticated();
        api.authRole();
        final boolean dbTx = api.dbTx();
        context.get("appName");
        final MeterRegistry meterRegistry = context.get("meterRegistry");
        Timer.builder("apitimers." + MetricsUtil.pathToMetricName(api.path())).description("Time taken for api " + api.path()).publishPercentiles(new double[] { 0.3, 0.5, 0.95 }).publishPercentileHistogram().tags(new String[] { "api", "latency" }).register(meterRegistry);
        Counter.builder("apicalls." + MetricsUtil.pathToMetricName(api.path())).baseUnit("numbers").description("Number of Calls made to" + api.path()).tags(new String[] { "api", "requests" }).register(meterRegistry);
        Counter.builder("apierrors." + MetricsUtil.pathToMetricName(api.path())).baseUnit("beans").description("Errors reported for api " + api.path()).tags(new String[] { "api", "error" }).register(meterRegistry);
        if (dbTx) {
            return (BiFunction<HttpCall, GsonObject, GsonDto>)((httpCall2, json) -> {
                System.currentTimeMillis();
                counter.increment();
                try {
                    a(b, context2, s, httpCall2, json);
                    final Object o;
                    if (((Optional)((Pair)o).getLeft()).isPresent()) {
                        final Pair pair;
                        return (GsonDto)((Optional)pair.getLeft()).get();
                    }
                    else {
                        final Pair pair;
                        APIRegistrations.a.debug("Adding id authentication - %s", pair.getRight());
                        json.put("id", (GsonDto)pair.getRight());
                        GsonDto.fromGson(json, (Class<GsonDto>)classOfT);
                        final GsonDto gsonDto;
                        new ArrayList(Arrays.asList(httpCall2, gsonDto));
                        return Database.withinTx(context2.getDatabase(), handle2 -> {
                            try {
                                list2.add(handle2);
                                return (GsonDto)method2.invoke(obj, list2.toArray());
                            }
                            catch (final IllegalAccessException cause) {
                                throw new RuntimeException(cause);
                            }
                            catch (final InvocationTargetException cause2) {
                                throw new RuntimeException(cause2);
                            }
                        });
                    }
                }
                catch (final Throwable e) {
                    final Throwable e2;
                    ErrorUtil.getErrorSignature(e2);
                    counter2.increment();
                    final Pair<String, String> errorSig;
                    MetricsUtil.reportError(context2, errorSig, e);
                    throw e;
                }
                finally {
                    final long n;
                    timer.record(System.currentTimeMillis() - n, TimeUnit.MILLISECONDS);
                }
            });
        }
        return (BiFunction<HttpCall, GsonObject, GsonDto>)((httpCall4, json2) -> {
            System.currentTimeMillis();
            counter3.increment();
            a(b2, context3, s2, httpCall4, json2);
            final Object o3;
            if (((Optional)((Pair)o3).getLeft()).isPresent()) {
                final Pair pair2;
                return (GsonDto)((Optional)pair2.getLeft()).get();
            }
            else {
                final Pair pair2;
                APIRegistrations.a.debug("Adding id authentication - %s", pair2.getRight());
                json2.put("id", (GsonDto)pair2.getRight());
                try {
                    GsonDto.fromGson(json2, (Class<GsonDto>)classOfT2);
                    if (b3) {
                        final GsonDto gsonDto3;
                        return (GsonDto)method3.invoke(o2, httpCall4, gsonDto3);
                    }
                    else {
                        final GsonDto gsonDto3;
                        final CompletionStage completionStage = (CompletionStage)method3.invoke(o2, gsonDto3);
                        return (GsonDto)completionStage.toCompletableFuture().get(timeout, TimeUnit.MILLISECONDS);
                    }
                }
                catch (final IllegalAccessException ex) {
                    final Throwable e3;
                    ErrorUtil.getErrorSignature(e3);
                    counter4.increment();
                    final Pair<String, String> errorSig2;
                    MetricsUtil.reportError(context3, errorSig2, ex);
                    throw new RuntimeException(ex);
                }
                catch (final InvocationTargetException ex2) {
                    final Throwable e4;
                    ErrorUtil.getErrorSignature(e4);
                    counter4.increment();
                    final Pair<String, String> errorSig3;
                    MetricsUtil.reportError(context3, errorSig3, ex2);
                    throw new RuntimeException(ex2);
                }
                catch (final InterruptedException ex3) {
                    final Throwable e5;
                    ErrorUtil.getErrorSignature(e5);
                    counter4.increment();
                    final Pair<String, String> errorSig4;
                    MetricsUtil.reportError(context3, errorSig4, ex3);
                    throw new RuntimeException(ex3);
                }
                catch (final ExecutionException ex4) {
                    final Throwable e6;
                    ErrorUtil.getErrorSignature(e6);
                    counter4.increment();
                    final Pair<String, String> errorSig5;
                    MetricsUtil.reportError(context3, errorSig5, ex4);
                    throw new RuntimeException(ex4);
                }
                catch (final TimeoutException ex5) {
                    final Throwable e7;
                    ErrorUtil.getErrorSignature(e7);
                    counter4.increment();
                    final Pair<String, String> errorSig6;
                    MetricsUtil.reportError(context3, errorSig6, ex5);
                    throw new RuntimeException(ex5);
                }
                finally {
                    final long n2;
                    timer2.record(System.currentTimeMillis() - n2, TimeUnit.MILLISECONDS);
                }
            }
        });
    }
    
    private static Pair<Optional<GsonDto>, GsonDto> a(final boolean b, final Context context, final String s, final HttpCall httpCall, final GsonObject gsonObject) {
        if (!b) {
            return (Pair<Optional<GsonDto>, GsonDto>)Pair.of((Object)Optional.empty(), (Object)null);
        }
        final Authenticator<TokenValidation, AuthRole> authenticator;
        GsonDto gsonDto = (authenticator = context.getAuthenticator()).defaultToken();
        final AuthRole role;
        if (!(role = authenticator.getRole(s)).equals(authenticator.publicRole())) {
            gsonDto = authenticator.authenticate(httpCall);
        }
        final AuthRole tokenRole = authenticator.getTokenRole((TokenValidation)gsonDto);
        if (APIRegistrations.a.isDebugEnabled()) {
            APIRegistrations.a.debug("Authentication %s", gsonDto);
        }
        final Pair<Boolean, BiConsumer<HttpCall, GsonObject>> authorize;
        if (!(boolean)(authorize = authenticator.authorize((TokenValidation)gsonDto, role, tokenRole)).getLeft()) {
            ((BiConsumer)authorize.getRight()).accept(httpCall, gsonObject);
            return (Pair<Optional<GsonDto>, GsonDto>)Pair.of((Object)Optional.of(UnAuthenitcatedDTO.INSTANCE), (Object)gsonDto);
        }
        ((BiConsumer)authorize.getRight()).accept(httpCall, gsonObject);
        return (Pair<Optional<GsonDto>, GsonDto>)Pair.of((Object)Optional.empty(), (Object)gsonDto);
    }
    
    static {
        a = MinimaLogger.getLog(APIRegistrations.class);
    }
}
