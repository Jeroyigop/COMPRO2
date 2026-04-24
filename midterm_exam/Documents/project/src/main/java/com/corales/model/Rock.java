package com.corales.model;

public class Rock extends GameMove {

    @Override
    public String getName() {
        return "Rock";
    }

    @Override
    public int compare(GameMove other) {
        return other.compareWithRock(this);
    }

    @Override
    protected int compareWithRock(Rock rock) {
        return 0;
    }

    @Override
    protected int compareWithPaper(Paper paper) {
        return -1;
    }

    @Override
    protected int compareWithScissor(Scissor scissor) {
        return 1;
    }
}
