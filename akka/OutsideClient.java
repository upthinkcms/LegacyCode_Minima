// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.akka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.RecipientRef;
import akka.actor.typed.javadsl.AskPattern;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.time.Duration;
import akka.actor.typed.ActorSystem;

public class OutsideClient<Command extends MinimaAkkaServiceRequest, Reply>
{
    private final ActorSystem<Command> a;
    private final Duration b;
    
    public OutsideClient(final ActorSystem<Command> system, final Duration requestTimeout) {
        this.a = system;
        this.b = requestTimeout;
    }
    
    public CompletionStage<Reply> call(final Command command) {
        if (this.a == null) {
            return CompletableFuture.failedStage(new RuntimeException("ActorSystem is null"));
        }
        return AskPattern.ask((RecipientRef)this.a, replyTo -> {
            command.setReplyTo(replyTo);
            return command;
        }, this.b, this.a.scheduler());
    }
}
