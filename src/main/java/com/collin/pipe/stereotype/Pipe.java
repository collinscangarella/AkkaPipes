package com.collin.pipe.stereotype;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.collin.pipe.transmission.Message;
import com.sun.corba.se.impl.io.TypeMismatchException;

import javax.xml.bind.TypeConstraintException;
import java.util.List;

/**
 * A basic pipe. Data of type I is operated on by the 'ingest' method.
 * The resultant data of type O is then forwarded to the downstream pipes.
 * This pipe is to be overridden to transform data in some way.
 * While type parameters are never handled by the programmer, its important that they are entered correctly
 * when extending this object as that information is used when building these pipe objects.
 * For example, MyPipe extends Pipe<String, String>.
 * @param <I> The type of object to be received.
 * @param <O> The type of object to be sent.
 */
public abstract class Pipe<I, O> extends AbstractPipe<I, O> {

    /**
     *  Sends the outbound O object to the downstream pipes.
     * @param outbound The outbound message to be sent.
     */
    @Override
    protected final void send(O outbound) {
        Message<O> info = new Message<>(this.getId(), outbound);
        this.getSender().tell(info, this.getSelf());
    }
}
