package com.web.chesswebsite.model;

public class PieceModel {
    public int id;
    public int movementId;

    public PieceModel(int id, int movementId) {
        this.id = id;
        this.movementId = movementId;
    }

    public PieceModel() {
    }

    public static PieceModel toPieceModel(String piece) {
        String[] idAndMovementId = piece.split("\\s");
        return new PieceModel(Integer.parseInt(idAndMovementId[0]),Integer.parseInt(idAndMovementId[1]));
    }

    @Override
    public String toString() {
        return id + " " + movementId;
    }
}
