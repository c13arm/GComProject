
package com.GroupManagement;

import com.Communication.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserService extends UnicastRemoteObject implements UserServiceRmi {
    Group group;

    UserService(Group group) throws RemoteException {
        super();
        this.group = group;
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        System.out.println("in sendMessage");
        group.communicationModule.receive(message);
    }

    @Override
    public Group getGroupRemote() throws RemoteException {
        return group;
    }
}
