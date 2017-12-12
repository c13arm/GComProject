package com.Communication;

import com.GroupManagement.User;
import com.MessageOrdering.MessageOrdering;

import java.util.List;

public class Reliable extends Communication {
    public Reliable(MessageOrdering orderingModule) {
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
