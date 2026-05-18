package com.project;
import java.net.Socket;

public class GameRoom {

    private ClientHandler player1;
    private ClientHandler player2;

    private Board board;

    private int turn = 1;

    public GameRoom(Socket p1, Socket p2) throws Exception {

        board = new Board(5);

        player1 = new ClientHandler(p1, this, 'X');
        player2 = new ClientHandler(p2, this, 'O');
    }

    public void startGame() {

        new Thread(player1).start();
        new Thread(player2).start();
    }

    public synchronized boolean isPlayerTurn(char symbol) {

        return (turn == 1 && symbol == 'X') ||
               (turn == 2 && symbol == 'O');
    }

    public synchronized void switchTurn() {

        turn = (turn == 1) ? 2 : 1;
    }

    public Board getBoard() {
        return board;
    }
}
