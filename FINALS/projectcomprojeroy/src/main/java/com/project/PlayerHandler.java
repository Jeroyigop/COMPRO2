package com.project;
import util.JsonUtil;
import java.io.*;
import java.net.Socket;

public class PlayerHandler implements Runnable {

    private Socket socket;
    private TicTacToeRoom room;
    private char symbol;
    private UserDatabase database;
    private String username;

    private BufferedReader in;
    private PrintWriter out;

    public PlayerHandler(Socket socket, TicTacToeRoom room, char symbol, UserDatabase database)
            throws Exception {

        this.socket = socket;
        this.room = room;
        this.symbol = symbol;
        this.database = database;

        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {

        try {
            authenticate();
            room.setPlayerReady(symbol);
            room.waitForBothPlayers();

            send("Welcome! You are player " + symbol + ".");
            send("Board size: " + room.getBoard().getSize() + "x" + room.getBoard().getSize());
            send(room.getBoard().render());
            send("Enter move as: row col (1-based) or coordinate like A1.");

            while (!room.isSessionClosed()) {
                String json = in.readLine();
                if (json == null) {
                    room.stopSession();
                    break;
                }

                Message msg = JsonUtil.fromJson(json, Message.class);
                if (msg == null || msg.getType() == null) {
                    send("Invalid message format.");
                    continue;
                }

                if ("MOVE".equalsIgnoreCase(msg.getType())) {
                    if (room.isGameOver()) {
                        send("Game is over. Please answer the replay prompt.");
                        continue;
                    }
                    if (!room.isPlayerTurn(symbol)) {
                        send("Not your turn. Wait for the opponent.");
                        continue;
                    }

                    try {
                        int[] move = parseMove(msg.getContent());
                        room.getBoard().applyMove(move[0], move[1], symbol);
                        room.broadcast("Player " + symbol + " moved to " + (move[0] + 1) + "," + (move[1] + 1));
                        room.broadcastBoard();

                        char winner = room.getBoard().checkWinner();
                        if (winner != '-') {
                            room.endGame("Player " + winner + " wins!", winner);
                            continue;
                        }

                        if (room.getBoard().isFull()) {
                            room.endGame("Draw! The board is full.", '-');
                            continue;
                        }

                        room.switchTurn();
                        room.broadcast("Next turn: " + (room.isPlayerTurn('X') ? 'X' : 'O'));
                    } catch (InvalidMoveException e) {
                        send("Invalid move: " + e.getMessage());
                    }
                    continue;
                }

                if ("REPLAY".equalsIgnoreCase(msg.getType())) {
                    room.submitReplayVote(symbol, msg.getContent());
                    continue;
                }

                if ("PLAYERS".equalsIgnoreCase(msg.getType())) {
                    room.sendPlayerList(symbol);
                    continue;
                }

                send("Unknown command type: " + msg.getType());
            }
        } catch (Exception e) {
            System.out.println("Connection lost.");
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }

    private void authenticate() throws Exception {
        while (true) {
            send("=== LOGIN OR REGISTER ===");
            send("Send message type LOGIN or REGISTER with content username:password");
            send("Example: LOGIN alice:secret");

            String json = in.readLine();
            if (json == null) {
                throw new IOException("Connection closed before login.");
            }

            Message msg = JsonUtil.fromJson(json, Message.class);
            if (msg == null || msg.getType() == null || msg.getContent() == null) {
                send("ERROR: Invalid message format.");
                continue;
            }

            String[] credentials = parseCredentials(msg.getContent());
            if (credentials == null) {
                send("ERROR: Use username:password format.");
                continue;
            }

            String requestedName = credentials[0];
            String requestedPassword = credentials[1];

            if ("REGISTER".equalsIgnoreCase(msg.getType())) {
                if (database.registerUser(requestedName, requestedPassword)) {
                    username = requestedName;
                    send("REGISTER_SUCCESS");
                    send("Registration successful. Welcome, " + username + "!");
                    UserAccount account = database.getAccount(username);
                    if (account != null) {
                        send("Your stats: " + account.getStats());
                    }
                    break;
                } else {
                    send("ERROR: Username already exists.");
                }
            } else if ("LOGIN".equalsIgnoreCase(msg.getType())) {
                UserAccount account = database.authenticate(requestedName, requestedPassword);
                if (account != null) {
                    username = requestedName;
                    send("LOGIN_SUCCESS");
                    send("Login successful. Welcome back, " + username + "!");
                    send("Your stats: " + account.getStats());
                    break;
                } else {
                    send("ERROR: Invalid login credentials.");
                }
            } else {
                send("ERROR: Unknown command type: " + msg.getType());
            }
        }
    }

    private String[] parseCredentials(String content) {
        if (content == null) {
            return null;
        }
        String trimmed = content.trim();
        int delimiter = trimmed.indexOf(":");
        if (delimiter <= 0 || delimiter == trimmed.length() - 1) {
            return null;
        }
        String user = trimmed.substring(0, delimiter).trim();
        String password = trimmed.substring(delimiter + 1).trim();
        if (user.isEmpty() || password.isEmpty()) {
            return null;
        }
        return new String[] {user, password};
    }

    private int[] parseMove(String content) throws InvalidMoveException {
        if (content == null || content.trim().isEmpty()) {
            throw new InvalidMoveException("Move cannot be empty.");
        }

        String trimmed = content.trim().toUpperCase();

        if (trimmed.matches("\\d+\\s+\\d+")) {
            String[] parts = trimmed.split("\\s+");
            int row = Integer.parseInt(parts[0]) - 1;
            int col = Integer.parseInt(parts[1]) - 1;
            return new int[] {row, col};
        }

        if (trimmed.matches("[A-Z]\\d+")) {
            int row = trimmed.charAt(0) - 'A';
            int col = Integer.parseInt(trimmed.substring(1)) - 1;
            return new int[] {row, col};
        }

        throw new InvalidMoveException("Use row col (like 1 1) or A1 style coordinates.");
    }

    public void send(String message) {
        out.println(message);
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }

    public String getUsername() {
        return username;
    }
}
