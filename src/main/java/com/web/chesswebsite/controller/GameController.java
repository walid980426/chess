package com.web.chesswebsite.controller;

import com.web.chesswebsite.controllerservice.GameControllerService;
import com.web.chesswebsite.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping(value = "/game")
public class GameController {
    private final GameControllerService gameControllerService;

    @Autowired
    public GameController(GameControllerService gameControllerService) {
        this.gameControllerService = gameControllerService;
    }

    @PostMapping(value = "/start")
    public Result start(@RequestBody startPrams startPrams) {
        return gameControllerService.start(startPrams.token);
    }

    @PostMapping(value = "/started-game")
    public Result startedGame(@RequestBody startPrams startPrams) {
        return gameControllerService.getStartedGame(startPrams.token);
    }
    @PostMapping(value = "/move")
    public boolean move(@RequestBody MoveParams moveParams) {
        return gameControllerService.move(moveParams.sourceSquare, moveParams.targetSquare, moveParams.piece, UUID.fromString(moveParams.gameId.replace("\"", "")), moveParams.fEN, moveParams.whitePlayer);
    }

    @PostMapping(value = "/end")
    public void end(@RequestBody startPrams params) {
        gameControllerService.end(params.token);
    }

    public static class startPrams {
        public String token;
    }

    public static class MoveParams {
        public String gameId;
        public String sourceSquare;
        public String targetSquare;
        public String piece;
        public boolean whitePlayer;
        public String fEN;
    }
}
