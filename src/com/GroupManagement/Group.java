package com.GroupManagement;

import com.Communication.Communication;
import com.Communication.Message;
import com.Communication.MessageType;

import javax.swing.*;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains information about the group, a group consists of users and each
 * group has its own communication module
 */
public class Group implements Serializable {
    List<User> members;
    String id;
    User leader;
    Communication communicationModule;
    NamingServiceRmi stub;

    Group(String id, User leader, Communication communicationModule, NamingServiceRmi stub){
        super();
        members = new ArrayList<>();
        members.add(leader);
        this.leader = leader;
        this.id = id;
        this.communicationModule = communicationModule;
        this.stub = stub;
    }

    public void addMember(User user) {

        members.add(user);
    }

    public void removeMember(User user)
    {
        if(members.remove(user)) {
            memberLeft(user);
        }
    }

    public void multicast(Message message) {
        List<User> failed = communicationModule.multicast(members, message);
        for (User u : failed) {
            removeMember(u);
        }
    }

    public List<User> getMembers() {
        return members;
    }

    /**
     * Notify group members of newly joined group member
     */
    void memberJoined(User newMember)
    {
        Message mess = new Message(MessageType.JOIN, newMember);
        communicationModule.prepareMessage(mess);
        communicationModule.multicast(members, mess);
    }

    /**
     * Notify group members a group member that left
     */
    public void memberLeft(User oldMember)
    {
        Message mess = new Message(MessageType.LEAVE, oldMember);
        communicationModule.prepareMessage(mess);
        communicationModule.multicast(members, mess);
    }

    boolean isMember(User user) {
        return members.contains(user);
    }

    public Message createMessage(String mess, User user)
    {
        Message message = new Message(MessageType.MESS, user, mess);
        return communicationModule.prepareMessage(message);
    }
    /**
     * Notify group members about new leader
     */
    void notifyNewLeader(User sender) throws RemoteException
    {
        Message message = new Message(MessageType.ELECTION_DONE, leader);
        int index = members.indexOf(sender);
        if(index == members.size() - 1) {
            index = 0;
        } else {
            index++;
        }
        members.get(index).sendMessage(message);
    }

    void election(User user, User sender)
    {
        if(members.size() < 2) {
            leader = sender;
            return;
        }
        int index = members.indexOf(sender);
        if(index == members.size() - 1) {
            index = 0;
        } else {
            index++;
        }
        User next = members.get(index);
        User toSend;
        if(user.name.compareTo(sender.getName()) < 0) {
            toSend = sender;
        } else {
            toSend = user;
        }
        Message message = new Message(MessageType.ELECTION, toSend);
        try
        {
            next.sendMessage(message);
        } catch (RemoteException e)
        {
            System.err.println("Failed to send election message");
            System.exit(1);
        }
    }

    protected Group clone() {
        Group ret = new Group(this.id, this.leader, this.communicationModule, this.stub);
        ret.members.addAll(this.members);
        return ret;
    }

    public Message deliver() throws InterruptedException
    {
        return communicationModule.deliver();
    }

    public ListModel<Message> getHoldBackListModel() {
        return communicationModule.getHoldBackListModel();
    }

}
