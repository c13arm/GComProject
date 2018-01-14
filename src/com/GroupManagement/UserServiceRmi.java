package com.GroupManagement;

import com.Communication.Message;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for the user's RMI
 */
public interface UserServiceRmi extends Remote {

    void sendMessage(Message message) throws RemoteException;

    Group getGroupRemote() throws RemoteException;
}
