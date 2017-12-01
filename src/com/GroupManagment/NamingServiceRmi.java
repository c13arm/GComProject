package com.GroupManagment;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NamingServiceRmi extends Remote{

    public String getLeader(String groupId) throws RemoteException;
    public void registerGroup(String groupId, String leader) throws RemoteException;
    public List<String> getGroups() throws RemoteException;

}
