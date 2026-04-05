package activity10;

import java.io.*;
import java.net.*;

public class HangmanServer {
    public static void main(String[] args) throws IOException {
     
        int port = 5000;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Waiting for client to connect...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected: " + clientSocket.getInetAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

       
        System.out.println("Enter a word for the client to guess:");
        String word = console.readLine().toLowerCase();
        char[] wordChars = word.toCharArray();
        char[] display = new char[word.length()];
        for (int i = 0; i < display.length; i++) display[i] = '_';

        int attempts = 6; 
        out.println(display.length);

        while (attempts > 0 && new String(display).contains("_")) {
           
            out.println(new String(display) + "," + attempts);

           
            String guess = in.readLine();
            if (guess == null) break;
            guess = guess.toLowerCase();

            boolean correct = false;
            for (int i = 0; i < wordChars.length; i++) {
                if (wordChars[i] == guess.charAt(0)) {
                    display[i] = wordChars[i];
                    correct = true;
                }
            }

            if (!correct) attempts--;
        }

      
        if (new String(display).equals(word)) {
            out.println("WIN," + word);
        } else {
            out.println("LOSE," + word);
        }

        clientSocket.close();
        serverSocket.close();
    }
}
