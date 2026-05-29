package com.project;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class TicTacToeRoom {

    private PlayerHandler player1;
    private PlayerHandler player2;

    private Board board;
    private UserDatabase database;

    private int turn = 1;
    private boolean gameOver = false;
    private boolean player1Ready = false;
    private boolean player2Ready = false;
    private Boolean player1Replay = null;
    private Boolean player2Replay = null;
    private boolean sessionClosed = false;

    public TicTacToeRoom(Socket p1, Socket p2, UserDatabase database) throws Exception {

        this.database = database;
        board = new Board(3);

        player1 = new PlayerHandler(p1, this, 'X', database);
        player2 = new PlayerHandler(p2, this, 'O', database);
    }

    public void startGame(ExecutorService executor) {
        executor.submit(player1);
        executor.submit(player2);
    }

    public synchronized boolean isPlayerTurn(char symbol) {
        return !gameOver && ((turn == 1 && symbol == 'X') ||
               (turn == 2 && symbol == 'O'));
    }

    public synchronized void switchTurn() {
        turn = (turn == 1) ? 2 : 1;
    }

    public synchronized boolean isGameOver() {
        return gameOver;
    }

    public Board getBoard() {
        return board;
    }

    public synchronized void broadcast(String message) {
        player1.send(message);
        player2.send(message);
        System.out.println(message);
    }

    public synchronized void sendToPlayer(char symbol, String message) {
        if (symbol == 'X') {
            player1.send(message);
        } else {
            player2.send(message);
        }
    }

    public synchronized void sendPlayerList(char requestingSymbol) {
        String player1Name = player1.getUsername();
        String player2Name = player2.getUsername();
        sendToPlayer(requestingSymbol, "Connected players:");
        sendToPlayer(requestingSymbol, "- " + player1Name + " (X)");
        sendToPlayer(requestingSymbol, "- " + player2Name + " (O)");
    }

    public synchronized void broadcastBoard() {
        player1.send("Current board:");
        player1.send(board.render());
        player2.send("Current board:");
        player2.send(board.render());
        System.out.println("Current board:");
        board.display();
    }

    public synchronized void setPlayerReady(char symbol) {
        if (symbol == 'X') {
            player1Ready = true;
        } else {
            player2Ready = true;
        }
        notifyAll();
    }

    public synchronized void waitForBothPlayers() {
        while (!player1Ready || !player2Ready) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
    }

    public synchronized void endGame(String resultMessage, char winner) {
        if (gameOver) {
            return;
        }
        gameOver = true;
        player1Replay = null;
        player2Replay = null;

        broadcast(resultMessage);
        broadcastBoard();
        updateStats(winner);
        broadcast("Play again? yes/no");
        broadcast("REPLAY_PROMPT");
    }

    public synchronized void submitReplayVote(char symbol, String voteContent) {
        if (!gameOver || sessionClosed) {
            return;
        }

        boolean vote = "yes".equalsIgnoreCase(voteContent.trim());
        if (symbol == 'X') {
            player1Replay = vote;
        } else {
            player2Replay = vote;
        }

        broadcast("Player " + symbol + " voted: " + (vote ? "yes" : "no"));

        if (player1Replay != null && player2Replay != null) {
            if (player1Replay && player2Replay) {
                broadcast("Both players agreed to play again. Restarting game.");
                resetGame();
                broadcast("New game starts now.");
                broadcastBoard();
            } else {
                broadcast("One player declined. Ending the session.");
                stopSession();
            }
        }
    }

    private void resetGame() {
        board = new Board(3);
        turn = 1;
        gameOver = false;
        player1Replay = null;
        player2Replay = null;
    }

    public synchronized void stopSession() {
        sessionClosed = true;
        player1.close();
        player2.close();
    }

    public synchronized boolean isSessionClosed() {
        return sessionClosed;
    }

    private void updateStats(char winner) {
        String name1 = player1.getUsername();
        String name2 = player2.getUsername();

        if (winner == 'X') {
            database.recordResult(name1, 'W');
            database.recordResult(name2, 'L');
        } else if (winner == 'O') {
            database.recordResult(name1, 'L');
            database.recordResult(name2, 'W');
        } else {
            database.recordResult(name1, 'T');
            database.recordResult(name2, 'T');
        }
    }
}
