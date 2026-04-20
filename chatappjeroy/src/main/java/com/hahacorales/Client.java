package com.hahacorales;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;
    private static AtomicBoolean isConnected = new AtomicBoolean(false);

    public static void main(String[] args) {
        Socket socket = null;
        PrintWriter out = null;
        final BufferedReader in;
        BufferedReader userInput = null;
        Thread receiveThread = null;

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            isConnected.set(true);
            System.out.println("[Client] Connected to server at " + SERVER_ADDRESS + ":" + SERVER_PORT);

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            userInput = new BufferedReader(new InputStreamReader(System.in));

         
            String serverRequest = in.readLine();
            if ("ENTER_NAME".equals(serverRequest)) {
                System.out.print("Enter your name: ");
                String name = userInput.readLine();
                out.println(name.isEmpty() ? "Anonymous" : name);
            }

        
            receiveThread = new Thread(() -> {
                Thread.currentThread().setName("MessageReceiver");
                try {
                    String message;
                    while (isConnected.get() && (message = in.readLine()) != null) {
                        System.out.println("\n[Received] " + message);
                        System.out.print("You: ");
                    }
                } catch (IOException e) {
                    if (isConnected.get()) {
                        System.err.println("[" + Thread.currentThread().getName() + "] Connection lost: " + e.getMessage());
                    }
                } finally {
                    isConnected.set(false);
                }
            });
            receiveThread.setDaemon(true);
            receiveThread.start();

           
            System.out.println("[Client] Type your messages (type EXIT to quit):");
            String message;
            while (isConnected.get() && (message = userInput.readLine()) != null) {
                out.println(message);
                if ("EXIT".equalsIgnoreCase(message)) {
                    isConnected.set(false);
                    break;
                }
                System.out.print("You: ");
            }

        } catch (IOException e) {
            System.err.println("[Client] Error connecting to server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            isConnected.set(false);
            
         
            if (receiveThread != null && receiveThread.isAlive()) {
                try {
                    receiveThread.join(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

   
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            System.out.println("[Client] Disconnected from server");
        }
    }
}
