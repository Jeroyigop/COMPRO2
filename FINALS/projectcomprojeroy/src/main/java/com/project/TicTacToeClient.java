package com.project;
import util.JsonUtil;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class TicTacToeClient {

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 5000);
                Scanner scanner = new Scanner(System.in)) {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            AtomicBoolean awaitingReplay = new AtomicBoolean(false);

            boolean authenticated = false;
            while (!authenticated) {
                System.out.println("=== MENU ===");
                System.out.println("1) Login");
                System.out.println("2) Register");
                System.out.print("Select option: ");
                if (!scanner.hasNextLine()) {
                    return;
                }

                String option = scanner.nextLine().trim();
                String type;
                if ("1".equals(option)) {
                    type = "LOGIN";
                } else if ("2".equals(option)) {
                    type = "REGISTER";
                } else {
                    System.out.println("Please enter 1 or 2.");
                    continue;
                }

                System.out.print("Username: ");
                String username = scanner.nextLine().trim();
                System.out.print("Password: ");
                String password = scanner.nextLine().trim();

                Message msg = new Message(type, username + ":" + password);
                out.println(JsonUtil.toJson(msg));

                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if ("LOGIN_SUCCESS".equals(response) || "REGISTER_SUCCESS".equals(response)) {
                        authenticated = true;
                        break;
                    }
                    if (response.startsWith("ERROR:") || response.contains("Send message type")) {
                        break;
                    }
                }
            }

            Thread readerThread = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        if (response.equals("REPLAY_PROMPT") || response.contains("Play again? yes/no")) {
                            awaitingReplay.set(true);
                        }
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });
            readerThread.setDaemon(true);
            readerThread.start();

            while (true) {
                if (awaitingReplay.get()) {
                    System.out.print("Enter yes or no: ");
                } else {
                    System.out.print("Enter move (row col or A1) or PLAYERS: ");
                }
                if (!scanner.hasNextLine()) {
                    break;
                }

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    continue;
                }

                if (awaitingReplay.get()) {
                    Message msg = new Message("REPLAY", input);
                    out.println(JsonUtil.toJson(msg));
                    awaitingReplay.set(false);
                } else {
                    if ("PLAYERS".equalsIgnoreCase(input)) {
                        Message msg = new Message("PLAYERS", "");
                        out.println(JsonUtil.toJson(msg));
                        continue;
                    }
                    Message msg = new Message("MOVE", input);
                    out.println(JsonUtil.toJson(msg));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}