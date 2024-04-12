package src.pieces;

import src.main.Board;

import java.awt.image.BufferedImage;

public class Rook extends Piece{
    public Rook(Board board, int col, int row, boolean isWhite){
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.isWhite = isWhite;
        this.name = "Rook";

        this.sprite = sheet.getSubimage(4*sheetScale, (isWhite ? 0 : sheetScale), sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row){
        return (col == this.col || row == this.row);
    }

    public boolean moveCollidesWithPiece(int col, int row){
        /* Integer.compare returns -1 if the first number is smaller, 
        0 if they are equal, and 1 if the first number is larger */
        int xDir = Integer.compare(col, this.col);
        int yDir = Integer.compare(row, this.row);

        int x = this.col + xDir;
        int y = this.row + yDir;

        while (x != col || y != row){
            if (board.getPiece(x, y) != null){
                return true;
            }

            x += xDir;
            y += yDir;
        }

        return false;
    }
}
