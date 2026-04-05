import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Player {
    String name;
    int score = 0;

    Player(String name) {
        this.name = name;
    }
}

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

        Player p1 = new Player(in1.readLine());
        Player p2 = new Player(in2.readLine());

        for (int round = 1; round <= 5; round++) {
            out1.println("ROUND " + round);
            out2.println("ROUND " + round);

            int move1 = Integer.parseInt(in1.readLine());
            int move2 = Integer.parseInt(in2.readLine());

            int result = getWinner(move1, move2);

            if (result == 1) {
                p1.score++;
                out1.println("[YOU WIN]!");
                out2.println("[YOU LOSE]!");
            } else if (result == 2) {
                p2.score++;
                out2.println("[YOU WIN]!");
                out1.println("[YOU LOSE]!");
            } else {
                out1.println("[DRAW]");
                out2.println("[DRAW]");
            }
        }

        String finalResult;
        if (p1.score > p2.score) finalResult = p1.name + " {WINS THE MATCH}!";
        else if (p2.score > p1.score) finalResult = p2.name + " {WINS THE MATCH}!";
        else finalResult = "Match is a draw!";

        out1.println("FINAL " + p1.score + " " + p2.score);
        out2.println("FINAL " + p2.score + " " + p1.score);

        out1.println(finalResult);
        out2.println(finalResult);

        saveResult(p1, p2);

        p1Socket.close();
        p2Socket.close();
        serverSocket.close();
    }

    static int getWinner(int m1, int m2) {
        if (m1 == m2) return 0;
        if ((m1 == 0 && m2 == 2) || (m1 == 1 && m2 == 0) || (m1 == 2 && m2 == 1)) return 1;
        return 2;
    }

    static void saveResult(Player p1, Player p2) throws Exception {
        FileWriter fw = new FileWriter("results.txt", true);
        fw.write(p1.name + " vs " + p2.name + " => " + p1.score + ":" + p2.score + "\n");
        fw.close();
    }
}
