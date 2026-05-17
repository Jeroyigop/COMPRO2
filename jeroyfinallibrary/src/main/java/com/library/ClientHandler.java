package com.library;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            BufferedReader reader =
                new BufferedReader(
                    new InputStreamReader(
                        socket.getInputStream()));

            PrintWriter writer =
                new PrintWriter(
                    socket.getOutputStream(), true);

            writer.println("Connected to POS Server");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
