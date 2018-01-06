package com.MessageOrdering;

import com.Communication.Message;

import javax.swing.*;
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
    HoldBackListModel holdBackQueueListModel;

    public CausalOrdering()
    {
        clock = new VectorClock(10);
        messageQueue = new LinkedBlockingQueue<>();
        holdBackQueue = new PriorityBlockingQueue<>(10, new MessageComparator());
        holdBackQueueListModel = new HoldBackListModel();
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
            System.out.println("in while");
        }
        holdBackQueueListModel.update();
    }

    @Override
    public Message deliver() throws InterruptedException
    {
        return messageQueue.take();
    }

    @Override
    public ListModel<Message> getHoldBackListModel()
    {
        return holdBackQueueListModel;
    }

    private class MessageComparator implements Comparator<Message>, Serializable {
        @Override
        public int compare(Message message, Message t1)
        {
            return message.getClock().compareTo(t1.getClock());
        }
    }

    private class HoldBackListModel extends AbstractListModel<Message>
    {
        Message[] messages;

        HoldBackListModel() {
            messages = holdBackQueue.toArray(new Message[0]);
        }

        @Override
        public int getSize()
        {
            return holdBackQueue.size();
        }

        @Override
        public Message getElementAt(int i)
        {
            return messages[i];
        }

        private void update() {
            System.out.println("In update");
            Message[] oldMessages = messages;
            messages = holdBackQueue.toArray(new Message[0]);
            if(oldMessages.length > messages.length) {
                fireIntervalRemoved(this, messages.length, oldMessages.length - 1);
            } else if (messages.length > oldMessages.length) {
                fireIntervalAdded(this, oldMessages.length - 1 , messages.length - 1);
            }
            fireContentsChanged(this, 0, messages.length - 1);
            System.out.println(messages.length);
        }
    }
}
