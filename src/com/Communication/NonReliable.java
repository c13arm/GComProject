package com.Communication;

import com.GroupManagement.User;
import com.MessageOrdering.MessageOrdering;

import java.io.Serializable;
import java.rmi.RemoteException;
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
    public void multicast(List<User> members, Message mess)
    {
        if(orderingModule == null) {
            System.out.println("orderingModule");
        }
        Message newMess = super.orderingModule.prepareMessage(mess);
        for (User member: members)
        {
            try {
                member.sendMessage(newMess);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void receive(Message mess)
    {
        super.orderingModule.receive(mess);
    }
}
