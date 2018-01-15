package com.MessageOrdering;

import com.Communication.Message;

import javax.swing.*;
import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class implements unordered ordering, which isn't more to it than just creating and sending the message
 */
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
            System.err.println("Failed when receiving message");
            System.exit(1);
        }

    }

    @Override
    public Message deliver() throws InterruptedException
    {
        return messageQueue.take();
    }

    @Override
    public ListModel<Message> getHoldBackListModel()
    {
        return new DefaultListModel<>();
    }
}
