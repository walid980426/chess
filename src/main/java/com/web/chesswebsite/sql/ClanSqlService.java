package com.web.chesswebsite.sql;

import com.web.chesswebsite.model.ClanModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class ClanSqlService {
    private final
    JdbcTemplate jdbcTemplate;

    @Autowired
    public ClanSqlService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public boolean insert(UUID ownerId,String clanName){
        boolean clanCreate =  jdbcTemplate.update("insert into `clan` (name,rank_id) VALUES (?,1)",clanName) == 1;
        long clanId = jdbcTemplate.queryForObject("select * from `clan` where name = ? limit 1 ",new ClanMapper(),clanName).id;
        return  jdbcTemplate.update("insert into `clan` (user_id,clan_id,relation_type)VALUES (?,?,0)",ownerId,clanId) == 1&& clanCreate;
    }
    public List<ClanModel> search(String name) {
        return jdbcTemplate.query("select * from chesskaw.clan where name like ?", new ClanMapper(), "%" + name + "%");
    }

    public boolean addUserToClan(UUID userId, long clanId) {
        if (findUserClan(userId) != -1) {
            return jdbcTemplate.update("insert  into `clan` (clan_id,user_id,relation_type) VALUES (?,?, 0)", clanId, userId) == 1;
        }
        return false;
    }
    public List<UUID>getClanUsersIds(long id){
        return jdbcTemplate.query("select  user_id from `clan` where clan_id = ? and relation_type =1", (rs, rowNum) -> UUID.fromString(rs.getString("user_id")), id);
    }
    public List<UUID>getRequestClanUsersIds(long id){
        return jdbcTemplate.query("select  user_id from `clan` where clan_id = ? and relation_type =-1", (rs, rowNum) -> UUID.fromString(rs.getString("user_id")), id);
    }
    public Long findUserClan(UUID userId) {
        try {
            return jdbcTemplate.queryForObject("select  clan_id from `clan` where user_id = ? and relation_type = 0", (rs, rowNum) -> rs.getLong("clan_id"), userId);
        } catch (Exception e) {
            return null;
        }
    }
    public void accept(UUID userId,long clanId){
        jdbcTemplate.update("update `clan` set relation_type = 1 where  clan_id = ? and user_id = ?",clanId,userId);
    }
    public void reject(UUID userId,long clanId){
        jdbcTemplate.update("delete from `clan` where  clan_id = ? and user_id = ?",clanId,userId);
    }
    public static class ClanMapper implements RowMapper<ClanModel> {
        public ClanMapper() {
        }

        @Override
        public ClanModel mapRow(ResultSet resultSet, int i) throws SQLException {
            ClanModel clanModel = new ClanModel();
            clanModel.id = resultSet.getLong("id");
            clanModel.rank_id = resultSet.getInt("rank_id");
            clanModel.name = resultSet.getString("name");
            return clanModel;
        }
    }
}
