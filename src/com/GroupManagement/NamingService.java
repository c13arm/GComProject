package com.GroupManagement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NamingService extends UnicastRemoteObject implements NamingServiceRmi {
    //group name, leader
    HashMap<String,String> groupMap;

    public NamingService() throws RemoteException {
        super();
        groupMap = new HashMap<>();
    }

    @Override
    public String getLeader(String groupId) {
        return groupMap.get(groupId);
    }

    @Override
    public void registerGroup(String groupId, String leader) {
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

