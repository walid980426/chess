package com.web.chesswebsite.board;

public class Rook extends Piece {
    public Rook() {
        super();
        this.color = 0;
    }

    public Rook(int color) {
        super();
        this.color = color;
    }

    public Rook(int color, int x, int y) {
        super();
        this.color = color;
        this.setCoordinates(x, y);
    }

    @Override
    public String getType() {
        return "R";
    }

    @Override
    public boolean isMoveLegal(int x, int y, int[][] grid_matrix) {
        int sum = 0;
        if (isInBounds(x, y) && grid_matrix[x][y] == 0) {
            if (isVertical(x, y)) {
                if (x < this.xCoordinate) {
                    for (int i = x + 1; i < this.xCoordinate - 1; x++) {
                        sum += grid_matrix[i][y];
                    }
                } else {
                    for (int i = xCoordinate + 1; i < x - 1; x++) {
                        sum += grid_matrix[i][y];
                    }
                }
                return sum == 0;
            } else if (isHorizontal(x, y)) {
                if (y < this.yCoordinate) {
                    for (int i = y + 1; i < this.yCoordinate - 1; y++) {
                        sum += grid_matrix[x][i];
                    }
                } else {
                    for (int i = yCoordinate + 1; i < y - 1; y++) {
                        sum += grid_matrix[x][i];
                    }
                }
                return sum == 0;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean killPiece(int x, int y, int[][] grid_matrix) {
        int sum = 0;
        if (isInBounds(x, y) && grid_matrix[x][y] == 1) {
            if (isVertical(x, y)) {
                if (x < this.xCoordinate) {
                    for (int i = x + 1; i < this.xCoordinate - 1; x++) {
                        sum += grid_matrix[i][y];
                    }
                } else {
                    for (int i = xCoordinate + 1; i < x - 1; x++) {
                        sum += grid_matrix[i][y];
                    }
                }
                return sum == 0;
            } else if (isHorizontal(x, y)) {
                if (y < this.yCoordinate) {
                    for (int i = y + 1; i < this.yCoordinate - 1; y++) {
                        sum += grid_matrix[x][i];
                    }
                } else {
                    for (int i = yCoordinate + 1; i < y - 1; y++) {
                        sum += grid_matrix[x][i];
                    }
                }
                return sum == 0;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean isDiagonal(int x, int y) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean isVertical(int x, int y) {
        // TODO Auto-generated method stub
        return x == xCoordinate;
        // TODO Auto-generated method stub
    }

    @Override
    public boolean isHorizontal(int x, int y) {
        // TODO Auto-generated method stub
        return y == yCoordinate;
    }


}
