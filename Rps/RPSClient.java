package com.corales.Rps;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class RPSClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5000);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner sc = new Scanner(System.in);

        new Thread(() -> {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println(msg);
                }
            } catch (Exception e) {}
        }).start();

        while (true) {
            System.out.println("\n1.Register\n2.Login\n3.Play\n4.Leaderboard");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Username: ");
                    String ru = sc.nextLine();
                    System.out.print("Password: ");
                    String rp = sc.nextLine();
                    out.println("REGISTER " + ru + " " + rp);
                    break;

                case 2:
                    System.out.print("Username: ");
                    String lu = sc.nextLine();
                    System.out.print("Password: ");
                    String lp = sc.nextLine();
                    out.println("LOGIN " + lu + " " + lp);
                    break;

                case 3:
                    out.println("PLAY");
                    System.out.print("Enter move (rock/paper/scissors): ");
                    String move = sc.nextLine();
                    out.println("MOVE " + move);
                    break;

                case 4:
                    out.println("LEADERBOARD");
                    break;
            }
        }
    }
}