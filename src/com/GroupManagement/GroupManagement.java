package com.GroupManagement;

import com.Communication.Communication;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * Provide operations to create and remove groups, as well as add and remove members from groups
 * Monitoring a group and indicates when a member of the group crashes
 * Notify members in a group about changes in group composition
 * When a group gets sent a message, the group name is resolved into a list of group members
 */
public class GroupManagement {
    private NamingServiceRmi stub;

    public GroupManagement(String hostName) {
        try {
            Registry registry = LocateRegistry.getRegistry(hostName);
            stub = (NamingServiceRmi) registry.lookup("NamingService");
        } catch (NotBoundException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<String> getGroups() throws RemoteException {
        return stub.getGroups();
    }

    public Group createGroup(String groupName, User leader, Communication communicationModule) throws RemoteException {
        Group group = new Group(groupName, leader, communicationModule, stub);
        try {
            UserServiceRmi userService = new UserService(group, leader);
            Registry registry = LocateRegistry.getRegistry();

            registry.rebind("UserService" + leader.getName() , userService);
        } catch (Exception e) {
            e.printStackTrace();
        }
        stub.registerGroup(groupName, leader);
        return group;
    }



    public Group joinGroup(String groupName, User localUser) throws RemoteException, UnknownHostException {
        User leader = stub.getLeader(groupName);
        try {
            leader.initStub();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException r) {
            r.printStackTrace();
        }
        Group group = leader.stub.getGroupRemote();
        for (User u: group.members) {
            try {
                u.initStub();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
        group.addMember(localUser);
        try {
            UserServiceRmi userService = new UserService(group, localUser);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("UserService" + localUser.getName() , userService);
        } catch (Exception e) {
            e.printStackTrace();
        }
        group.memberJoined(localUser);
        return group;
    }

    /**
     * Remove group when all group members left
     * @param groupId Group Id
     * @throws RemoteException
     */
    void removeGroup(String groupId) throws RemoteException {
        stub.removeGroup(groupId);
    }
}
