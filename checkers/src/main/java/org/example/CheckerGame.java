package org.example;

import org.example.utils.Constants;

import java.util.Scanner;

public class CheckerGame {

    private final Player player1;
    private final Player player2;
    private final Board board;
    private Player currentPlayer;
    private Piece multipleJumpPiece;
    private String multipleJumpPiecePos;
    private Piece capturedPiece;

    public CheckerGame() {
        player1 = new Player( 'W',1);
        player2 = new Player( 'B',2);
        currentPlayer = player1;
        multipleJumpPiece = null;
        capturedPiece = null;
        multipleJumpPiecePos = null;
        board = new Board();
    }

    public int columnToIndex(char col) {
        return col - 'a';
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }

    public Piece getPiece(int x, int y){
        return board.getPiece(x,y);
    }

    public void setPiece(int x, int y, Piece piece){
        board.setPiece(x,y,piece);
    }

    public boolean kingMoves(int fromRow, int fromCol, int toRow, int toCol, int rowDiff, int colDiff) {
        if (rowDiff == 1 && colDiff == 1) {
            return true;
        }
        if (rowDiff == 2 && colDiff == 2) {
            int midRow = (fromRow + toRow) / 2;
            int midCol = (fromCol + toCol) / 2;
            return currentPlayer == player1 ? logicPerPlayer(player2,midRow,midCol) : logicPerPlayer(player1,midRow,midCol);
        }
        return false;
    }

    public boolean logicPerPlayer(Player oppositePlayer, int midRow, int midCol) {
        if (board.getPiece(midRow, midCol) != null && board.getPiece(midRow, midCol).getPlayerChar() == oppositePlayer.getSymbol()){
            oppositePlayer.pieceCaptured();
            capturedPiece = board.getPiece(midRow,midCol);
            board.setPiece(midRow, midCol, null ) ;
            return true;
        } else {
            return false;
        }
    }

    public boolean regularMoves(int fromRow, int fromCol, int toRow, int toCol, int rowDiff, int colDiff) {
        if (currentPlayer == player1 && fromRow > toRow || currentPlayer == player2 && fromRow < toRow){
            return false;
        }
        if (rowDiff == 1 && colDiff == 1) {
            return true;
        }
        if (rowDiff == 2 && colDiff == 2) {
            int midRow = (fromRow + toRow) / 2;
            int midCol = (fromCol + toCol) / 2;
            return currentPlayer == player1 ? logicPerPlayer(player2,midRow,midCol) : logicPerPlayer(player1,midRow,midCol);
        }
        return false;
    }

    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);

        if (toRow < 0 || toRow >= Constants.BOARD_SIZE || toCol < 0 || toCol >= Constants.BOARD_SIZE)
            return false;

        if (board.getPiece(fromRow, fromCol) == null || board.getPiece(toRow, toCol) != null)
            return false;

        if (board.getPiece(fromRow,fromCol).isKing()) {
            return kingMoves(fromRow,fromCol, toRow, toCol, rowDiff, colDiff);
        } else {
            return regularMoves(fromRow,fromCol, toRow, toCol, rowDiff, colDiff);
        }
    }

    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        if (isValidMove(fromRow, fromCol, toRow, toCol)) {
            Piece piece = board.getPiece(fromRow, fromCol);
            board.setPiece(toRow, toCol, piece) ;
            board.setPiece(fromRow, fromCol, null);

            if (currentPlayer == player1 && toRow == Constants.BOARD_SIZE - 1) {
                piece.promoteToKing();
            }
            if (currentPlayer == player2 && toRow == 0) {
                piece.promoteToKing();
            }
            return true;
        }
        return false;
    }

    public boolean additionalCheck(int toRow, int fromRow){
        if (currentPlayer == player1 && fromRow > toRow)
            return false;
        if (currentPlayer == player2 && fromRow < toRow)
            return false;
        return true;
    }

    public boolean hasMultipleJumps(int fromRow, int fromCol) {
        int[] directions = {-1, 1};
        for (int rowDirection : directions) {
            for (int colDirection : directions) {
                int midRow = fromRow + rowDirection;
                int midCol = fromCol + colDirection;
                int toRow = fromRow + 2 * rowDirection;
                int toCol = fromCol + 2 * colDirection;

                if (toRow >= 0 && toRow < Constants.BOARD_SIZE && toCol >= 0 && toCol < Constants.BOARD_SIZE) {
                    if (board.getPiece(midRow, midCol) != null && board.getPiece(toRow,toCol) == null
                            && board.getPiece(midRow, midCol).getPlayerChar() == (currentPlayer == player1 ? 'B' : 'W') && additionalCheck(midRow, fromRow)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (player2.getPiecesCount() > 0 && player1.getPiecesCount() > 0) {
            board.printBoard();
            System.out.println(currentPlayer + "'s turn.");
            System.out.print("Select piece: ");
            String from = scanner.nextLine();
            System.out.print("Next position: ");
            String to = scanner.nextLine();

            int fromRow = Integer.parseInt(from.substring(1)) - 1;
            int fromCol = columnToIndex(from.charAt(0));
            int toRow = Integer.parseInt(to.substring(1)) - 1;
            int toCol = columnToIndex(to.charAt(0));

            if (multipleJumpPiece != null && multipleJumpPiece != board.getPiece(fromRow,fromCol)){
                System.out.println("Only " + multipleJumpPiecePos +" can be moved");
                continue;
            }

            if (movePiece(fromRow, fromCol, toRow, toCol)) {
                System.out.println("Move successful!");

                if (hasMultipleJumps(toRow, toCol) && capturedPiece != null) {
                    System.out.println("You can make another jump with this piece.");
                    multipleJumpPiece = board.getPiece(toRow,toCol);
                    multipleJumpPiecePos = to;
                } else {
                    switchPlayer();
                    multipleJumpPiece = null;
                    multipleJumpPiecePos = null;
                    capturedPiece = null;
                }
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }
}
