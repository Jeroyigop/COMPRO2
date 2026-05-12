package com.project;

public abstract class GameMove {

    protected int row;
    protected int col;
    protected String playerId;

    public GameMove(int row, int col, String playerId) {
        this.row = row;
        this.col = col;
        this.playerId = playerId;
    }

    public abstract String execute();

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getPlayerId() {
        return playerId;
    }
}
