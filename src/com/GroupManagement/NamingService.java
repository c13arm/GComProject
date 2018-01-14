package com.GroupManagement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The name server, consists of a hashmap which contains the leader and
 * the leader's group
 */
public class NamingService extends UnicastRemoteObject implements NamingServiceRmi {
    //group name, leader
    HashMap<String,User> groupMap;

    public NamingService() throws RemoteException {
        super();
        groupMap = new HashMap<>();
    }

    @Override
    public User getLeader(String groupId) throws RemoteException {
        return groupMap.get(groupId);
    }

    @Override
    public void registerGroup(String groupId, User leader) throws RemoteException {
        groupMap.put(groupId, leader);
    }

    @Override
    public void removeGroup(String groupId) throws RemoteException {
        groupMap.remove(groupId);
    }

    @Override
    public List<String> getGroups() throws RemoteException {
        return new ArrayList<>(groupMap.keySet());
    }

}

