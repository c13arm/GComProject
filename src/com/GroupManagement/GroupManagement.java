package com.GroupManagement;

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

    List<User> members;
    String groupName;

    public static void main(String args[]) {
        new GroupManagement("localhost");
    }

    public GroupManagement(String hostName) {
        try {
            List<String> groups;
            Registry registry = LocateRegistry.getRegistry(hostName);
            stub = (NamingServiceRmi) registry.lookup("NamingService");

            stub.registerGroup("test", "testLeader");
            System.out.println("Result: "+stub.getLeader("test"));
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
        }
    }

    List<String> getGroups() throws RemoteException {
        return stub.getGroups();
    }

    void createGroup(String groupName, User leader) throws RemoteException {
        stub.registerGroup(groupName, leader.getName());
    }

    /**
     * Remove group when all group members left
     * @param groupId Group Id
     * @throws RemoteException
     */
    void removeGroup(String groupId) throws RemoteException {
        stub.removeGroup(groupId);
    }





    List<User> getMembers() {
        return null;
    }
}
