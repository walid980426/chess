package com.web.chesswebsite.controllerservice;

import com.web.chesswebsite.enums.Status;
import com.web.chesswebsite.model.User;
import com.web.chesswebsite.sql.SessionSqlService;
import com.web.chesswebsite.sql.UserSQLService;
import com.web.chesswebsite.utils.JwtUtils;
import com.web.chesswebsite.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginControllerService {
    private final UserSQLService sqlUser;
    private final JwtUtils jwtUtils;
    private final SessionSqlService sessionSqlService;

    @Autowired
    public LoginControllerService(UserSQLService sqlUser, JwtUtils jwtUtils, SessionSqlService sessionSqlService) {
        this.sqlUser = sqlUser;
        this.jwtUtils = jwtUtils;
        this.sessionSqlService = sessionSqlService;
    }

    public Result login(String email, String password) {
        Result result = new Result();
        User user = sqlUser.getUserByEmail(email);
        if (user == null) {
            result.error("user doesn't exist");
            return result;
        } else if (!user.password_encrypt.equals(password)) {
            result.error("wrong password");
            return result;
        }
        String token = jwtUtils.generateJwt(user);
        sessionSqlService.insert(token, user.id);
        result.put("name", user.name);
        result.put("email", user.email_encrypt);
        result.put("rank", user.rank);
        result.put("userType", user.userType);
        result.put("token", token);
        return result;
    }

    public Result signup(String email, String password, int usertype, String username) {
        Result result = new Result();
        if (sqlUser.getUserByEmail(email) == null) {
            sqlUser.insertUser(username, email, usertype, password);
            result.status(Status.OK);
        } else {
            result.error("user already exists");
        }
        return result;
    }

    public Result logout(String email) {
        Result result = new Result();
        result.put("deleted",
                sessionSqlService.deleteSession(sqlUser.getUserByEmail(email).id));
        return result;
    }

    public String getAll() {
        return sqlUser.getAllUsers().toString();
    }
}
