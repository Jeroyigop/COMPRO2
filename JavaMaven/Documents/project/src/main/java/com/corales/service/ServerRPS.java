package com.corales.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.corales.model.Message;
import com.corales.model.Player;
import com.google.gson.Gson;

public class ServerRPS {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Waiting for 2 players...");

        Socket p1Socket = serverSocket.accept();
        System.out.println("Player 1 connected");

        Socket p2Socket = serverSocket.accept();
        System.out.println("Player 2 connected");

        BufferedReader in1 = new BufferedReader(new InputStreamReader(p1Socket.getInputStream()));
        PrintWriter out1 = new PrintWriter(p1Socket.getOutputStream(), true);

        BufferedReader in2 = new BufferedReader(new InputStreamReader(p2Socket.getInputStream()));
        PrintWriter out2 = new PrintWriter(p2Socket.getOutputStream(), true);

        Gson gson = new Gson();
        AuthService authService = new AuthService();
        FileService fileService = new FileService();


        Player p1 = authenticatePlayer(in1, out1, gson, authService);
        Player p2 = authenticatePlayer(in2, out2, gson, authService);

        System.out.println(p1.getUsername() + " vs " + p2.getUsername());


        for (int round = 1; round <= 10; round++) {

            Message roundMsg = new Message("ROUND");
            roundMsg.text = "ROUND " + round;

            out1.println(gson.toJson(roundMsg));
            out2.println(gson.toJson(roundMsg));

            Message m1 = gson.fromJson(in1.readLine(), Message.class);
            Message m2 = gson.fromJson(in2.readLine(), Message.class);

            int result = GameService.getWinner(m1.move, m2.move);

            Message r1 = new Message("RESULT");
            Message r2 = new Message("RESULT");

            if (result == 1) {
                p1.setScore(p1.getScore() + 1);
                p2.setLosses(p2.getLosses() + 1);
                r1.text = "WIN";
                r2.text = "LOSE";
            } else if (result == 2) {
                p2.setScore(p2.getScore() + 1);
                p1.setLosses(p1.getLosses() + 1);
                r1.text = "LOSE";
                r2.text = "WIN";
            } else {
                r1.text = "DRAW";
                r2.text = "DRAW";
            }

            out1.println(gson.toJson(r1));
            out2.println(gson.toJson(r2));

            System.out.println();
            Message lb = new Message("LEADERBOARD");
            lb.text = p1.getUsername() + ": " + p1.getScore() + " wins, " + p1.getLosses() + " losses\n" +
            p2.getUsername() + ": " + p2.getScore() + " wins, " + p2.getLosses() + " losses";
            out1.println(gson.toJson(lb));
            out2.println(gson.toJson(lb));
            System.out.println();
        }


        Message f1 = new Message("FINAL");
        f1.score1 = p1.getScore();
        f1.score2 = p2.getScore();

        Message f2 = new Message("FINAL");
        f2.score1 = p2.getScore();
        f2.score2 = p1.getScore();

        out1.println(gson.toJson(f1));
        out2.println(gson.toJson(f2));


        String winner;
        if (p1.getScore() > p2.getScore()) {
            winner = p1.getUsername();
            p1.addWin();
            p2.addLoss();
        } else if (p2.getScore() > p1.getScore()) {
            winner = p2.getUsername();
            p2.addWin();
            p1.addLoss();
        } else {
            winner = "DRAW";
        }

        Message endMsg = new Message("END");
        endMsg.text = winner + " WINS THE MATCH";

        out1.println(gson.toJson(endMsg));
        out2.println(gson.toJson(endMsg));

        // Save results and update player stats
        fileService.saveResult(p1, p2, winner);
        fileService.updateUser(p1);
        fileService.updateUser(p2);

        p1Socket.close();
        p2Socket.close();
        serverSocket.close();
    }

    private static Player authenticatePlayer(BufferedReader in, PrintWriter out, Gson gson, AuthService authService) throws Exception {

        while (true) {
            Message loginMsg = gson.fromJson(in.readLine(), Message.class);

            if (loginMsg.type.equals("LOGIN")) {
                Player player = authService.login(loginMsg.username, loginMsg.password);

                Message response = new Message("LOGIN_RESULT");

                if (player != null) {
                    response.text = "SUCCESS";
                    out.println(gson.toJson(response));
                    return player;
                } else {
                    response.text = "FAIL";
                    out.println(gson.toJson(response));
                }
            }
        }
    }
}