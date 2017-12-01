package com.GroupManagment;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GroupManagement {
    public static void main(String args[]) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");

            NamingServiceRmi stub = (NamingServiceRmi) registry.lookup("NamingService");
            //stub.registerGroup("test", "testLeader");

            //System.out.println("Result: "+stub.getLeader("test"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
