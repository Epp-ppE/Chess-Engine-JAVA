package src.main;

import src.pieces.Piece;

public class CheckScanner {
    
    Board board;

    public CheckScanner(Board board){
        this.board = board;
    }

    public boolean isKingChecked(Move move){

        Piece king = board.findKing(move.piece.isWhite);
        assert king != null;

        int kingCol = king.col;
        int kingRow = king.row;

        if (board.selectedPiece != null && board.selectedPiece.name.equals("King")){
            kingCol = move.newcol;
            kingRow = move.newrow;
        }
        
        return  hitByRook   (move.newcol, move.newrow, king, kingCol, kingRow, 0, 1) ||
                hitByRook   (move.newcol, move.newrow, king, kingCol, kingRow, 1, 0) ||
                hitByRook   (move.newcol, move.newrow, king, kingCol, kingRow, 0, -1) ||
                hitByRook   (move.newcol, move.newrow, king, kingCol, kingRow, -1, 0) ||

                hitByBishop (move.newcol, move.newrow, king, kingCol, kingRow, -1, 1) ||
                hitByBishop (move.newcol, move.newrow, king, kingCol, kingRow, -1, -1) ||
                hitByBishop (move.newcol, move.newrow, king, kingCol, kingRow, 1, -1) ||
                hitByBishop (move.newcol, move.newrow, king, kingCol, kingRow, 1, 1) ||

                hitByKnight(move.newcol, move.newrow, king, kingCol, kingRow) ||
                hitByPawn(move.newcol, move.newrow, king, kingCol, kingRow) ||

                hitByKing(king, kingCol, kingRow);
                
    }

    private boolean hitByRook(int col, int row, Piece king, int kingCol, int kingRow, int colVal, int rowVal){
        for (int i = 1; i < board.cols; i++){
            // check whether there is an ally piece in the vertical or horizontal path before the rook/queen
            if (kingCol + (i * colVal) == col && kingRow + (i * rowVal) == row){
                // there is an ally piece in the path
                break;
            }
            Piece piece = board.getPiece(kingCol + (i* colVal), kingRow + (i * rowVal));
            if (piece != null && piece != board.selectedPiece){
                if (!board.sameTeam(piece, king) && (piece.name.equals("Rook") || piece.name.equals("Queen"))){
                    return true;
                }
                break;
            }
        }

        return false;
    }

    private boolean hitByKnight(int col, int row, Piece king, int kingCol, int kingRow){
        return  checkKnight(board.getPiece(kingCol - 1, kingRow -2), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 1, kingRow +2), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow -1), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow +1), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow -2), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow +2), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow -1), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow +1), king, col, row);
    }

    private boolean checkKnight(Piece p, Piece k, int col, int row){
        return p != null && !board.sameTeam(p, k) && p.name.equals("Knight") && !(p.col == col && p.row == row);
    }

    private boolean hitByKing(Piece king, int kingCol, int kingRow){
        return  checkKing(board.getPiece(kingCol - 1, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol + 0, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow + 0), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow + 1), king) ||
                checkKing(board.getPiece(kingCol + 0, kingRow + 1), king) ||
                checkKing(board.getPiece(kingCol - 1, kingRow + 1), king) ||
                checkKing(board.getPiece(kingCol - 1, kingRow + 0), king);
    }

    private boolean checkKing(Piece p, Piece k){
        return p != null && !board.sameTeam(p, k) && p.name.equals("King");
    }

    private boolean hitByPawn(int col, int row, Piece king, int kingCol, int kingRow){
        int dir = (king.isWhite ? -1 : 1);
        return  checkPawn(board.getPiece(kingCol - 1, kingRow + dir), king, col, row) ||
                checkPawn(board.getPiece(kingCol + 1, kingRow + dir), king, col, row);
    }

    private boolean checkPawn(Piece p, Piece k, int col, int row){
        
        return p != null && !board.sameTeam(p, k) && p.name.equals("Pawn") && !(p.col == col && p.row == row);
    }

    private boolean hitByBishop(int col, int row, Piece king, int kingCol, int kingRow, int colVal, int rowVal){
        for (int i = 1; i < board.cols; i++){
            if (kingCol + (i * colVal) == col && kingRow + (i * rowVal) == row){
                break;
            }
            Piece piece = board.getPiece(kingCol + (i* colVal), kingRow + (i * rowVal));
            if (piece != null && piece != board.selectedPiece){
                if (!board.sameTeam(piece, king) && (piece.name.equals("Bishop") || piece.name.equals("Queen"))){
                    return true;
                }
                break;
            }
        }

        return false;
    }
    
    public boolean havePossibleMovesLeft(boolean isWhite){
        for (Piece piece : board.pieceList) {
            if (piece.isWhite == isWhite){
                board.selectedPiece = piece;
                if (pieceCanMove(piece)){
                    board.selectedPiece = null;
                    return true;
                }
            }
        }
        board.selectedPiece = null;
        return false;
    }
    
    public boolean pieceCanMove(Piece piece){
        for (int r = 0; r < board.rows; r++){
            for (int c = 0; c < board.cols; c++){
                if (board.isValidMove(new Move(board, piece, c, r))){
                    System.out.println(piece.isWhite + "\'s"+ piece.name + " can move to " + c + " " + r);
                    return true;
                }
            }
        }
        return false;
    }
}
