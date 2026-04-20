package com.hahacorales;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private List<ClientHandler> connectedClients;
    private String clientName;
    private int clientId;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private volatile boolean isConnected = true;

    public ClientHandler(Socket socket, List<ClientHandler> connectedClients, int clientId) {
        this.socket = socket;
        this.connectedClients = connectedClients;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("ClientHandler-" + clientId);
        
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

       
            out.println("ENTER_NAME");
            clientName = in.readLine();
            
            if (clientName == null || clientName.trim().isEmpty()) {
                clientName = "Client#" + clientId;
            }
            
            System.out.println("[" + Thread.currentThread().getName() + "] " + clientName + " joined the chat");

          
            broadcastMessage("*** " + clientName + " joined the chat ***");

            String message;
            while (isConnected && (message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("EXIT")) {
                    break;
                }
                System.out.println("[" + Thread.currentThread().getName() + "] " + clientName + ": " + message);
                broadcastMessage(clientName + ": " + message);
            }


            isConnected = false;
            connectedClients.remove(this);
            broadcastMessage("*** " + clientName + " left the chat ***");
            System.out.println("[" + Thread.currentThread().getName() + "] " + clientName + " disconnected");

        } catch (IOException e) {
            System.err.println("[" + Thread.currentThread().getName() + "] Error handling client: " + e.getMessage());
        } finally {
            isConnected = false;
            try {
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcastMessage(String message) {
        lock.readLock().lock();
        try {
            for (ClientHandler client : connectedClients) {
                client.sendMessage(message);
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public void sendMessage(String message) {
        lock.readLock().lock();
        try {
            if (out != null && isConnected) {
                out.println(message);
            }
        } finally {
            lock.readLock().unlock();
        }
    }
}
