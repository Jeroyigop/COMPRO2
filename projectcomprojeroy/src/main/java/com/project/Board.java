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

        if (grid[row][col] != '-') {
            throw new InvalidMoveException("Cell already occupied.");
        }

        grid[row][col] = symbol;
    }

    public void display() {

        for (char[] row : grid) {

            for (char cell : row) {
                System.out.print(cell + " ");
            }

            System.out.println();
        }
    }
}
