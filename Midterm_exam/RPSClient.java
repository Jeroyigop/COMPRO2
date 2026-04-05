import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RPSClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5000);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));


        System.out.print("Enter your name: ");
        out.println(userInput.readLine());

        String serverMsg;
        while ((serverMsg = in.readLine()) != null) {
                System.out.println();
            if (serverMsg.startsWith("[[[[[[[ROUND")) {
                System.out.println(serverMsg);
                System.out.println();
                System.out.println("=====[GAME MENU]=====");
                System.out.println("[Rock Paper Scissor]");
                System.out.println();
                System.out.println("[0] = Rock");
                System.out.println("[1] = Paper");
                System.out.println("[2] = Scissors");
                System.out.println();
                System.out.println("====[INSTRUCTION]====");
                System.out.println("{ Type [0] if ROCK }");
                System.out.println("{ Type [1] if PAPER }");
                System.out.println("{ Type [2] if SCISSOR }");
                System.out.println();
                System.out.println("[BEST OF LUCK]");
                System.out.println();
                System.out.print("[ENTER YOUR CHOICE]: ");
                String choice = userInput.readLine();
                out.println(choice);

            } else if (serverMsg.startsWith("[WIN]")) {
                System.out.println("[YOU WIN THIS ROUND]!");

            } else if (serverMsg.startsWith("[LOSE]")) {
                System.out.println("[YOU LOSE THIS ROUND]!");

            } else if (serverMsg.startsWith("[DRAW]")) {
                System.out.println("[DRAW]!");

            } else if (serverMsg.startsWith("[FINAL]")) {
                String[] parts = serverMsg.split(" ");
                System.out.println("Final Score: " + parts[1] + " - " + parts[2]);

            } else {
                System.out.println(serverMsg);
            }
        }

        socket.close();
    }
}