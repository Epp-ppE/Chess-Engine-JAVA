package src.main;

import src.pieces.Piece;

public class Move {
    
    int oldcol;
    int oldrow;
    int newcol;
    int newrow;

    Piece piece;
    Piece capture;

    public Move(Board board, Piece piece, int newcol, int newrow){
        this.piece = piece;
        this.oldcol = piece.col;
        this.oldrow = piece.row;
        this.newcol = newcol;
        this.newrow = newrow;

        this.capture = board.getPiece(newcol, newrow);
    }
}
