package com.hahacorales;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 5000;
    private static final int THREAD_POOL_SIZE = 50;
    private static List<ClientHandler> connectedClients = Collections.synchronizedList(new ArrayList<>());
    private static ServerSocket serverSocket;
    private static ExecutorService executorService;
    private static int clientCounter = 0;

    public static void main(String[] args) {
        
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            System.out.println("Thread pool size: " + THREAD_POOL_SIZE);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientCounter++;
                System.out.println("[Server] New client connected: " + clientSocket.getInetAddress() + " (Client #" + clientCounter + ")");

            
                ClientHandler clientHandler = new ClientHandler(clientSocket, connectedClients, clientCounter);
                connectedClients.add(clientHandler);
                executorService.execute(clientHandler);
                System.out.println("[Server] Active connections: " + connectedClients.size());
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
                executorService.shutdown();
                System.out.println("Server shut down");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
