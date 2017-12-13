package com.Communication;

import com.GroupManagement.User;
import com.MessageOrdering.MessageOrdering;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class NonReliable extends Communication implements Serializable {

    public NonReliable() {
        super();
    }

    public NonReliable(MessageOrdering messageOrdering)
    {
        super(messageOrdering);
    }

    @Override
    public List<User> multicast(List<User> members, Message mess)
    {
        List<User> failed = new ArrayList<>();
        if(orderingModule == null) {
            System.out.println("orderingModule");
        }
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
