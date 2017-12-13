package com.MessageOrdering;

import com.Communication.Message;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UnorderedOrdering implements MessageOrdering, Serializable
{
    BlockingQueue<Message> messageQueue;

    public UnorderedOrdering()
    {
        messageQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public Message prepareMessage(Message message)
    {
        return message;
    }

    @Override
    public void receive(Message message)
    {
        try
        {
            messageQueue.put(message);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public Message deliver() throws InterruptedException
    {
        return messageQueue.take();
    }
}
