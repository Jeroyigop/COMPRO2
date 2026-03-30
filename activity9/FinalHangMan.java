package activity9;

import java.util.*;
import java.io.*;

public class FinalHangMan {

    public static Scanner xScanner = new Scanner(System.in);

    static class User {
        String username;
        String password;
        int score;

        User(String u, String p, int s) {
            username = u;
            password = p;
            score = s;
        }
    }

    public static void main(String[] args) {

        String[] words = loadWordsFromFile("words.txt");
        System.out.println("Words loaded: " + words.length);

        List<User> users = loadUsers("users.json");

        User currentUser = null;

        while (currentUser == null) {
            currentUser = loginOrRegister(users);
        }

        int score = playgame(words, 6, 10);

        currentUser.score += score;

        saveUsers("users.json", users);

        System.out.println("Total Score: " + currentUser.score);
    }


    public static String[] loadWordsFromFile(String filename) {
        List<String> wordList = new ArrayList<>();

        try {
            InputStream is = FinalHangMan.class.getResourceAsStream("/" + filename);

            if (is == null) {
                System.out.println("words.txt not found in resources. Using defaults.");
                return getDefaultWords();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    wordList.add(line.trim().toLowerCase());
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error reading words file.");
        }

        if (wordList.isEmpty()) {
            System.out.println("words.txt is empty. Using defaults.");
            return getDefaultWords();
        }

        return wordList.toArray(new String[0]);
    }


    public static String[] getDefaultWords() {
        return new String[] {
                "java", "computer", "hangman", "keyboard", "programming"
        };
    }


    public static List<User> loadUsers(String filename) {
        List<User> users = new ArrayList<>();

        try {
            File file = new File(filename);
            if (!file.exists()) return users;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.contains("username")) {
                    String username = line.split(":")[1].replace("\"","").replace(",","");

                    String passwordLine = reader.readLine().trim();
                    String password = passwordLine.split(":")[1].replace("\"","").replace(",","");

                    String scoreLine = reader.readLine().trim();
                    int score = Integer.parseInt(scoreLine.split(":")[1].replace(",",""));

                    users.add(new User(username, password, score));
                }
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading users file.");
        }

        return users;
    }

    public static void saveUsers(String filename, List<User> users) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            writer.write("[\n");

            for (int i = 0; i < users.size(); i++) {
                User u = users.get(i);

                writer.write("  {\"username\":\"" + u.username +
                        "\",\"password\":\"" + u.password +
                        "\",\"score\":" + u.score + "}");

                if (i < users.size() - 1) writer.write(",");
                writer.write("\n");
            }

            writer.write("]");
            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving users.");
        }
    }


    public static User loginOrRegister(List<User> users) {
        System.out.print("\n1. Sign In\n2. Sign Up\nChoose: ");

        int choice;
        try {
            choice = Integer.parseInt(xScanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input.");
            return null;
        }

        if (choice == 1) {
            System.out.print("Username: ");
            String u = xScanner.nextLine();

            System.out.print("Password: ");
            String p = xScanner.nextLine();

            for (User user : users) {
                if (user.username.equals(u) && user.password.equals(p)) {
                    System.out.println("Welcome back, " + u);
                    return user;
                }
            }

            System.out.println("Invalid login.");
            return null;

        } else {
            System.out.print("New username: ");
            String u = xScanner.nextLine();

            for (User user : users) {
                if (user.username.equals(u)) {
                    System.out.println("Username already exists.");
                    return null;
                }
            }

            System.out.print("New password: ");
            String p = xScanner.nextLine();

            User newUser = new User(u, p, 0);
            users.add(newUser);

            System.out.println("Account created!");
            return newUser;
        }
    }


    public static int playgame(String[] words, int MaxIncorrect, int xMaxScore) {

        String word = randomwords(words);
        String HiddenWord = InitializeHiddenWord(word);

        char[] GuessedLetters = new char[26];
        int GuessIndex = 0;

        int normalTriesLeft = MaxIncorrect;
        int bonusTriesLeft = 0;
        int score = 0;
        boolean bonusActive = false;

        while (!fullyguessed(HiddenWord)) {

            System.out.println("\nWord: " + HiddenWord);
            System.out.println("Score: " + score);

            if (!bonusActive) {
                System.out.println("Tries left: " + normalTriesLeft);
            } else {
                System.out.println("Bonus tries left: " + bonusTriesLeft);
            }

            char guess = getletterguess();

            if (alreadyinguessed(guess, GuessedLetters)) {
                System.out.println("Already guessed!");
                continue;
            }

            if (GuessIndex < GuessedLetters.length) {
                GuessedLetters[GuessIndex++] = guess;
            }

            if (IsGuessCorrect(word, guess)) {
                HiddenWord = UpdateHiddenWord(word, HiddenWord, guess);
                score = Math.min(score + 1, xMaxScore);
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong!");
                score = Math.max(score - 1, 0);

                if (!bonusActive) {
                    normalTriesLeft--;
                    if (normalTriesLeft == 0) {
                        bonusActive = true;
                        bonusTriesLeft = 5;
                        System.out.println("\nNormal tries over! BONUS TRIES!");
                    }
                } else {
                    bonusTriesLeft--;
                    if (bonusTriesLeft == 0) break;
                }
            }
        }

        if (!fullyguessed(HiddenWord)) {
            System.out.println("\nGAME OVER!");
            System.out.println("The word was: " + word);
        } else {
            System.out.println("\nYOU WIN!");
        }

        System.out.println("Final Score: " + score);
        return score;
    }


    public static String randomwords(String[] words) {
        return words[(int)(Math.random() * words.length)];
    }

    public static String InitializeHiddenWord(String word) {
        StringBuilder hidden = new StringBuilder();
        for (int i = 0; i < word.length(); i++) hidden.append("*");
        return hidden.toString();
    }

    public static char getletterguess() {
        while (true) {
            System.out.print("Guess a letter: ");
            String input = xScanner.nextLine().trim().toLowerCase();

            if (input.isEmpty() || !Character.isLetter(input.charAt(0))) {
                System.out.println("Enter a valid letter.");
                continue;
            }

            return input.charAt(0);
        }
    }

    public static boolean alreadyinguessed(char g, char[] guessed) {
        for (char c : guessed) if (c == g) return true;
        return false;
    }

    public static boolean IsGuessCorrect(String word, char g) {
        return word.indexOf(g) >= 0;
    }

    public static String UpdateHiddenWord(String word, String hidden, char g) {
        char[] h = hidden.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == g) h[i] = g;
        }
        return new String(h);
    }

    public static boolean fullyguessed(String hidden) {
        return !hidden.contains("*");
    }
}