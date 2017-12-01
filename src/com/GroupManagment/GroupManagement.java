package com.GroupManagment;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class GroupManagement {
    static NamingServiceRmi stub;

    List<User> members;
    String groupName;

    public static void main(String args[]) {
        try {
            List<String> groups;
            Registry registry = LocateRegistry.getRegistry("localhost");

            NamingServiceRmi stub = (NamingServiceRmi) registry.lookup("NamingService");
            stub.registerGroup("test", "testLeader");
            stub.registerGroup("test2", "testLeader2");
            stub.registerGroup("test3", "testLeader4");
            stub.registerGroup("test4", "testLeader5");

            System.out.println("Result: "+stub.getLeader("test"));
            groups = stub.getGroups();
            for (String g: groups) {
                System.out.println(g);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static  {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            stub = (NamingServiceRmi) registry.lookup("NamingService");
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

    static List<String> getGroups() throws RemoteException {
        return stub.getGroups();
    }

    static void createGroup(String groupName, User leader) throws RemoteException {
        stub.registerGroup(groupName, leader.getName());
    }

    GroupManagement(String groupId) {

        groupName = groupId;
        //stub.getLeader()
    }

    /*void removeGroup(String groupName) {

    }*/

    void addMember(User user) {

    }

    void removeMember(User user) {

    }

    List<User> getMembers() {
        return null;
    }
}
