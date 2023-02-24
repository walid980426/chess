package com.web.chesswebsite.controllerservice;

import com.web.chesswebsite.model.User;
import com.web.chesswebsite.service.SecurityService;
import com.web.chesswebsite.sql.ClanSqlService;
import com.web.chesswebsite.sql.SessionSqlService;
import com.web.chesswebsite.sql.UserSQLService;
import com.web.chesswebsite.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClanControllerService {
    private final ClanSqlService clanSqlService;
    private final SessionSqlService sessionSqlService;
    private final UserSQLService userSQLService;
    private final SecurityService securityService;

    @Autowired
    public ClanControllerService(ClanSqlService clanSqlService, SessionSqlService sessionSqlService, UserSQLService userSQLService, SecurityService securityService) {
        this.clanSqlService = clanSqlService;
        this.sessionSqlService = sessionSqlService;
        this.userSQLService = userSQLService;
        this.securityService = securityService;
    }

    public Result search(String name) {
        Result result = new Result();
        result.put("clans", clanSqlService.search(name));
        return result;
    }

    public Result insert(String token, String clanName) {
        Result result = new Result();
        result.put("clanAdded", clanSqlService.insert(sessionSqlService.get(token).userId, clanName));
        return result;
    }

    public Result getClanUsersIds(String token) {
        long clanId = clanSqlService.findUserClan(sessionSqlService.get(token).userId);
        Result result = new Result();
        List<User> users = userSQLService.getUserByIds(clanSqlService.getClanUsersIds(clanId));
        users.forEach(user -> {
            user.name = securityService.decrypt(user.name);
            user.email_encrypt = securityService.decrypt(user.email_encrypt);
        });
        result.put("clanMember", users);
        return result;
    }

    public Result getRequestClanUsers(String token) {
        long clanId = clanSqlService.findUserClan(sessionSqlService.get(token).userId);
        Result result = new Result();
        List<User> users = userSQLService.getUserByIds(clanSqlService.getRequestClanUsersIds(clanId));
        users.forEach(user -> {
            user.name = securityService.decrypt(user.name);
            user.email_encrypt = securityService.decrypt(user.email_encrypt);
        });
        result.put("clanMember", users);
        return result;
    }

    public Result addUserToClan(String token, long clanId) {
        Result result = new Result();
        try {
            result.put("added", clanSqlService.addUserToClan(sessionSqlService.get(token).userId, clanId));

        } catch (Exception e) {
            result.error("not a valid token");
        }
        return result;
    }

    public void accept(String email, String token) {
        clanSqlService.accept(userSQLService.getUserByEmail(email).id, clanSqlService.findUserClan(sessionSqlService.get(token).userId));
    }

    public void reject(String email, String token) {
        clanSqlService.reject(userSQLService.getUserByEmail(email).id, clanSqlService.findUserClan(sessionSqlService.get(token).userId));
    }

}
