package com.project;
import util.JsonUtil;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 5000);
                Scanner scanner = new Scanner(System.in)) {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            while (true) {

                System.out.print("Enter move: ");

                String move = scanner.nextLine();

                Message msg = new Message("MOVE", move);

                out.println(JsonUtil.toJson(msg));

                System.out.println(in.readLine());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}