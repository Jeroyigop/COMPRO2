package com.project;

import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

    public static final int PORT = 5000;

    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server started...");

            Socket player1 = serverSocket.accept();
            System.out.println("Player 1 connected.");

            Socket player2 = serverSocket.accept();
            System.out.println("Player 2 connected.");

            GameRoom room = new GameRoom(player1, player2);
            room.startGame();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
