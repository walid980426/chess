package com.web.chesswebsite.controller;

import com.web.chesswebsite.service.UserService;
import com.web.chesswebsite.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/search")
public class SearchController {
    @Autowired
    private
    UserService userService;


    @PostMapping(value = "/friends")
    public Result friends(@RequestBody SearchParams searchParams) {
        return userService.friends(searchParams.email);
    }

    @GetMapping(value = "/all")
    public Result all() {
        return userService.getAll();
    }

    @PostMapping(value = "/clan-members")
    public Result clanMembers(@RequestBody SearchParams searchParams) {
        return userService.getUserFromClan(searchParams.clanId);
    }

    @PostMapping(value = "/in-rank-range")
    public Result inRankRange(@RequestBody SearchParams searchParams) {
        return userService.getUserByRank(searchParams.rank);
    }

    public static class SearchParams {
        public String email;
        public int rank;
        public int clanId;
    }
}
