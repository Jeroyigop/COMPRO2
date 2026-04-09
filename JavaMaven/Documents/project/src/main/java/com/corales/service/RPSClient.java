package com.corales.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.corales.model.Message;
import com.corales.model.Player;
import com.google.gson.Gson;

public class RPSClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthService authService = new AuthService();

        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Choose: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        Player player = null;

        if (choice == 1) {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (authService.register(username, password)) {
                System.out.println("Registered successfully!");
                player = authService.login(username, password);
            } else {
                System.out.println("Username already exists.");
                return;
            }

        } else if (choice == 2) {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            player = authService.login(username, password);

            if (player == null) {
                System.out.println("Invalid credentials.");
                return;
            }
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.println("Login successful! Welcome " + player.getUsername());

        // Now connect to server
        try {
            Socket socket = new Socket("localhost", 5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Gson gson = new Gson();

            // Send login message
            Message loginMsg = new Message("LOGIN");
            loginMsg.username = player.getUsername();
            loginMsg.password = player.getPassword();
            out.println(gson.toJson(loginMsg));

            // Wait for login result
            Message response = gson.fromJson(in.readLine(), Message.class);
            if (!response.text.equals("SUCCESS")) {
                System.out.println("Login failed on server.");
                socket.close();
                return;
            }
            System.out.println("Connected to server. Waiting for game to start...");
            while (true) {
                Message msg = gson.fromJson(in.readLine(), Message.class);
                if (msg.type.equals("ROUND")) {
                    System.out.println(msg.text);
                    System.out.println();
                    System.out.println("""
                        |========Rock Paper Scissor Game========|
                                    DESCRIPTION
                                Enter '0' for 'ROCK'
                                Enter '1' for 'PAPER'
                                Enter '2' for 'SCISSOR'

                        |==============GOODLUCK=================|
                            """);
                    System.out.println();
                    System.out.print("Enter move: ");
                    int move;
                    do {
                        move = scanner.nextInt();
                        if (move < 0 || move > 2) {
                            System.out.print("Invalid choice. Please choose again (0, 1, 2): ");
                        }
                    } while (move < 0 || move > 2);
                    Message moveMsg = new Message("[MOVE]");
                    moveMsg.move = move;
                    out.println(gson.toJson(moveMsg));

                } else if (msg.type.equals("RESULT")) {
                    System.out.println("Result: " + msg.text);

                } else if (msg.type.equals("LEADERBOARD")) {
                    System.out.println("Leaderboard:\n" + msg.text);

                } else if (msg.type.equals("FINAL")) {
                    System.out.println("Final Score: " + msg.score1 + " - " + msg.score2);

                } else if (msg.type.equals("END")) {
                    System.out.println(msg.text);
                    break;
                }
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}