package com.Communication;

import com.GroupManagement.User;
import com.MessageOrdering.MessageOrdering;

import java.util.List;

/**
 * Class module for the implementation of Tree-based reliable multicasted messages
 * (Tree-based reliable is currently not working)
 */
public class TreeBasedReliableMultiCastModule extends Communication {
    public TreeBasedReliableMultiCastModule(MessageOrdering orderingModule) {
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
