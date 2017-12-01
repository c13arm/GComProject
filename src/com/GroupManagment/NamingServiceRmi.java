package com.GroupManagment;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NamingServiceRmi extends Remote{

    public String getLeader(String groupId) throws RemoteException;
    public void registerGroup(String groupId, String leader) throws RemoteException;

}
