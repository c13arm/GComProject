package com.GroupManagement;

import com.Communication.Message;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserServiceRmi extends Remote {

    public void sendMessage(Message message) throws RemoteException;

    public Group getGroupRemote() throws RemoteException;
}
