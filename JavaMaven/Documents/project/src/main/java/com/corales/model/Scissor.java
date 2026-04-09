

package com.corales.model;

public class Scissor extends GameMove {

    @Override
    public String getName() {
        return "Scissor";
    }

    @Override
    public int compare(GameMove other) {
        return other.compareWithScissor(this);
    }

    @Override
    protected int compareWithRock(Rock rock) {
        return -1;
    }

    @Override
    protected int compareWithPaper(Paper paper) {
        return 1;
    }

    @Override
    protected int compareWithScissor(Scissor scissor) {
        return 0;
    }
}