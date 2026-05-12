package com.project;

public class AttackMove extends GameMove {

    public AttackMove(int row, int col, String playerId) {
        super(row, col, playerId);
    }

    @Override
    public String execute() {
        return playerId + " attacked (" + row + "," + col + ")";
    }
}
