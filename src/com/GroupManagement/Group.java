package com.GroupManagement;

import com.Communication.Communication;
import com.Communication.Message;
import com.Communication.MessageType;

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
        members.remove(user);
    }

    public void multicast(Message message) {
        communicationModule.multicast(members, message);
    }

    /**
     * Notify group members of newly joined group member
     */
    void memberJoined(User newMember)
    {
        Message mess = new Message(MessageType.JOIN, newMember);
        communicationModule.multicast(members, mess);
    }

    /**
     * Notify group members a group member that left
     */
    void memberLeft()
    {

    }

    boolean isMember(User user) {
        return members.contains(user);
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

}
