package org.app;

import com.sun.tools.javac.util.Pair;

import java.rmi.RemoteException;

public class RemoteCalculationServer implements Calculator {

    @Override
    public Pair<Double, Double> solveQuadraticEquation(int a, int b, int c) throws RemoteException {
        double d = Math.pow(b, 2) - 4 * a * c;

        if (d > 0) {
            return new Pair<>(
                (-b + Math.sqrt(d)) / (2 * a),
                (-b - Math.sqrt(d)) / (2 * a)
            );
        } else if (d == 0) {
            return new Pair<>(
                (double) -b / (2 * a),
                null
            );
        } else {
            return new Pair<>(
                (double) -b / (2 * a),
                Math.sqrt(-d) / (2 * a)
            );
        }
    }
}
