package com.GroupManagement;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Name server's main class, used to start the name server
 */
public class NameServer {
    public static void main(String args[]) {
        try {
            NamingServiceRmi namingService = new NamingService();
            Registry registry = LocateRegistry.getRegistry();

            registry.rebind("NamingService", namingService);
        } catch (Exception e) {
            System.err.println("Failed to start the name server");
            System.exit(1);
        }
    }

}
