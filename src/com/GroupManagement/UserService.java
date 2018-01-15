
package com.GroupManagement;

import com.Communication.Message;
import com.Communication.MessageType;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class contains what happens when the user sends a message of different types
 */
public class UserService extends UnicastRemoteObject implements UserServiceRmi {
    private Group group;
    private User user;

    UserService(Group group, User uid) throws RemoteException {
        super();
        this.group = group;
        this.user = uid;
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        if(message.getMessageType() == MessageType.JOIN) {
            User newUser = message.getSender();
            try {
                newUser.initStub();
            } catch (NotBoundException e) {
                System.err.println("Failed to send Join message");
                System.exit(1);
            }
            group.addMember(newUser);
        } else if(message.getMessageType() == MessageType.LEAVE) {
            group.removeMember(message.getSender());
            if(group.leader.equals(message.getSender())) {
                group.election(user, user);
            }
        } else if(message.getMessageType() == MessageType.ELECTION) {
            User sender = message.getSender();
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
        if(message.getMessageType() != MessageType.ELECTION &&
                message.getMessageType() != MessageType.ELECTION_DONE) {
            group.communicationModule.receive(message);
        }
    }

    @Override
    public Group getGroupRemote() throws RemoteException {
        return group;
    }
}
