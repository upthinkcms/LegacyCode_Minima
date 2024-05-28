// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.akka;

import akka.actor.typed.ActorRef;

public interface MinimaAkkaServiceRequest<T, R>
{
    void setReplyTo(final ActorRef<T> p0);
    
    void reply(final R p0);
}
