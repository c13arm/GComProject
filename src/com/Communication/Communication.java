package com.Communication;

import com.GroupManagement.User;
import com.MessageOrdering.MessageOrdering;

import javax.swing.*;
import java.io.Serializable;
import java.util.List;

public abstract class Communication implements Serializable{
    MessageOrdering orderingModule;

    Communication() {

    }
    Communication(MessageOrdering orderingModule) {
        this.orderingModule = orderingModule;
    }

    abstract public List<User> multicast(List<User> members, Message mess);
    abstract public void receive(Message mess);
    public Message deliver() throws InterruptedException
    {
        return orderingModule.deliver();
    }
    public Message prepareMessage(Message message) {
        return orderingModule.prepareMessage(message);
    }

    public ListModel<Message> getHoldBackListModel() {
        return orderingModule.getHoldBackListModel();
    }

}
