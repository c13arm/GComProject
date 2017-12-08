package com.Communication;

import java.io.Serializable;

public class Message implements Serializable
{
    MessageType messageType;
    public Message(MessageType messageType)
    {
        this.messageType = messageType;
    }


}
