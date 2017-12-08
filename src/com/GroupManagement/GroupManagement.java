package com.GroupManagement;

import com.Communication.Communication;
import com.Communication.Message;
import com.Communication.MessageType;
import com.Communication.NonReliable;
import com.MessageOrdering.UnorderedOrdering;

import java.net.InetAddress;
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
    NamingServiceRmi stub;

    public static void main(String args[]) {
        new GroupManagement("localhost");
    }

    public GroupManagement(String hostName) {
        try {
            List<String> groups;
            Group group;
            Registry registry = LocateRegistry.getRegistry(hostName);
            stub = (NamingServiceRmi) registry.lookup("NamingService");

            groups = stub.getGroups();
            if(!groups.contains("test")) {
                group = createGroup("test", new User("test", InetAddress.getLocalHost().getHostName(), false), new NonReliable(new UnorderedOrdering()));
            } else {
                group = joinGroup("test", "user");
                group.multicast(new Message(MessageType.MESS));
            }

            System.out.println("Result: "+stub.getLeader("test").hostname +"  " +stub.getLeader("test").name );
            groups = stub.getGroups();
            for (String g: groups) {
                System.out.println(g);
            }
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    List<String> getGroups() throws RemoteException {
        return stub.getGroups();
    }

    Group createGroup(String groupName, User leader, Communication communicationModule) throws RemoteException {
        Group group = new Group(groupName, leader, communicationModule);
        try {
            UserServiceRmi userService = new UserService(group);
            Registry registry = LocateRegistry.getRegistry();

            registry.rebind("UserService" + leader.getName() , userService);
        } catch (Exception e) {
            e.printStackTrace();
        }
        stub.registerGroup(groupName, leader);
        return group;
    }

    Group joinGroup(String groupName, String userName) throws RemoteException {
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
        try {
            UserServiceRmi userService = new UserService(group);
            Registry registry = LocateRegistry.getRegistry();

            registry.rebind("UserService" + userName, userService);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
