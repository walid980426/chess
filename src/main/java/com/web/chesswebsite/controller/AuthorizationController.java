package com.web.chesswebsite.controller;

import com.web.chesswebsite.controllerservice.LoginControllerService;
import com.web.chesswebsite.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/Authorization")
public class AuthorizationController {
    private final LoginControllerService loginControllerService;

    @Autowired
    public AuthorizationController(LoginControllerService loginControllerService) {
        this.loginControllerService = loginControllerService;
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public Result login(@RequestBody LoginParams params) {
        return loginControllerService.login(params.email, params.password);
    }

    @PostMapping(value = "/signup")
    public Result signUp(@RequestBody SignUpParams param) {
        return loginControllerService.signup(param.email, param.password, param.usertype, param.username);
    }

    @GetMapping("/get-all")
    public String getAll() {
        return loginControllerService.getAll();
    }

    @PostMapping(value = "/logout")
    public Result logout(@RequestBody LoginParams loginParams) {
        return loginControllerService.logout(loginParams.email);
    }

    public static class LoginParams {
        public String email;
        public String password;
    }

    public static class SignUpParams {
        public String username;
        public String email;
        public int usertype;
        public String password;
    }
}
