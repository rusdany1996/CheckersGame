package org.example;

public class Player {
    private final char symbol;
    private final int id;
    private int piecesCount;

    public Player (char symbol, int id) {
        this.symbol = symbol;
        this.id = id;
        piecesCount = 12;
    }

    public char getSymbol() {
        return symbol;
    }

    public void pieceCaptured() {
        piecesCount--;
    }

    public int getPiecesCount() {
        return piecesCount;
    }

    @Override
    public String toString() {
        return "Player" + id;
    }
}
