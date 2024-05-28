// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.akka;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.time.Duration;
import com.yojito.minima.api.ApiController;
import java.util.List;
import akka.actor.typed.ActorSystem;
import com.yojito.minima.api.Context;
import com.yojito.minima.logging.MinimaLogger;

public abstract class MinimaAkkaService<REQ extends MinimaAkkaServiceRequest, RESP, DESC>
{
    private static final MinimaLogger a;
    protected OutsideClient<REQ, RESP> outsideClient;
    protected DESC clientProxyInstance;
    protected Context context;
    
    public MinimaAkkaService(final Context context) {
        this.context = context;
    }
    
    public OutsideClient<REQ, RESP> getOutsideClient() {
        return this.outsideClient;
    }
    
    public abstract ActorSystem<REQ> getActorSystem();
    
    public abstract Class<DESC> getServiceDescriptor();
    
    public abstract String getServiceName();
    
    public abstract List<Class<? extends ApiController>> getServiceControllers();
    
    public synchronized DESC getClient() {
        if (this.clientProxyInstance == null) {
            this.outsideClient = new OutsideClient<REQ, RESP>(this.getActorSystem(), Duration.ofSeconds(3L));
            this.clientProxyInstance = (DESC)Proxy.newProxyInstance(MinimaAkkaService.class.getClassLoader(), new Class[] { this.getServiceDescriptor() }, new a(this.outsideClient));
        }
        return this.clientProxyInstance;
    }
    
    public void init() {
    }
    
    public abstract void postInit();
    
    static {
        a = MinimaLogger.getLog(MinimaAkkaService.class);
    }
    
    final class a implements InvocationHandler
    {
        private OutsideClient<REQ, RESP> a;
        
        public a(final MinimaAkkaService minimaAkkaService, final OutsideClient<REQ, RESP> a) {
            this.a = a;
        }
        
        @Override
        public final Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            MinimaAkkaService.a.info("Invoked method: {%s}", method.getName());
            return this.a.call((REQ)args[0]);
        }
    }
}
