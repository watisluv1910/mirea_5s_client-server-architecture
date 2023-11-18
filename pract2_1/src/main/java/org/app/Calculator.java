package org.app;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Calculator extends Remote {

    ArrayList<Double> solveQuadraticEquation(int a, int b, int c) throws RemoteException;
}
