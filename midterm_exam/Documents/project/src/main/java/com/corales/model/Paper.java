package com.corales.model;


public class Paper extends GameMove {

    @Override
    public String getName() {
        return "Paper";
    }

    @Override
    public int compare(GameMove other) {
        return other.compareWithPaper(this);
    }

    @Override
    protected int compareWithRock(Rock rock) {
        return 1;
    }

    @Override
    protected int compareWithPaper(Paper paper) {
        return 0;
    }

    @Override
    protected int compareWithScissor(Scissor scissor) {
        return -1;
    }
}