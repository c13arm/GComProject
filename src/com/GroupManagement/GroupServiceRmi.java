package com.GroupManagement;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GroupServiceRmi extends Remote{
    public List<User> getMembersRemote() throws RemoteException;
}
