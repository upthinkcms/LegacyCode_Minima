// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.akka;

import akka.actor.typed.ActorRef;
import com.yojito.minima.gson.GsonDto;

public abstract class ReplyingActorCommand<T> extends GsonDto
{
    protected ActorRef<T> replyTo;
    
    public ActorRef<T> getReplyTo() {
        return this.replyTo;
    }
    
    public void setReplyTo(final ActorRef replyTo) {
        this.replyTo = (akka.actor.typed.ActorRef<T>)replyTo;
    }
}
