package com.GroupManagment;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class NamingService extends UnicastRemoteObject implements NamingServiceRmi {
    //group name -> leader
    HashMap<String,String> groupMap;

    public NamingService() throws RemoteException {
        super();
    }

    @Override
    public String getLeader(String groupId) {
        return groupMap.get(groupId);
    }

    @Override
    public void registerGroup(String groupId, String leader) {
        groupMap.put(groupId, leader);
    }
}

