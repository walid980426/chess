package com.web.chesswebsite.sql;

import com.web.chesswebsite.model.MovementModel;
import com.web.chesswebsite.model.PieceModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;


@Service
public class MovementSQLTable {
    final JdbcTemplate jdbcTemplate;

    public MovementSQLTable(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean insert(MovementModel movementModel) {
        return jdbcTemplate.update("insert into `movement` " +
                        "( table_id,fen,white_play_next,piece ) values(?, ?, ?,?) ",
                movementModel.tableId.toString(),
                movementModel.fen,
                movementModel.whitePlayNext,
                movementModel.piece) > 0;
    }

    public List<MovementModel> selectByTableId(UUID id) {
        return jdbcTemplate.query("SELECT * FROM `movement` WHERE table_id = ? ;",
                new MovementMapper(),
                id);
    }

    public MovementModel selectLastMoveByTableId(UUID tableId) {
        List<MovementModel> list = jdbcTemplate.query("SELECT * FROM `movement` WHERE table_id = ? order by time_move desc ;",
                new MovementMapper(),
                tableId.toString());
        return list.isEmpty()?null:list.get(0);
    }

    public List<MovementModel> select() {
        return jdbcTemplate.query("select * from `movement`",
                new MovementMapper());
    }

    public MovementModel selectById(int id) {
        return jdbcTemplate.queryForObject("select * from `movement` WHERE id = ? limit 1",
                new MovementMapper(),
                id);
    }

    public static class PieceMapper implements RowMapper<PieceModel> {
        @Override
        public PieceModel mapRow(ResultSet resultSet, int i) throws SQLException {
            PieceModel piece = new PieceModel();
            piece.id = resultSet.getInt("id");
            piece.movementId = resultSet.getInt("movement_id");
            return piece;
        }
    }


    public static class MovementMapper implements RowMapper<MovementModel> {
        @Override
        public MovementModel mapRow(ResultSet resultSet, int i) throws SQLException {
            MovementModel movementModel = new MovementModel();
            movementModel.tableId = UUID.fromString(resultSet.getString("table_id"));
            movementModel.id = resultSet.getLong("id");
            movementModel.id = resultSet.getLong("id");
            movementModel.fen = resultSet.getString("fen");
            movementModel.whitePlayNext = resultSet.getBoolean("white_play_next");
            return movementModel;
        }
    }
}
