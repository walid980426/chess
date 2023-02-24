package com.web.chesswebsite.board;

public class Pawn extends Piece {
    protected int type;
    protected int direction = color == 0 ? 1 : -1;

    public Pawn() {
        super();
        this.color = 0;
        this.type = 6;
    }

    public Pawn(int color) {
        super();
        this.color = color;
        this.type = 6;
    }

    public Pawn(int color, int x, int y) {
        super();
        this.color = color;
        this.type = 6;
        this.setCoordinates(x, y);
    }

    @Override
    public String getType() {
        return "P";
    }

    @Override
    public boolean isMoveLegal(int x, int y, int[][] grid_matrix) {
        if (isInBounds(x, y) && (isVerticalDoubleStepPossible(x, y))) {
            return true;
        } else return isInBounds(x, y) && grid_matrix[x][y] == 0 && isVertical(x, y);
    }

    @Override
    public boolean killPiece(int x, int y, int[][] grid_matrix) {
        return (this.xCoordinate - x) == 1 && (this.yCoordinate - y) == 1 && grid_matrix[x][y] == 1;
    }

    @Override
    public boolean isVertical(int x, int y) {
        return xCoordinate == x && direction * (yCoordinate - y) == 1;
    }

    public boolean isVerticalDoubleStepPossible(int x, int y) {
        return xCoordinate == x && direction * (yCoordinate - y) > 0 && direction * (yCoordinate - y) <= 2 && !hasMoved();
    }

    @Override
    public boolean isHorizontal(int x, int y) {
        return false;
    }

    @Override
    public boolean isDiagonal(int x, int y) {
        return false;
    }


}
