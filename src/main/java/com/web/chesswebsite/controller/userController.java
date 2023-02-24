package com.web.chesswebsite.controller;


import com.web.chesswebsite.model.User;
import com.web.chesswebsite.sql.UserSQLService;
import com.web.chesswebsite.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class userController {
    private final UserSQLService userSQLService;

    @Autowired
    public userController(UserSQLService userSQLService) {
        this.userSQLService = userSQLService;
    }

    @PutMapping(value = "/update")
    @ResponseBody
    public Result updateUser(@RequestBody Params params) {
        return userSQLService.updateUser(new User(params.email,null, params.name), params.lastEmail);
    }

    public static class Params {
        public String lastEmail;
        public String email;
        public String name;
    }
}
