package com.Communication;

import com.GroupManagement.User;
import com.MessageOrdering.MessageOrdering;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class module for the implementation of Non-reliable multicasted messages
 * (Non-reliable is currently working)
 */
public class NonReliableMulticastModule extends Communication implements Serializable {

    public NonReliableMulticastModule(MessageOrdering messageOrdering)
    {
        super(messageOrdering);
    }

    @Override
    public List<User> multicast(List<User> members, Message mess)
    {
        List<User> failed = new ArrayList<>();
        for (User member: members)
        {
            try {
                member.sendMessage(mess);
            } catch (RemoteException e) {
                failed.add(member);
            }
        }
        return failed;
    }

    @Override
    public void receive(Message mess)
    {
        super.orderingModule.receive(mess);
    }
}
