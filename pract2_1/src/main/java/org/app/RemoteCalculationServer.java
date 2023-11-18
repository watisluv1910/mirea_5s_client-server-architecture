package org.app;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class RemoteCalculationServer implements Calculator {

    @Override
    public ArrayList<Double> solveQuadraticEquation(int a, int b, int c) throws RemoteException {
        double d = Math.pow(b, 2) - 4 * a * c;

        ArrayList<Double> result = new ArrayList<>();

        if (d > 0) {
            result.add((-b + Math.sqrt(d)) / (2 * a));
            result.add((-b - Math.sqrt(d)) / (2 * a));
        } else if (d == 0) {
            result.add((double)-b /(2*a));
        } else {
            result.add((double) -b / (2 * a));
            result.add(Math.sqrt(-d) / (2 * a));
        }

        return result;
    }
}

