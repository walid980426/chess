package com.web.chesswebsite.board;

public class Queen extends Piece {

    public Queen() {
        super();
        this.color = 0;
    }

    public Queen(int color) {
        super();
        this.color = color;
    }

    public Queen(int color, int x, int y) {
        super();
        this.color = color;
        this.setCoordinates(x, y);
    }

    public String getType() {
        return "Q";
    }


    @Override
    public boolean isMoveLegal(int x, int y, int[][] grid_matrix) {
        if(isInBounds(x, y) && grid_matrix[x][y]==0){
            Rook rook = new Rook(color, xCoordinate, yCoordinate);
            Bishop bishop = new Bishop(color, xCoordinate, yCoordinate);
            return bishop.isMoveLegal(x, y, grid_matrix) || rook.isMoveLegal(x, y, grid_matrix);
        }
        return false;
    }
    @Override
    public boolean killPiece(int x, int y, int[][] grid_matrix) {
        if(isInBounds(x, y) && grid_matrix[x][y]==1){
            Rook rook = new Rook(color, xCoordinate, yCoordinate);
            Bishop bishop = new Bishop(color, xCoordinate, yCoordinate);
            return bishop.isMoveLegal(x, y, grid_matrix) || rook.isMoveLegal(x, y, grid_matrix);
        }
        return false;
    }

    @Override
    public boolean isDiagonal(int x, int y) {
        return Math.abs(xCoordinate - x) == Math.abs(yCoordinate - y);
    }


    @Override
    public boolean isHorizontal(int x, int y) {
        return y == yCoordinate;
    }

    @Override
    public boolean isVertical(int x, int y) {
        return x == xCoordinate;
    }
}
