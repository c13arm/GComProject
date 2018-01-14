package com.GroupManagement;

import com.Communication.Message;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class contains information about the user
 */
public class User implements Serializable {
    String name;
    String hostname;
    UserServiceRmi stub = null;

    public User(String name) throws UnknownHostException {
        if(name == null)
            throw new IllegalArgumentException();
        this.hostname = InetAddress.getLocalHost().getCanonicalHostName();
        this.name = name;
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!name.equals(user.name)) return false;
        return hostname.equals(user.hostname);
    }

    @Override
    public int hashCode()
    {
        int result = name.hashCode();
        result = 31 * result + hostname.hashCode();
        return result;
    }
}
