package org.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            logger.info("Connected to server {}:{}", SERVER_ADDRESS, SERVER_PORT);

            Scanner scanner = new Scanner(System.in);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Thread for receiving messages from the server
            new Thread(() -> {
                try {
                    Scanner serverScanner = new Scanner(socket.getInputStream());
                    while (serverScanner.hasNextLine()) {
                        String message = serverScanner.nextLine();
                        logger.info("Received from server: " + message);
                    }
                } catch (IOException e) {
                    logger.error("Error in receiving messages from a server", e);
                }
            }).start();

            // Thread for sending messages to the server
            new Thread(() -> {
                while (true) {
                    String message = scanner.nextLine();
                    out.println(message);
                }
            }).start();

        } catch (IOException e) {
            logger.error("Error in Client main method", e);
        }
    }
}

