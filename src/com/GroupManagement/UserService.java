
package com.GroupManagement;

import com.Communication.Message;
import com.Communication.MessageType;

import java.rmi.NotBoundException;
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
        System.out.println("in sendMessage, from " + message.getSender().name + " type " + message.getMessageType().toString());
        if(message.getMessageType() == MessageType.JOIN) {
            User newUser = message.getSender();
            try {
                newUser.initStub();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
            group.addMember(newUser);
            System.out.println(group.members.size());
        } else if(message.getMessageType() == MessageType.LEAVE) {
            group.removeMember(message.getSender());
        }
        group.communicationModule.receive(message);
    }

    @Override
    public Group getGroupRemote() throws RemoteException {
        return group;
    }
}
