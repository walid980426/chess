package com.web.chesswebsite.model;



import java.sql.Date;
import java.util.UUID;

public class MovementModel {

    public long id;
    public UUID tableId;
    public String piece;
    public String fen;

    public MovementModel(UUID tableId, String piece, String fen, boolean whitePlayNext) {
        this.tableId = tableId;
        this.piece = piece;
        this.fen = fen;
        this.whitePlayNext = whitePlayNext;
    }
    public MovementModel(){

    }
    public boolean whitePlayNext;




}
