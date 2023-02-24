package com.web.chesswebsite.sql;

import com.web.chesswebsite.model.SessionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Service
public class SessionSqlService {
    private final
    JdbcTemplate jdbcTemplate;

    @Autowired
    public SessionSqlService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean insert(String token, UUID userId) {
        return jdbcTemplate.update("insert into `session` (user_id,token) values (?,?) ", userId.toString(), token) > 0;
    }

    public SessionModel get(String token) {
        return jdbcTemplate.queryForObject("select * from `session` where token = ?", new SessionMapper(), token.replace("\n", ""));
    }

    public boolean deleteSession(UUID id) {
        return jdbcTemplate.update("delete from `session` where user_id = ?", id) == 1;

    }

    public static class SessionMapper implements RowMapper<SessionModel> {
        public SessionMapper() {
        }

        @Override
        public SessionModel mapRow(ResultSet resultSet, int i) throws SQLException {
            SessionModel sessionModel = new SessionModel();
            sessionModel.id = resultSet.getLong("id");
            sessionModel.userId = UUID.fromString(resultSet.getString("user_id"));
            sessionModel.token = resultSet.getString("token");
            return sessionModel;
        }
    }
}
