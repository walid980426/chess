package com.web.chesswebsite.controller;


import com.web.chesswebsite.controllerservice.ClanControllerService;
import com.web.chesswebsite.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clan")
public class ClanController {

    private final ClanControllerService clanControllerService;

    @Autowired
    public ClanController(ClanControllerService clanControllerService) {
        this.clanControllerService = clanControllerService;
    }

    @PostMapping("/search")
    public Result search(@RequestBody Params params) {
        return clanControllerService.search(params.name);
    }

    @PostMapping("/insert")
    public Result insert(@RequestBody Params params) {
        return clanControllerService.insert(params.token, params.name);
    }

    @PostMapping("/clan-member-request")
    public Result getRequestClanUsersIds(@RequestBody Params params) {
        return clanControllerService.getRequestClanUsers(params.token);
    }

    @PostMapping("/clan-member")
    public Result getClanUsersIds(@RequestBody Params params) {
        return clanControllerService.getClanUsersIds(params.token);
    }

    @PostMapping("/add-user")
    public Result addUserToClan(@RequestBody Params params) {
        return clanControllerService.addUserToClan(params.token, params.clanId);
    }

    @PostMapping("/accept")
    public void accept(@RequestBody Params params) {
        clanControllerService.accept(params.email, params.token);
    }

    @PostMapping("/reject")
    public void reject(@RequestBody Params params) {
        clanControllerService.reject(params.email, params.token);
    }

    public static class Params {
        public String name;
        public String email;
        public String token;
        public long clanId;
    }

}
