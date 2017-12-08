package com.GroupManagement;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NamingServiceRmi extends Remote{

    public User getLeader(String groupId) throws RemoteException;
    public void registerGroup(String groupId, User leader) throws RemoteException;
    public List<String> getGroups() throws RemoteException;
    public void removeGroup(String groupId) throws RemoteException;

}
