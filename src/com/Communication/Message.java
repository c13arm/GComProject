package com.Communication;

import com.GroupManagement.User;

import java.io.Serializable;

public class Message implements Serializable
{
    private MessageType messageType;
    private User sender;

    public Message(MessageType messageType, User sender)
    {
        this.messageType = messageType;
        this.sender = sender;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public User getSender() {
        return sender;
    }
}
