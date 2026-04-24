package com.corales.model;

public class GameResult {
    public String player1;
    public String player2;
    public int score1;
    public int score2;
    public String winner;

    public GameResult(String p1, String p2, int s1, int s2, String winner) {
        this.player1 = p1;
        this.player2 = p2;
        this.score1 = s1;
        this.score2 = s2;
        this.winner = winner;
    }
}