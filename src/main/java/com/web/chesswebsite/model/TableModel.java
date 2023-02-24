package com.web.chesswebsite.model;

import com.web.chesswebsite.enums.PlayerColor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


public class TableModel {
    public UUID id;
    public UUID user1Id;
    public UUID user2Id;
    public int status;
    public Date startTime;

}
