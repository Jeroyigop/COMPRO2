package com.corales.Rps;

import java.net.*;
import java.util.*;

public class RPSServer {

    public static Map<String, User> users = new HashMap<>();
    public static List<ClientHandler> waitingPlayers = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server running...");

        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler client = new ClientHandler(socket);
            client.start();
        }
    }

  
    public static synchronized void addToQueue(ClientHandler player) {
        waitingPlayers.add(player);

        if (waitingPlayers.size() >= 2) {
            ClientHandler p1 = waitingPlayers.remove(0);
            ClientHandler p2 = waitingPlayers.remove(0);

            p1.setOpponent(p2);
            p2.setOpponent(p1);

            p1.send("Match found! Enter move:");
            p2.send("Match found! Enter move:");
        }
    }
}