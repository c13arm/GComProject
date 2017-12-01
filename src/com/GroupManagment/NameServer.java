package com.GroupManagment;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NameServer {
    public static void main(String args[]) {
        try {
            NamingServiceRmi namingService = new NamingService();
            Registry registry = LocateRegistry.getRegistry();

            registry.rebind("NamingService", namingService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
