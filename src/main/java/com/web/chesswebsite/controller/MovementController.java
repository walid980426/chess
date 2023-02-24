package com.web.chesswebsite.controller;


import com.web.chesswebsite.controllerservice.MovementControllerService;
import com.web.chesswebsite.model.MovementModel;
import com.web.chesswebsite.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/move")
public class MovementController {
    private final MovementControllerService movementControllerService;
    @Autowired
    public MovementController(MovementControllerService movementControllerService) {
        this.movementControllerService = movementControllerService;
    }

    @PostMapping(value="insert-move")
    public boolean insertMovement(@RequestBody MovementModel movementModel){
        return movementControllerService.insert(movementModel);//todo delete this
    }
    @PostMapping(value = "last-move")
    public Result selectLastMoveByTableId(@RequestBody Params params){
        return movementControllerService.selectLastMoveByTableId(params.tableId);
    }
    public static class Params{
        UUID tableId;
    }
}
