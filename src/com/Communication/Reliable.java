package com.Communication;

import com.GroupManagement.User;
import com.MessageOrdering.MessageOrdering;

import java.util.List;

public class Reliable extends Communication {
    Reliable(MessageOrdering orderingModule) {
        super(orderingModule);
    }

    @Override
    public void multicast(List<User> members, Message mess) {

    }

    @Override
    public void receive(Message mess) {

    }
}
