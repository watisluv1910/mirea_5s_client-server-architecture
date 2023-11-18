package org.example;

public class App 
{
    private static final Object lock = new Object();
    private static boolean isPingTurn = true;

    public static void main( String[] args )
    {
        Thread pingThread = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (!isPingTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("Ping ");
                    isPingTurn = false;
                    lock.notify();
                }
            }
        });

        Thread pongThread = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (isPingTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("Pong ");
                    isPingTurn = true;
                    lock.notify();
                }
            }
        });

        pingThread.start();
        pongThread.start();
    }
}
