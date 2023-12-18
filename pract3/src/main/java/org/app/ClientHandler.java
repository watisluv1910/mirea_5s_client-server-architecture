package org.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    private final Socket clientSocket;
    private final PrintWriter out;

    static final List<ClientHandler> clientHandlers = new ArrayList<>();

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        clientHandlers.add(this);
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(clientSocket.getInputStream());
            logger.info("Client socket connected: {}", clientSocket.isConnected());
            while (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                logger.info("Received message from client: " + message);
                Server.addLatestMessage(message);
            }
        } catch (IOException e) {
            logger.error("Error in ClientHandler run method", e);
        } finally {
            try {
                clientSocket.close();
                clientHandlers.remove(this);
            } catch (IOException e) {
                logger.error("Error closing client socket", e);
            }
        }
    }

    void sendMessage(String message) {
        out.println(message);
    }
}

