package org.example;

public class Piece {

    private boolean isKing;
    private final char playerChar;

    public Piece(char playerChar) {
        this.isKing = false;
        this.playerChar = playerChar;
    }

    public boolean isKing() {
        return isKing;
    }

    public void promoteToKing() {
        this.isKing = true;
    }

    public char getPlayerChar() {
        return playerChar;
    }

    @Override
    public String toString() {
        return isKing ? playerChar + "K": playerChar+"";
    }
}
