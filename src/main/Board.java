package src.main;

import src.pieces.Piece;
import src.pieces.Knight;
import src.pieces.Queen;
import src.pieces.King;
import src.pieces.Bishop;
import src.pieces.Rook;
import src.pieces.Pawn;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Board extends JPanel {

    // constants
    final int cols = 8;
    final int rows = 8;

    ArrayList<Piece> pieceList = new ArrayList<Piece>();

    Input input = new Input(this);

    public Piece selectedPiece;
    public boolean isWhiteTurn = true;
    
    public CheckScanner checkScanner = new CheckScanner(this);

    public final int tileSize = 100;
    
    final Color dark_tile = new Color(112,102,119);
    final Color light_tile = new Color(204,183,174);
    final Color moveable_tile = new Color(68,180, 57, 190);

    public int enPassantTile = -1;

    public Board() {
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));

        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        //this.setBackground(Color.GRAY);
        this.addPieces();
    }

    public Piece getPiece(int col, int row){
        for (Piece piece : pieceList) {
            if (piece.col == col && piece.row == row) {
                return piece;
            }
        }
        return null;
    }

    public void makeMove(Move move){

        if (move.piece.name.equals("Pawn")){
            this.movePawn(move);
        } else if (move.piece.name.equals("King")){
            moveKing(move);
        } else {
            move.piece.col = move.newcol;
            move.piece.row = move.newrow;
            move.piece.xPos = move.newcol * tileSize;
            move.piece.yPos = move.newrow * tileSize;

            move.piece.isFirstMove = false;

            capture(move.capture);
            switchTurn();
            
        }
        
    }

    private void moveKing(Move move){

        if (Math.abs(move.piece.col - move.newcol) == 2) {
            Piece rook;
            if (move.newcol == 6){
                rook = this.getPiece(7, move.newrow);
                rook.col = 5;
                rook.xPos = 5 * tileSize;
            } else if (move.newcol == 2){
                rook = this.getPiece(0, move.newrow);
                rook.col = 3;
                rook.xPos = 3 * tileSize;
            }
        }
        
        move.piece.col = move.newcol;
        move.piece.row = move.newrow;
        move.piece.xPos = move.newcol * tileSize;
        move.piece.yPos = move.newrow * tileSize;

        move.piece.isFirstMove = false;

        capture(move.capture);
        switchTurn();
    }

    public void movePawn(Move move){

        // en passant
        int dir = (move.piece.isWhite ? -1 : 1);
        if (this.getTileNumber(move.newcol, move.newrow) == this.enPassantTile){
            move.capture = this.getPiece(move.newcol, move.newrow - dir);
        }
        if (Math.abs(move.newrow - move.oldrow) == 2){
            this.enPassantTile = this.getTileNumber(move.newcol, move.newrow - dir);
        } else {
            this.enPassantTile = -1;
        }
        // promotion
        int promotionTile = (move.piece.isWhite ? 0 : 7);
        if (move.newrow == promotionTile){
            // promotion
            promotePawn(move);
        }

        move.piece.col = move.newcol;
        move.piece.row = move.newrow;
        move.piece.xPos = move.newcol * tileSize;
        move.piece.yPos = move.newrow * tileSize;

        move.piece.isFirstMove = false;

        capture(move.capture);
        switchTurn();
    }

    public void promotePawn(Move move){
        pieceList.add(new Queen(this, move.newcol, move.newrow, move.piece.isWhite));
        capture(move.piece);
    }

    public void capture(Piece piece){
        pieceList.remove(piece);
    }

    public void switchTurn(){
        this.isWhiteTurn = !this.isWhiteTurn;
        return;
    }

    public boolean isValidMove(Move move){
        // No capturing own pieces
        if (this.sameTeam(move.piece, move.capture)){
            return false;
        }
        if (!move.piece.isValidMovement(move.newcol, move.newrow)){
            return false;
        }
        if (move.piece.moveCollidesWithPiece(move.newcol, move.newrow)){
            return false;
        }
        if (checkScanner.isKingChecked(move)){
            return false;
        }
        if (move.piece.isWhite != this.isWhiteTurn){
            return false;
        }
        return true;
    }


    public boolean sameTeam(Piece piece1, Piece piece2){
        if (piece1 == null || piece2 == null) {
            return false;
        }
        return piece1.isWhite == piece2.isWhite;
    }

    public int getTileNumber(int col, int row){
        return row * this.rows + col;
    }

    public void addPieces(){
        // black pieces
        pieceList.add(new Rook(this,0,0,false));
        pieceList.add(new Knight(this,1,0,false));
        pieceList.add(new Bishop(this,2,0,false));
        pieceList.add(new Queen(this,3,0,false));
        pieceList.add(new King(this,4,0,false));
        pieceList.add(new Bishop(this,5,0,false));
        pieceList.add(new Knight(this,6,0,false));
        pieceList.add(new Rook(this,7,0,false));
        // pawns
        for (int i = 0; i < cols; i++) {
            pieceList.add(new Pawn(this,i,1,false));
        }


        // white pieces
        pieceList.add(new Rook(this,0,7,true));
        pieceList.add(new Knight(this,1,7,true));
        pieceList.add(new Bishop(this,2,7,true));
        pieceList.add(new Queen(this,3,7,true));
        pieceList.add(new King(this,4,7,true));
        pieceList.add(new Bishop(this,5,7,true));
        pieceList.add(new Knight(this,6,7,true));
        pieceList.add(new Rook(this,7,7,true));
        // pawns
        for (int i = 0; i < cols; i++) {
            pieceList.add(new Pawn(this,i,6,true));
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if ((r + c) % 2 == 0) {
                    g2d.setColor(light_tile);
                } else {
                    g2d.setColor(dark_tile);
                }
                //(x, y, width, height)
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize); 
            }
        }

        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }

        if (selectedPiece != null){
            for (int r = 0; r < rows; r++){
                for (int c = 0; c < cols; c++){
                    if (this.isValidMove(new Move(this, selectedPiece, c, r))){
                        g2d.setColor(moveable_tile);

                        // fillRect(int x, int y, int width, int height)
                        // g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
                        //fillOval(int x, int y, int width, int height)
                        g2d.fillOval(c * tileSize + tileSize / 2 - 10, r * tileSize + tileSize / 2 - 10, 20, 20);
                    }
                }
            }
        }
        

        
    }

    public Piece findKing(boolean isWhite){
        for (Piece piece : pieceList) {
            if (piece.name.equals("King") && piece.isWhite == isWhite) {
                return piece;
            }
        }
        return null;
    }
}