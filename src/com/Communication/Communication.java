package com.Communication;

import com.GroupManagement.User;
import com.MessageOrdering.MessageOrdering;

import java.io.Serializable;
import java.util.List;

public abstract class Communication implements Serializable{
    MessageOrdering orderingModule;

    Communication() {

    }
    Communication(MessageOrdering orderingModule) {
        this.orderingModule = orderingModule;
    }

    abstract public void multicast(List<User> members, Message mess);
    abstract public void receive(Message mess);

}
