package com.MessageOrdering;

import com.Communication.Message;
import com.GroupManagement.User;

import java.io.Serializable;
import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class CausalOrdering implements MessageOrdering, Serializable
{
    VectorClock clock;
    BlockingQueue<Message> messageQueue;
    PriorityBlockingQueue<Message> holdBackQueue;


    public CausalOrdering()
    {
        clock = new VectorClock(10);
        messageQueue = new LinkedBlockingQueue<>();
        holdBackQueue = new PriorityBlockingQueue<>(10, new MessageComparator());
    }

    @Override
    public Message prepareMessage(Message message)
    {
        clock.increaseClock(message.getSender());
        VectorClock newClock = null;
        try
        {
             newClock= clock.clone();
        } catch (CloneNotSupportedException e) {}
        message.setClock(newClock);
        System.out.println(clock.clock.toString());
        return message;
    }

    @Override
    public void receive(Message message)
    {
        System.out.println(message.getClock().clock.toString());
        holdBackQueue.put(message);
        Message nextMess = message;
        while (clock.isNext(nextMess.getClock(), nextMess.getSender())) {
            holdBackQueue.poll();
            messageQueue.add(nextMess);
            clock.update(nextMess.getClock());
            nextMess = holdBackQueue.peek();
            if(nextMess == null) {
                break;
            }
        }
    }

    @Override
    public Message deliver() throws InterruptedException
    {
        return messageQueue.take();
    }

    private class MessageComparator implements Comparator<Message>, Serializable {

        @Override
        public int compare(Message message, Message t1)
        {
            return t1.getClock().compareTo(message.getClock());
        }
    }
}
