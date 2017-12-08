package com.GroupManagement;

import com.Communication.Communication;
import com.Communication.Message;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {
    List<User> members;
    String id;
    User leader;
    Communication communicationModule;

    Group(String id, User leader, Communication communicationModule) throws RemoteException {
        super();
        members = new ArrayList<>();
        addMember(leader);
        this.leader = leader;
        this.id = id;
        this.communicationModule = communicationModule;
    }

    public void addMember(User user)
    {
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
    void memberJoined()
    {

    }

    /**
     * Notify group members a group member that left
     */
    void memberLeft()
    {

    }

    /**
     * Notify group members about new leader
     */
    /*void notifyNewLeader()
    {
        communicationModule.multicastNotification(members);
    }*/


}
