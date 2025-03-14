import org.example.CheckerGame;
import org.example.Piece;
import org.example.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckerGameTest {
    private CheckerGame game;

    @BeforeEach
    void setUp() {
        game = new CheckerGame();
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            for (int j = 0; j < Constants.BOARD_SIZE; j++) {
                game.setPiece(i, j,  null);
            }
        }
    }

    @Test
    void testValidRegularMoveForPlayer1() {

        Piece piece = new Piece('W');
        game.setPiece(0,0,piece);

        int fromRow = 0;
        int fromCol = 0;
        int toRow = 1;
        int toCol = 1;

        boolean moveResult = game.movePiece(fromRow, fromCol, toRow, toCol);

        assertTrue(moveResult, "Move should be valid.");
    }

    @Test
    void testValidRegularMoveForPlayer2() {

        Piece piece = new Piece('B');
        game.setPiece(7,0,piece);
        game.switchPlayer();

        int fromRow = 7;
        int fromCol = 0;
        int toRow = 6;
        int toCol = 1;

        boolean moveResult = game.movePiece(fromRow, fromCol, toRow, toCol);

        assertTrue(moveResult, "Move should be valid.");
    }

    @Test
    void testInvalidMoveToOccupiedSpaceP1() {

        Piece piece = new Piece('W');
        game.setPiece(0,0,piece);
        Piece piece1 = new Piece('B');
        game.setPiece(1,1,piece1);
        game.switchPlayer();


        boolean moveResult = game.movePiece(0, 0, 1, 1);

        assertFalse(moveResult, "Move should be invalid, the space is already occupied.");
    }

    @Test
    void testInvalidMoveToOccupiedSpaceP2() {

        Piece piece = new Piece('W');
        game.setPiece(0,0,piece);
        Piece piece1 = new Piece('B');
        game.setPiece(1,1,piece1);

        boolean moveResult = game.movePiece(1, 1, 0, 0);

        assertFalse(moveResult, "Move should be invalid, the space is already occupied.");
    }

    @Test
    void testKingPromotion() {
        Piece piece = new Piece('W');
        game.setPiece(6,0,piece);

        int fromRow = 6;
        int fromCol = 0;
        int toRow = 7;
        int toCol = 1;

        game.movePiece(fromRow, fromCol, toRow, toCol);

        Piece promotedPiece = game.getPiece(toRow, toCol);
        assertTrue(promotedPiece.isKing(), "Piece should be promoted to king.");
    }

    @Test
    void testMultipleJumps() {
        Piece piece = new Piece('W');
        game.setPiece(0,0,piece);
        Piece piece1 = new Piece('B');
        game.setPiece(1,1,piece1);
        Piece piece2 = new Piece('B');
        game.setPiece(3,3,piece2);

        game.movePiece(0, 0, 2, 2);
        game.hasMultipleJumps(2,2);
        boolean moveResult = game.movePiece(2, 2, 4, 4);

        assertTrue(moveResult, "Move should be valid for a multiple jump.");
    }

    @Test
    void testInvalidMoveOutOfBounds() {
        int fromRow = 0;
        int fromCol = 0;
        int toRow = 1;
        int toCol = -1;

        boolean moveResult = game.movePiece(fromRow, fromCol, toRow, toCol);

        assertFalse(moveResult, "Move should be invalid, destination is out of bounds.");
    }
}