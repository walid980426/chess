package com.web.chesswebsite.board;


public class King extends Piece {
    public boolean HasMovedOnce = false;

    public King() {
        super();
        this.color = 0;
    }

    public King(int color) {
        super();
        this.color = color;
    }

    public King(int color, int x, int y) {
        super();
        this.color = color;
        this.setCoordinates(x, y);
    }

    public String getType() {
        return "K";
    }

    @Override
    public boolean isMoveLegal(int x, int y, int[][] grid_matrix) {

        return isInBounds(x, y) && grid_matrix[x][y] == 0 && ((isVertical(x, y) || isHorizontal(x, y) || isDiagonal(x, y)));
    }

    @Override
    public boolean killPiece(int x, int y, int[][] grid_matrix) {

        return isInBounds(x, y) && grid_matrix[x][y] == 0 && ((isVertical(x, y) || isHorizontal(x, y) || isDiagonal(x, y)));
    }

    @Override
    public boolean isDiagonal(int x, int y) {
        if (Math.abs(xCoordinate - x) == Math.abs(yCoordinate - y)) {
            return Math.abs(xCoordinate - x) == 1 && Math.abs(yCoordinate - y) == 1;
        }
        return false;
    }


    @Override
    public boolean isVertical(int x, int y) {
        return xCoordinate == x && Math.abs(yCoordinate - y) == 1;
    }

    @Override
    public boolean isHorizontal(int x, int y) {
        return yCoordinate == y && Math.abs(xCoordinate - x) == 1;
    }


}

