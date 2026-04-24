package com.corales.model;


public abstract class GameMove {

    public abstract String getName();

    public abstract int compare(GameMove other);

    protected abstract int compareWithRock(Rock rock);
    protected abstract int compareWithPaper(Paper paper);
    protected abstract int compareWithScissor(Scissor scissor);
}