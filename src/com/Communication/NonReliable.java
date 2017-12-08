package com.Communication;

import com.GroupManagement.User;
import com.MessageOrdering.MessageOrdering;

import java.util.List;

public class NonReliable extends Communication {

    NonReliable(MessageOrdering messageOrdering)
    {
        super(messageOrdering);
    }

    @Override
    public void multicast(List<User> members, Message mess)
    {
        Message newMess = super.orderingModule.prepareMessage(mess);
        for (User member: members)
        {
            member.sendMessage(newMess);
        }
    }

    @Override
    public void receive(Message mess)
    {
        super.orderingModule.receive(mess);
    }
}
