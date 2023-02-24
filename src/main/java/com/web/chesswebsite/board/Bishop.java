package com.web.chesswebsite.board;

import org.springframework.stereotype.Component;

public class Bishop extends Piece {

    public Bishop() {
        super();
        this.color = 0;
    }

    public Bishop(int color) {
        super();
        this.color = color;
    }

    public Bishop(int color, int x, int y) {
        super();
        this.color = color;
        this.setCoordinates(x, y);
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return "B";
    }

    @Override
    public boolean isMoveLegal(int x, int y, int[][] grid_matrix) {
        int sum = 0;
        if (isInBounds(x, y) && grid_matrix[x][y] == 0) {
            if (x < xCoordinate) {
                if (y < yCoordinate) {
                    for (int i = x + 1; i < xCoordinate; i++) {
                        for (int j = y + 1; j < yCoordinate; j++) {
                            sum += grid_matrix[i][j];
                        }
                    }
                } else {
                    for (int i = x + 1; i < xCoordinate; i++) {
                        for (int j = yCoordinate + 1; j < y; j++) {
                            sum += grid_matrix[i][j];
                        }
                    }
                }
            } else {
                if (y < yCoordinate) {
                    for (int i = xCoordinate + 1; i < x; i++) {
                        for (int j = y + 1; j < yCoordinate; j++) {
                            sum += grid_matrix[i][j];
                        }
                    }
                } else {
                    for (int i = xCoordinate + 1; i < x; i++) {
                        for (int j = yCoordinate + 1; j < y; j++) {
                            sum += grid_matrix[i][j];
                        }
                    }
                }
            }
            return isDiagonal(x, y) && sum == 0;
        }
        return false;
    }

    @Override
    public boolean killPiece(int x, int y, int[][] grid_matrix) {
        int sum = 0;
        if (isInBounds(x, y)) {
            if (x < xCoordinate) {
                if (y < yCoordinate) {
                    for (int i = x + 1; i < xCoordinate; i++) {
                        for (int j = y + 1; j < yCoordinate; j++) {
                            sum += grid_matrix[i][j];
                        }
                    }
                } else {
                    for (int i = x + 1; i < xCoordinate; i++) {
                        for (int j = yCoordinate + 1; j < y; j++) {
                            sum += grid_matrix[i][j];
                        }
                    }
                }
            } else {
                if (y < yCoordinate) {
                    for (int i = xCoordinate + 1; i < x; i++) {
                        for (int j = y + 1; j < yCoordinate; j++) {
                            sum += grid_matrix[i][j];
                        }
                    }
                } else {
                    for (int i = xCoordinate + 1; i < x; i++) {
                        for (int j = yCoordinate + 1; j < y; j++) {
                            sum += grid_matrix[i][j];
                        }
                    }
                }
            }
            return isDiagonal(x, y) && sum == 0;
        }
        return false;
    }

    @Override
    public boolean isHorizontal(int x, int y) {
        return false;
    }

    @Override
    public boolean isDiagonal(int x, int y) {
        return Math.abs(xCoordinate - x) == Math.abs(yCoordinate - y);
    }


    @Override
    public boolean isVertical(int x, int y) {
        return false;
    }

}
