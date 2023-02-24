package com.web.chesswebsite.board;

public class Knight extends Piece {

    public Knight() {
        super();
        this.color = 0;
    }

    public Knight(int color) {
        super();
        this.color = color;
    }

    public Knight(int color, int x, int y) {
        super();
        this.color = color;
        this.setCoordinates(x, y);
    }

    @Override
    public String getType() {
        return "N";
    }

    @Override
    public boolean isMoveLegal(int x, int y, int[][] grid_matrix) {
        return isInBounds(x, y) && grid_matrix[x][y] == 0 && ((Math.abs(x - xCoordinate) == 2) && (Math.abs(y - yCoordinate) == 1) || (Math.abs(y - yCoordinate) == 2 && Math.abs(x - xCoordinate) == 1));
    }

    @Override
    public boolean killPiece(int x, int y, int[][] grid_matrix) {
        return isInBounds(x, y) && grid_matrix[x][y] == 0 && ((Math.abs(x - xCoordinate) == 2) && (Math.abs(y - yCoordinate) == 1) || (Math.abs(y - yCoordinate) == 2 && Math.abs(x - xCoordinate) == 1));
    }

    @Override
    public boolean isVertical(int x, int y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isHorizontal(int x, int y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDiagonal(int x, int y) {
        // TODO Auto-generated method stub
        return false;
    }

}
