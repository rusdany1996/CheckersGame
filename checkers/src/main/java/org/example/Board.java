package org.example;

import org.example.utils.Constants;

public class Board {

    private final Piece[][] board;

    public Board(){
        board = new Piece[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            for (int j = 0; j < Constants.BOARD_SIZE; j++) {
                board[i][j] = null;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = (i % 2 == 0) ? 0 : 1; j < Constants.BOARD_SIZE; j += 2) {
                board[i][j] = new Piece('W');
            }
        }

        for (int i = 5; i < 8; i++) {
            for (int j = (i % 2 == 0) ? 0 : 1; j < Constants.BOARD_SIZE; j += 2) {
                board[i][j] = new Piece('B');
            }
        }
    }

    public Piece getPiece(int x, int y) {
        return board[x][y];
    }

    public void setPiece(int x, int y, Piece piece) {
        board[x][y] = piece;
    }

    public void printBoard() {
        System.out.println("\n  a b c d e f g h");
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < Constants.BOARD_SIZE; j++) {
                if (board[i][j] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[i][j].toString() + " ");
                }
            }
            System.out.println();
        }
    }
}
