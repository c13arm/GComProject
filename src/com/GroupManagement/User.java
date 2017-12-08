package com.GroupManagement;

import com.Communication.Communication;
import com.Communication.Message;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class User implements Serializable {
    String name;
    String hostname;
    UserServiceRmi stub = null;

    User() {
        name = "";
        hostname = "";
    }

    User(String name, String hostname, boolean remote) throws RemoteException, NotBoundException {
        if(remote) {
            Registry registry = LocateRegistry.getRegistry(hostname);
            stub = (UserServiceRmi) registry.lookup("UserService" + name);
        }
        this.hostname = hostname;
        this.name = name;
        //System.out.println(this.name + "  " + this.hostname);
    }

    void initStub() throws RemoteException, NotBoundException {
        System.out.println(this.name + "  " + this.hostname);
        Registry registry = LocateRegistry.getRegistry(hostname);
        stub = (UserServiceRmi) registry.lookup("UserService" + name);
    }

    public String getName()
    {
        return name;
    }

    public void sendMessage(Message message) throws RemoteException {
        if(stub != null)
            stub.sendMessage(message);
        else
            System.out.println("stub is null");
    }
}
