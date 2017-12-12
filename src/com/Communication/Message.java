package com.Communication;

import com.GroupManagement.User;

import java.io.Serializable;

public class Message implements Serializable
{
    private MessageType messageType;
    private User sender;
    private String mess;

    public Message(MessageType messageType, User sender)
    {
        this.messageType = messageType;
        this.sender = sender;
    }

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
}
