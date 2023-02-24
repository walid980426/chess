package com.web.chesswebsite.model;


import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name = "clan")
public class ClanModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long id;
    @Column(name = "rank_id")
    public int rank_id;
    @Column(name = "name")
    public String name;

    public ClanModel() {
    }

    public ClanModel(long id, int rank_id, String name) {
        this.id = id;
        this.rank_id = rank_id;
        this.name = name;
    }
}
