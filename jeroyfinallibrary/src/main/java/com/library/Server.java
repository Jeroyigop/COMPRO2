package com.library;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(5000);

            System.out.println("Server started...");

            while(true) {
                Socket socket = serverSocket.accept();

                ClientHandler handler =
                    new ClientHandler(socket);

                new Thread(handler).start();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
