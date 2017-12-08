package com.Communication;

import com.GroupManagement.User;
import com.MessageOrdering.MessageOrdering;

import java.util.List;

public abstract class Communication {
    MessageOrdering orderingModule;

    Communication(MessageOrdering orderingModule) {
        this.orderingModule = orderingModule;
    }

    abstract void multicast(List<User> members, Message mess);
    abstract void receive(Message mess);

}
