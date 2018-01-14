package com.GroupManagement;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for the name server's RMI
 */
public interface NamingServiceRmi extends Remote{

    User getLeader(String groupId) throws RemoteException;
    void registerGroup(String groupId, User leader) throws RemoteException;
    List<String> getGroups() throws RemoteException;
    void removeGroup(String groupId) throws RemoteException;

}
