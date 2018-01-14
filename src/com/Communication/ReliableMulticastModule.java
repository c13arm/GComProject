package com.Communication;

import com.GroupManagement.User;
import com.MessageOrdering.MessageOrdering;

import java.util.List;

/**
 * Class module for the implementation of reliable multicasted messages
 * (Reliable is currently not working)
 */
public class ReliableMulticastModule extends Communication {
    public ReliableMulticastModule(MessageOrdering orderingModule) {
        super(orderingModule);
    }

    @Override
    public List<User> multicast(List<User> members, Message mess) {
        return null;
    }

    @Override
    public void receive(Message mess) {

    }
}
