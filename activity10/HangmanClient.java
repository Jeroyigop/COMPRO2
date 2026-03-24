package activity10;

import java.io.*;
import java.net.*;

public class HangmanClient {
    public static void main(String[] args) throws IOException {
        String serverIP = "192.168.1.2"; // replace with server's IP
        int port = 5000;

        Socket socket = new Socket(serverIP, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        int wordLength = Integer.parseInt(in.readLine());
        System.out.println("Word has " + wordLength + " letters.");

        while (true) {
            String data = in.readLine();
            if (data == null) break;

            if (data.startsWith("WIN") || data.startsWith("LOSE")) {
                String[] parts = data.split(",");
                if (parts[0].equals("WIN")) System.out.println("You guessed it! Word: " + parts[1]);
                else System.out.println("You lost! Word: " + parts[1]);
                break;
            }

            String[] parts = data.split(",");
            String board = parts[0];
            int attempts = Integer.parseInt(parts[1]);

            System.out.println("Word: " + board + "  Attempts left: " + attempts);
            System.out.print("Guess a letter: ");
            String guess = console.readLine();
            out.println(guess);
        }

        socket.close();
    }
}