package com.project;



import util.JsonUtil;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private GameRoom room;
    private char symbol;

    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket, GameRoom room, char symbol)
            throws Exception {

        this.socket = socket;
        this.room = room;
        this.symbol = symbol;

        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {

        try {

            while(true) {

                String json = in.readLine();

                Message msg =
                        JsonUtil.fromJson(json, Message.class);

                System.out.println(msg.getContent());

                out.println("ACK");
            }

        } catch(Exception e) {

            System.out.println("Connection lost.");
        }
    }
}
