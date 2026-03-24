package activity9;

import java.io.*;
import java.util.*;
import com.google.gson.*;

public class T {

    static Scanner scanner = new Scanner(System.in);
    static List<String> words = new ArrayList<>();
    static String jsonFile = "users.json";
    static Map<String, User> users = new HashMap<>();
    static User currentUser;

    public static void main(String[] args) throws Exception {
        loadWords("RandomWords.txt");
        loadUsers();
        System.out.println("Welcome to VIP Hangman!");
        loginOrRegister();
        int score = playGame(6, 10);
        currentUser.score = Math.max(currentUser.score, score);
        saveUsers();
        System.out.println("Final Score: " + score);
        displayLeaderboard();
    }

    static class User {
        String username;
        String password;
        int score;
        User(String u, String p, int s){ username=u; password=p; score=s; }
    }

    public static void loadWords(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while((line = br.readLine()) != null){
            if(!line.trim().isEmpty()) words.add(line.trim());
        }
        br.close();
    }

    public static void loadUsers() throws Exception {
        File f = new File(jsonFile);
        if(!f.exists()){
            f.createNewFile();
            try (FileWriter fw = new FileWriter(f)) { fw.write("{\"users\":[]}"); }
        }
        Reader reader = new FileReader(jsonFile);
        JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
        JsonArray arr = obj.getAsJsonArray("users");
        for(JsonElement el : arr){
            JsonObject u = el.getAsJsonObject();
            users.put(u.get("username").getAsString(),
                      new User(u.get("username").getAsString(),
                               u.get("password").getAsString(),
                               u.get("score").getAsInt()));
        }
        reader.close();
    }

    public static void saveUsers() throws Exception {
        JsonArray arr = new JsonArray();
        for(User u : users.values()){
            JsonObject o = new JsonObject();
            o.addProperty("username", u.username);
            o.addProperty("password", u.password);
            o.addProperty("score", u.score);
            arr.add(o);
        }
        JsonObject obj = new JsonObject();
        obj.add("users", arr);
        try(FileWriter fw = new FileWriter(jsonFile)){
            fw.write(new GsonBuilder().setPrettyPrinting().create().toJson(obj));
        }
    }

    public static void loginOrRegister(){
        while(true){
            System.out.print("Do you have an account? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if(choice.equals("y")){
                System.out.print("Username: ");
                String u = scanner.nextLine().trim();
                System.out.print("Password: ");
                String p = scanner.nextLine().trim();
                if(users.containsKey(u) && users.get(u).password.equals(p)){
                    currentUser = users.get(u);
                    System.out.println("Welcome back, "+u+"!");
                    break;
                } else {
                    System.out.println("Invalid credentials!");
                }
            } else if(choice.equals("n")){
                System.out.print("Choose username: ");
                String u = scanner.nextLine().trim();
                System.out.print("Choose password: ");
                String p = scanner.nextLine().trim();
                if(users.containsKey(u)){
                    System.out.println("Username already exists!");
                } else {
                    currentUser = new User(u,p,0);
                    users.put(u, currentUser);
                    System.out.println("Account created!");
                    break;
                }
            }
        }
    }

    public static int playGame(int MaxIncorrect, int MaxScore){
        String word = words.get(new Random().nextInt(words.size()));
        String hidden = "*".repeat(word.length());
        char[] guessed = new char[26];
        int guessIndex=0, tries=MaxIncorrect, score=0;

        while(hidden.contains("*") && (tries>0)){
            System.out.println("\nWord: "+hidden+" | Score: "+score+" | Tries left: "+tries);
            System.out.print("Guess a letter: ");
            char g = scanner.nextLine().trim().toLowerCase().charAt(0);

            boolean already = false;
            for(char c : guessed) if(c==g) already=true;
            if(already){ System.out.println("Already guessed!"); continue; }
            guessed[guessIndex++] = g;

            if(word.indexOf(g)>=0){
                char[] h = hidden.toCharArray();
                for(int i=0;i<word.length();i++) if(word.charAt(i)==g) h[i]=g;
                hidden = new String(h);
                score = Math.min(score+1, MaxScore);
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong!");
                score = Math.max(score-1,0);
                tries--;
            }
        }

        if(hidden.contains("*")) System.out.println("\nGAME OVER! The word was: "+word);
        else System.out.println("\nYOU WIN! Word: "+word);

        return score;
    }

    public static void displayLeaderboard(){
        List<User> list = new ArrayList<>(users.values());
        list.sort((a,b)->b.score-a.score);
        System.out.println("\n=== LEADERBOARD ===");
        for(int i=0;i<list.size();i++){
            System.out.println((i+1)+". "+list.get(i).username+" - "+list.get(i).score);
        }
    }
}