package com.Communication;

import com.GroupManagement.User;
import com.MessageOrdering.VectorClock;

import java.io.Serializable;

/**
 *  The message class contains useful information such as the message itself,
 *  vector clock, vector type as well as the message's sender.
 */
public class Message implements Serializable
{
    private MessageType messageType;
    private User sender;
    private String mess;
    private VectorClock clock;

    // Join, leave, election, election done -- messages
    public Message(MessageType messageType, User sender)
    {
        this.messageType = messageType;
        this.sender = sender;
    }

    // Causal
    public Message(MessageType messageType, User sender, String mess, VectorClock clock)
    {
        this.messageType = messageType;
        this.sender = sender;
        this.mess = mess;
        this.clock = clock;
    }

    // Unordered
    public Message(MessageType messageType, User sender, String mess)
    {
        this.messageType = messageType;
        this.sender = sender;
        this.mess = mess;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public User getSender() {
        return sender;
    }

    public String getMess()
    {
        return mess;
    }

    public VectorClock getClock()
    {
        return clock;
    }

    public void setClock(VectorClock clock)
    {
        this.clock = clock;
    }

    @Override
    public String toString()
    {
        String ret = sender.getName();
        if(clock != null)
            ret += ":" + clock.toString();
        ret += ":"+ mess;
        return ret;
    }
}
