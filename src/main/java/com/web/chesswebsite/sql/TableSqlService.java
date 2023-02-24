package com.web.chesswebsite.sql;

import com.web.chesswebsite.model.TableModel;
import com.web.chesswebsite.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


@Service
public class TableSqlService {
    final
    JdbcTemplate jdbcTemplate;

    @Autowired
    public TableSqlService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public int addUserToTableOrCreateOne(User user, UUID tableId) {

        TableModel table = getTableWithoOnePlayer();
        if (table == null) {
            jdbcTemplate.update("insert into `table` (id,user1_id,status) values " +
                    " (?,?,0)", tableId.toString(), user.id.toString());
            return 0;
        }
        jdbcTemplate.update("update `table` set " +
                "status = 1 , user2_id = ? where id = ?", user.id.toString(), table.id.toString());
        return 1;
    }

    public TableModel getTableWithoOnePlayer() {
        try {
            return jdbcTemplate.queryForObject("select * from `table` where " +
                    "status = 0  limit 1", new TableMapper());
        } catch (Exception e) {
            return null;
        }
    }

    public TableModel getTableByPlayer(UUID userId) {
        try {
            return jdbcTemplate.queryForObject("select * from `table` where " +
                    "(user1_id = ? or user2_id = ?) and status != 2", new TableMapper(), userId.toString(), userId.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public void updateState(UUID tableId, int status) {
        jdbcTemplate.update("update `table` set " +
                "status = ?  where id = ?", status, tableId);
    }

    public class TableMapper implements RowMapper<TableModel> {
        @Override
        public TableModel mapRow(ResultSet resultSet, int i) throws SQLException {
            TableModel table = new TableModel();
            table.id = UUID.fromString(resultSet.getString("id"));
            table.user1Id = UUID.fromString(resultSet.getString("user1_id"));
            String user2Id = resultSet.getString("user2_id");
            if (user2Id != null) {
                table.user2Id = UUID.fromString(user2Id);
            }
            table.status = resultSet.getInt("status");
            table.startTime = resultSet.getDate("start_time");
            return table;
        }
    }

}
