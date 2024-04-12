package src.pieces;

import src.main.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece {
    public int col, row;
    public int xPos, yPos;

    public boolean isWhite;
    public String name;
    public int value;

    public boolean isFirstMove = true;

    BufferedImage sheet;
    {
        try{
            // assign the image to sheet
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("src/res/chess_pieces.png"));
        }
        catch(IOException e){
            // In case the image is not found
            e.printStackTrace();
        }
    }

    // number of pieces in a row of the sprite sheet
    final int num_pieces_row = 6;
    protected int sheetScale = sheet.getWidth() / num_pieces_row;

    Image sprite;
    Board board;

    public Piece(Board board){
        this.board = board;
    }

    public boolean isValidMovement(int col, int row) {
        return true;
    }

    public boolean moveCollidesWithPiece(int col, int row){
        return false;
    }

    public void paint(Graphics2D g2d){
        g2d.drawImage(sprite, xPos, yPos, null);
    }
}
