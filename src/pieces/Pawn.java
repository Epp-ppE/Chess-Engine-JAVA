package src.pieces;

import src.main.Board;

import java.awt.image.BufferedImage;

public class Pawn extends Piece{
    public Pawn(Board board, int col, int row, boolean isWhite){
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.isWhite = isWhite;
        this.name = "Pawn";

        this.sprite = sheet.getSubimage(5*sheetScale, (isWhite ? 0 : sheetScale), sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row){
        int dir = (isWhite ? -1 : 1);
        // push pawn 1
        if (col == this.col && row == this.row + dir && board.getPiece(col, row) == null){
            return true;
        } 
        // push pawn 2
        if (col == this.col && row == this.row + 2*dir && this.isFirstMove && board.getPiece(col, row) == null){
            return true;
        }
        // capture
        if (Math.abs(col - this.col) == 1 && row == this.row + dir && board.getPiece(col, row) != null){
            return true;
        }
        // en passant left
        if (board.getTileNumber(col, row) == board.enPassantTile && col == this.col - 1 && row == this.row + dir && board.getPiece(col, row - dir) != null){
            return true;
        }
        // en passant right
        if (board.getTileNumber(col, row) == board.enPassantTile && col == this.col + 1 && row == this.row + dir && board.getPiece(col, row - dir) != null){
            return true;
        }
        return false;
    }
}
