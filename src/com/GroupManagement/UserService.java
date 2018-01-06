
package com.GroupManagement;

import com.Communication.Message;
import com.Communication.MessageType;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserService extends UnicastRemoteObject implements UserServiceRmi {
    Group group;
    User user;

    UserService(Group group, User uid) throws RemoteException {
        super();
        this.group = group;
        this.user = uid;
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
            if(group.leader.equals(message.getSender())) {
                group.election(user, user);
            }
        } else if(message.getMessageType() == MessageType.ELECTION) {
            User sender = message.getSender();
            System.out.println("Election " + sender.name + ":" + user.name);
            if(user.equals(sender)){
                group.leader = user;
                group.stub.registerGroup(group.id, user);
                group.notifyNewLeader(user);
            } else {
                group.election(sender, user);
            }
        } else if (message.getMessageType() == MessageType.ELECTION_DONE) {
            group.leader = message.getSender();
            if(!user.equals(group.leader)) {
                group.notifyNewLeader(user);
            }
        }
        group.communicationModule.receive(message);
    }

    @Override
    public Group getGroupRemote() throws RemoteException {
        return group;
    }
}
