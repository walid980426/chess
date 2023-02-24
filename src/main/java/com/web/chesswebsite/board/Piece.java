package com.web.chesswebsite.board;

public abstract class Piece {
    public int color; //0 for white, 1 for black
    public int xCoordinate, yCoordinate;
    boolean HasMovedOnce = false;

    public void setCoordinates(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
    }

    public int getColor() {
        return this.color;
    }


    public boolean hasMoved() {
        return HasMovedOnce;
    }

    abstract public boolean isVertical(int x, int y);

    abstract public boolean isHorizontal(int x, int y);

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    abstract public boolean isDiagonal(int x, int y);

    abstract public String getType();

    abstract public boolean isMoveLegal(int x, int y,int[][] grid_matrix);
    abstract public boolean killPiece(int x, int y,int[][] grid_matrix);

    public boolean isVerticallyObstructed(int x, int y, int[][] grid_matrix) {
        if (x == xCoordinate) {
            int step_size = Math.abs(yCoordinate - y);
            int dir = (yCoordinate - y) / Math.abs(yCoordinate - y);
            for (int i = 0; i < step_size; i++) {
                if (dir == 1) {
                    if (grid_matrix[x][yCoordinate + i] == 1) {
                        return true;
                    }
                } else {
                    if (grid_matrix[x][yCoordinate - i] == 1) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public boolean isHorizontallyObstructed(int x, int y, int[][] grid_matrix) {
        if (y == yCoordinate) {
            int step_size = Math.abs(xCoordinate - x);
            int dir = (xCoordinate - x) / Math.abs(xCoordinate - x);
            for (int i = 0; i < step_size; i++) {
                if (dir == 1) {
                    if (grid_matrix[xCoordinate + i][y] == 1) {
                        return true;
                    }
                } else {
                    if (grid_matrix[xCoordinate - i][y] == 1) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public boolean isDiagonallyObstructed(int x, int y, int[][] grid_matrix) {
        if (Math.abs(y - yCoordinate) == Math.abs(x - xCoordinate)) {
            int step_size = Math.abs(yCoordinate - y);
            for (int i = 0; i < step_size; i++) {
                if ((grid_matrix[x + i][y + i] == 1) || (grid_matrix[x - i][y - i] == 1)) //Check this condition again
                {
                    return true;
                }
            }

        }
        return false;
    }


}
