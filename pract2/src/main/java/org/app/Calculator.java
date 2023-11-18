package org.app;

import com.sun.tools.javac.util.Pair;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {

    Pair<Double, Double> solveQuadraticEquation(int a, int b, int c) throws RemoteException;
}
