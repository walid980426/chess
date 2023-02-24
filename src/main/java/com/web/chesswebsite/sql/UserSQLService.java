package com.web.chesswebsite.sql;

import com.web.chesswebsite.model.User;
import com.web.chesswebsite.service.SecurityService;
import com.web.chesswebsite.utils.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserSQLService {
    private static final int vactor = 40000;
    private static final Logger logger = LogManager.getLogger(UserSQLService.class);
    private final JdbcTemplate jdbcTemplate;
    private final SecurityService securityService;

    @Autowired
    public UserSQLService(JdbcTemplate jdbcTemplate, SecurityService securityService) {
        this.jdbcTemplate = jdbcTemplate;
        this.securityService = securityService;
    }


    public User getUserById(UUID id) {
        User user = jdbcTemplate.queryForObject("select * from `user` where id = ?",
                new UserMapper(), id.toString());

        return user;
    }

    public List<User> getUserByIds(List<UUID> ids) {
        if (ids != null && ids.size() > 0) {
            return jdbcTemplate.query("select * from `user` where id::varchar in ( ?)",
                    new UserMapper(),toSqlIds(ids));
        }
        return new ArrayList<User>();
    }

    public String toSqlIds(List<UUID> ids) {

        StringBuilder stringids = new StringBuilder("'" + ids.get(0));
        for (int i = 1; i < ids.size(); i++) {
            stringids.append("','").append(ids.get(i));
        }
        stringids.append("'");
        return stringids.toString();
    }

    public User getUserByEmail(String email) {
        String emailEncrypt;
        try {
            emailEncrypt = securityService.encrypt(email);
        } catch (Exception e) {
            emailEncrypt = email;
        }
        User user = null;
        try {
            user = jdbcTemplate.queryForObject("select * from `user` where email_encrypt = ? limit 1",
                    new UserMapper(), emailEncrypt);
            user.email_encrypt = email;
            user.password_encrypt = securityService.decrypt(user.password_encrypt);
            user.name = securityService.decrypt(user.name);
        } catch (Exception e) {
            logger.warn("don't find user by email :" + email);
        }
        return user;
    }


    public List<User> getAllUsers() {
        return jdbcTemplate.query("select * from `user`",
                new UserMapper()).stream().map(this::decryptUser).collect(Collectors.toList());
    }

    public boolean deleteUser(String email_encrypt) {
        return jdbcTemplate.update("delete from `user` where email_encrypt = ?", email_encrypt) > 0;
    }

    public boolean insertUser(String name, String email, int usertype, String password) {
        String emailEncrypt;
        try {
            emailEncrypt = securityService.encrypt(email);
        } catch (Exception e) {
            emailEncrypt = email;
        }
        String passwordEncrypt;
        try {
            passwordEncrypt = securityService.encrypt(password);
        } catch (Exception e) {
            passwordEncrypt = password;
        }
        String nameEncrypt;
        try {
            nameEncrypt = securityService.encrypt(name);
        } catch (Exception e) {
            nameEncrypt = name;
        }
        return jdbcTemplate.update("insert into `user`( id,name_encrypt, email_encrypt,user_type, password_encrypt) values(?,?,?,?,?)", UUID.randomUUID().toString(), nameEncrypt, emailEncrypt, usertype, passwordEncrypt) > 0;
    }

    public Result updateUser(User user, String email) {
        Result result = new Result();
        String emailEncrypt;
        try {
            emailEncrypt = securityService.encrypt(user.email_encrypt);
        } catch (Exception e) {
            emailEncrypt = user.email_encrypt;
        }
        String nameEncrypt;
        try {
            nameEncrypt = securityService.encrypt(user.name);
        } catch (Exception e) {
            nameEncrypt = user.name;
        }
        String lastEmail = email;
        try {
            lastEmail = securityService.encrypt(email);
        } catch (Exception e) {

        }
        result.put("updated", jdbcTemplate.update("update `user` set name_encrypt = ?, email_encrypt = ? where email_encrypt = ?", nameEncrypt, emailEncrypt, lastEmail) > 0);
        return result;
    }

    private User decryptUser(User user) {
        try {
            user.name = securityService.decrypt(user.name);
        } catch (Exception e) {
            logger.error("can not decrypt user name id : " + user.id);
        }
        try {
            user.email_encrypt = securityService.decrypt(user.email_encrypt);
        } catch (Exception e) {
            logger.error("can not decrypt user email id : " + user.id);
        }
        return user;
    }

    public List<User> getFriends(String email) {
        User user = getUserByEmail(email);
        if (user != null) {
            return jdbcTemplate.query("select  * from `user` where" +
                    " id in (select user1_id from chesskaw.relation_between_users where" +
                    " user2_id = ? and relation_type = 1) or" +
                    " id in (select user2_id from chesskaw.relation_between_users " +
                    "where user1_id = ? and relation_type = 1) ", new UserMapper(), user.id, user.id);

        }
        return new ArrayList<>();
    }

    public List<User> getUserByRank(int rank) {
        return jdbcTemplate.query("select  * from `user` where rank BETWEEN ? AND ? ;"
                , new UserMapper(), rank - 200, rank + 200);
    }

    public List<User> getUsersFromClan(int clanId) {
        return jdbcTemplate.query("select  * from `user` where " +
                "id in (select user_id from chesskaw.user_clan where clan_id = ? AND " +
                "relation_type = 1)", new UserMapper(), clanId);
    }

    public void updateWinerRank(UUID userId) {
        User user = getUserById(userId);
        long newRank = NewRankForWiner(user.rank);
        jdbcTemplate.update("update `user` set rank = ? where id = ?", newRank, userId);
    }

    public void updateLoserRank(UUID userId) {
        User user = getUserById(userId);
        long newRank = NewRankForLoser(user.rank);
        jdbcTemplate.update("update `user` set rank = ? where id = ? ", newRank, userId);
    }

    public long NewRankForWiner(long rank) {
        float increasing = (float) 1 / rank * vactor;
        return (long) (rank + increasing);
    }

    public long NewRankForLoser(long rank) {
        float dicreasing = (float) rank * vactor / 2000000;
        return (long) (rank - dicreasing);
    }

    public static class UserMapper implements RowMapper<User> {
        public UserMapper() {
        }

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.id = UUID.fromString(resultSet.getString("id"));
            user.email_encrypt = resultSet.getString("email_encrypt");
            user.password_encrypt = resultSet.getString("password_encrypt");
            user.name = resultSet.getString("name_encrypt");
            user.rank = resultSet.getLong("rank");
            user.rank = resultSet.getLong("rank");
            user.userType = resultSet.getInt("user_type");
            return user;
        }
    }

}
