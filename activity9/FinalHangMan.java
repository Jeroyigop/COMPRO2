package activity9;

import java.util.Scanner; // Import Scanner class to read user input


public class FinalHangMan { // Main class for the Hangman game


    // Scanner object used throughout the program
    public static Scanner xScanner = new Scanner(System.in);


    public static void main(String[] args) {


        // List of possible words for the game
        String[] words = {
            "noise","eager","sized","lying","plate","teeth","large","topic",
            "stuck","queen","which","joint","crowd","sleep","aware","worst",
            "audio","build","apart","stage","china","small","sixth","front",
            "above","ahead","going","chief","force","earth","found","sight",
            "minus","since","whose","spent","mouse","drama","whose","motor",
            "total","thank","write","world","terry","badly","track","heart",
            "phase","watch"
        };


        // Arrays to store up to 50 player names and scores
        String[] xPlayerNames = new String[50];
        int[] xPlayerScores = new int[50];
        int xPlayerCount = 0; // Keeps track of how many players played


        // Loop allows multiple players to play one after another
        do {
            String xName = getplayername(); // Get player's name
            int xScore = playgame(words, 6, 10); // Play the game


            // Store player name and score
            xPlayerNames[xPlayerCount] = xName;
            xPlayerScores[xPlayerCount] = xScore;
            xPlayerCount++;


        } while (anothergame() && xPlayerCount < 50); // Continue if user says yes


        // Show leaderboard after all players are done
        xDisplayLeaderboard(xPlayerNames, xPlayerScores, xPlayerCount);
    }



    // Asks the user for their name
    public static String getplayername() {
        System.out.print("Enter player name: ");
        return xScanner.nextLine();
    }


    // Asks if another player wants to play
    public static boolean anothergame() {
        System.out.print("Another player? (y/n): ");
        return xScanner.nextLine().toLowerCase().charAt(0) == 'y';
    }


   
    // Main Hangman game logic
    public static int playgame(String[] words, int MaxIncorrect, int xMaxScore) {


        String word = randomwords(words); // Pick a random word
        String HiddenWord = xInitializeHiddenWord(word); // Hide the word with *


        // Array to store letters already guessed
        char[] GuessedLetters = new char[26];
        int GuessIndex = 0;


        int normalTriesLeft = MaxIncorrect; 
        int bonusTriesLeft = 0;             
        int score = 0;                     
        boolean bonusActive = false;       


        // Loop runs until word is guessed or player loses
        while (!fullyguessed(HiddenWord)) {


            System.out.println("\nWord: " + HiddenWord);
            System.out.println("Score: " + score);


            // Show remaining tries
            if (!bonusActive) {
                System.out.println("Tries left: " + normalTriesLeft);
            } else {
                System.out.println("Bonus tries left: " + bonusTriesLeft);
            }


            char guess = getletterguess(); // Get player's letter guess


            // Check if letter was already guessed
            if (alreadyinguessed(guess, GuessedLetters)) {
                System.out.println("Already guessed!");
                continue;
            }


            // Store guessed letter
            xUpdateGuessedLetters(guess, GuessedLetters, GuessIndex++);


            // Check if guess is correct
            if (xIsGuessCorrect(word, guess)) {
                HiddenWord = xUpdateHiddenWord(word, HiddenWord, guess);
                score = Math.min(score + 1, xMaxScore); // Increase score
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong!");
                score = Math.max(score - 1, 0); // Decrease score


                // Handle tries
                if (!bonusActive) {
                    normalTriesLeft--;
                    if (normalTriesLeft == 0) {
                        bonusActive = true;
                        bonusTriesLeft = 5;
                        System.out.println("\nNormal tries over! 5 BONUS TRIES!");
                    }
                } else {
                    bonusTriesLeft--;
                    if (bonusTriesLeft == 0) {
                        break; // End game
                    }
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


    // =====================
    // Selects a random word from the array
    public static String randomwords(String[] words) {
        return words[(int)(Math.random() * words.length)];
    }


    // Creates a hidden version of the word using *
    public static String xInitializeHiddenWord(String word) {
        String hidden = "";
        for (int i = 0; i < word.length(); i++) {
            hidden += "*";
        }
        return hidden;
    }


    // Gets a letter guess from the user
public static char getletterguess() {
    while (true) {
        System.out.print("Guess a letter: ");
        String input = xScanner.nextLine().trim().toLowerCase();


        // If the player entered nothing, ask again
        if (input.isEmpty()) {
            System.out.println("Please enter a letter.");
            continue;
        }


        return input.charAt(0);
    }
}


    // Checks if the letter was already guessed
    public static boolean alreadyinguessed(char g, char[] guessed) {
        for (char c : guessed) {
            if (c == g) return true;
        }
        return false;
    }


    // Stores guessed letter in array
    public static void xUpdateGuessedLetters(char g, char[] guessed, int i) {
        guessed[i] = g;
    }


    // Checks if the guessed letter exists in the word
    public static boolean xIsGuessCorrect(String word, char g) {
        return word.indexOf(g) >= 0;
    }


    // Reveals correct letters in the hidden word
    public static String xUpdateHiddenWord(String word, String hidden, char g) {
        char[] h = hidden.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == g) {
                h[i] = g;
            }
        }
        return new String(h);
    }


    // Checks if the word has been fully guessed
    public static boolean fullyguessed(String hidden) {
        return !hidden.contains("*");
    }


    // ====================
    // Displays leaderboard sorted by highest score
    public static void xDisplayLeaderboard(String[] names, int[] scores, int count) {


        // Sort players by score (descending)
        for (int i = 0; i < count - 1; i++) {
            for (int j = i + 1; j < count; j++) {
                if (scores[j] > scores[i]) {
                    int ts = scores[i];
                    scores[i] = scores[j];
                    scores[j] = ts;


                    String tn = names[i];
                    names[i] = names[j];
                    names[j] = tn;
                }
            }
        }


        // Print leaderboard
        System.out.println("\n=== LEADERBOARD ===");
       for (int i = 0; i < count; i++) {


    // Check if this player is tied with the previous one
    if (i > 0 && scores[i] == scores[i - 1]) {
        System.out.println((i + 1) + ". " + names[i] + " - " + scores[i] + " (TIED)");
    } else {
        System.out.println((i + 1) + ". " + names[i] + " - " + scores[i]);
    }
}
    }
}
