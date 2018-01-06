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

public class Group implements Serializable {
    List<User> members;
    String id;
    User leader;
    Communication communicationModule;

    Group(String id, User leader, Communication communicationModule){
        super();
        members = new ArrayList<>();
        members.add(leader);
        this.leader = leader;
        this.id = id;
        this.communicationModule = communicationModule;
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
            System.out.println(u.name);
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
    /*void notifyNewLeader()
    {
        communicationModule.multicastNotification(members);
    }*/

    protected Group clone() {
        Group ret = new Group(this.id, this.leader, this.communicationModule);
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
