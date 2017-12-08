package com.GroupManagement;

import com.Communication.Communication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Group extends UnicastRemoteObject implements GroupServiceRmi{
    List<User> members;
    String id;
    User leader;
    Communication communicationModule;

    Group(String id, User leader) throws RemoteException {
        super();
        addMember(leader);
        this.leader = leader;
        this.id = id;
        setCommunicationModule(leader.getComMod());
    }

    public void addMember(User user)
    {
        members.add(user);
    }

    public void removeMember(User user)
    {
        members.remove(user);
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
    void notifyNewLeader()
    {
        communicationModule.multicastNotification(members);
    }

    void setCommunicationModule(Communication communicationModule)
    {
        this.communicationModule = communicationModule;
    }

    @Override
    public List<User> getMembersRemote() throws RemoteException {
        return null;
    }
}
