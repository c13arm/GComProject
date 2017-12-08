package com.GroupManagement;

import com.Communication.Communication;
import com.Communication.Message;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class User {
    String name;
    UserServiceRmi stub;


    User(String name, String hostname) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(hostname);
        stub = (UserServiceRmi) registry.lookup("UserService");
        this.name = name;
    }

    String getName()
    {
        return name;
    }

    public void sendMessage(Message message) {
        //stub.sendMessage();
    }

    public List<User> getMembers() throws RemoteException {
        return stub.getMembers();
    }

    Communication getComMod() {
        return null;
    }
}
