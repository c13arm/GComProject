package com.Communication;

import com.GroupManagement.User;

import java.util.List;

public class NonReliable implements Communication{
    @Override
    public void multicast(List<User> members, Message mess)
    {
        for (User member: members)
        {
            member.sendMessage(mess);
        }
    }

    @Override
    public void receive(Message mess) {
        // MessageOrdering
    }
}
