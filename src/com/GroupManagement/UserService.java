package com.GroupManagement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class UserService extends UnicastRemoteObject implements UserServiceRmi{


    protected UserService() throws RemoteException {
    }

    @Override
    public void sendMessage() throws RemoteException {

    }

    @Override
    public List<User> getMembers() throws RemoteException {
        return null;
    }
}
