package com.web.chesswebsite.model;


import javax.persistence.Table;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "message")
public class MessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long id;
    @Column(name = "from_user_id")
    public UUID fromUserId;
    @Column(name = "to_user_id")
    public UUID toUserId;
    @Column(name = "table_id")
    public UUID tableId;
    @Column(name = "time")
    public Date time;
    @Column(name = "content")
    public String content;

    public MessageModel() {

    }

    public MessageModel(long id, UUID fromUserId, UUID toUserId, UUID tableId, Date time, String content) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.tableId = tableId;
        this.time = time;
        this.content = content;
    }
}
