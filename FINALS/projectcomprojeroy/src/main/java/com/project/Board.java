package com.project;

public class Board {

    private final char[][] grid;

    public Board(int size) {
        grid = new char[size][size];

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                grid[r][c] = '-';
            }
        }
    }

    public synchronized void applyMove(int row, int col, char symbol)
            throws InvalidMoveException {

        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
            throw new InvalidMoveException("Move out of bounds.");
        }

        if (grid[row][col] != '-') {
            throw new InvalidMoveException("Cell already occupied.");
        }

        grid[row][col] = symbol;
    }

    public int getSize() {
        return grid.length;
    }

    public synchronized String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("  1 2 3").append(System.lineSeparator());
        for (int r = 0; r < grid.length; r++) {
            sb.append((char) ('A' + r)).append(' ');
            for (int c = 0; c < grid[r].length; c++) {
                sb.append(grid[r][c]);
                if (c < grid[r].length - 1) {
                    sb.append(' ');
                }
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public synchronized char checkWinner() {
        int size = grid.length;

        for (int i = 0; i < size; i++) {
            if (grid[i][0] != '-' && grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]) {
                return grid[i][0];
            }
            if (grid[0][i] != '-' && grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i]) {
                return grid[0][i];
            }
        }

        if (grid[0][0] != '-' && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) {
            return grid[0][0];
        }

        if (grid[0][2] != '-' && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0]) {
            return grid[0][2];
        }

        return '-';
    }

    public synchronized boolean isFull() {
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public void display() {
        System.out.print(render());
    }
}
