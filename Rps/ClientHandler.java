package com.corales.Rps;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private User user;
    private ClientHandler opponent;
    private String move;

    public ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        try {
            while (true) {
                String request = in.readLine();
                handleRequest(request);
            }
        } catch (Exception e) {
            System.out.println("Client disconnected");
        }
    }

    private void handleRequest(String req) {
        try {
            String[] parts = req.split(" ");
            String cmd = parts[0];

            switch (cmd) {

                case "REGISTER":
                    register(parts[1], parts[2]);
                    break;

                case "LOGIN":
                    login(parts[1], parts[2]);
                    break;

                case "PLAY":
                    RPSServer.addToQueue(this);
                    break;

                case "MOVE":
                    move = parts[1];
                    checkGame();
                    break;

                case "LEADERBOARD":
                    sendLeaderboard();
                    break;
            }
        } catch (Exception e) {
            send("Error processing request");
        }
    }

    private void register(String u, String p) {
        if (RPSServer.users.containsKey(u)) {
            send("Username exists");
        } else {
            RPSServer.users.put(u, new User(u, p));
            send("Registered successfully");
        }
    }

    private void login(String u, String p) {
        User user = RPSServer.users.get(u);
        if (user != null && user.password.equals(p)) {
            this.user = user;
            send("Login successful");
        } else {
            send("Invalid credentials");
        }
    }

    public void setOpponent(ClientHandler opp) {
        this.opponent = opp;
    }

    private void checkGame() {
        if (opponent.move != null && this.move != null) {
            String result = getWinner(this.move, opponent.move);

            if (result.equals("draw")) {
                send("Draw!");
                opponent.send("Draw!");
            } else if (result.equals("win")) {
                this.user.score++;
                send("You Win!");
                opponent.send("You Lose!");
            } else {
                opponent.user.score++;
                send("You Lose!");
                opponent.send("You Win!");
            }

            this.move = null;
            opponent.move = null;
        }
    }

    private String getWinner(String a, String b) {
        if (a.equals(b)) return "draw";

        if ((a.equals("rock") && b.equals("scissors")) ||
            (a.equals("paper") && b.equals("rock")) ||
            (a.equals("scissors") && b.equals("paper"))) {
            return "win";
        }
        return "lose";
    }

    private void sendLeaderboard() {
        StringBuilder sb = new StringBuilder("Leaderboard:\n");
        for (User u : RPSServer.users.values()) {
            sb.append(u.username).append(" - ").append(u.score).append("\n");
        }
        send(sb.toString());
    }

    public void send(String msg) {
        out.println(msg);
    }
}