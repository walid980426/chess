package com.web.chesswebsite.service;

import com.web.chesswebsite.sql.UserSQLService;
import com.web.chesswebsite.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserSQLService sqlUser;

    @Autowired
    public UserService(UserSQLService userSQLService) {
        this.sqlUser = userSQLService;
    }

    public Result friends(String email) {
        Result result = new Result();
        result.put("friends", sqlUser.getFriends(email));
        return result;
    }

    public Result getUserFromClan(int clanId) {
        Result result = new Result();
        result.put("clanMember", sqlUser.getUsersFromClan(clanId));
        return result;
    }

    public Result getUserByRank(int rank) {
        Result result = new Result();
        result.put("users", sqlUser.getUserByRank(rank));
        return result;
    }

    public Result getAll() {
        Result result = new Result();
        result.put("users", sqlUser.getAllUsers());
        return result;
    }


}
