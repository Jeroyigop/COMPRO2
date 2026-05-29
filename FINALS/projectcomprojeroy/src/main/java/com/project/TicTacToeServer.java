package com.project;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicTacToeServer {

    public static final int PORT = 5000;

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();
        Socket waitingPlayer = null;

        try {
            UserDatabase database = new UserDatabase("users.json");

            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Server started...");

                while (true) {
                    Socket playerSocket = serverSocket.accept();
                    System.out.println("New player connected: " + playerSocket.getRemoteSocketAddress());

                    if (waitingPlayer == null) {
                        waitingPlayer = playerSocket;
                        System.out.println("Waiting for a second player...");
                    } else {
                        TicTacToeRoom room = new TicTacToeRoom(waitingPlayer, playerSocket, database);
                        executor.submit(() -> room.startGame(executor));
                        waitingPlayer = null;
                        System.out.println("Matched two players into a new game room.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
