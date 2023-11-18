package org.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private static final int PORT = 12345;
    private static final int THREAD_POOL_SIZE = 10;
    private static final long BROADCAST_INTERVAL = 5000; // 5 seconds

    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    private static final ArrayList<String> messages = new ArrayList<>();

    private static volatile boolean serverRunning = true;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            logger.info("Server is running on port {}", PORT);

            // Thread for broadcasting messages at regular intervals
            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
            scheduledExecutorService.scheduleAtFixedRate(
                    Server::broadcastMessage,
                    0,
                    BROADCAST_INTERVAL,
                    TimeUnit.MILLISECONDS
            );

            while (serverRunning) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new ClientHandler(clientSocket));
            }

            // Shutdown server
            serverSocket.close();
            executorService.shutdown();
            scheduledExecutorService.shutdown();
            serverRunning = false;
        } catch (IOException e) {
            logger.error("Error in Server main method", e);
        }
    }

    private static void broadcastMessage() {
        if (!messages.isEmpty()) {
            for (String message: messages) {
                logger.info("Broadcasting message: {}", message);
                executorService.execute(() -> {
                    for (ClientHandler clientHandler : ClientHandler.clientHandlers) {
                        clientHandler.sendMessage(message);
                    }
                });
            }
        }
    }

    static void addLatestMessage(String message) {
        messages.add(message);
    }
}

