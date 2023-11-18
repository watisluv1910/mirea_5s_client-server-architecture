package org.app;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientMain {
    public static final String UNIQUE_BINDING_NAME = "server.calculator";

    public static void main(String[] args) throws RemoteException, NotBoundException {

        final Registry registry = LocateRegistry.getRegistry(2732);

        Calculator calculator = (Calculator) registry.lookup(UNIQUE_BINDING_NAME);

        System.out.println("Answer: " + calculator.solveQuadraticEquation(4, 5, 1));
    }
}

