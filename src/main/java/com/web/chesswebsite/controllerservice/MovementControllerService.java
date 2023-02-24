package com.web.chesswebsite.controllerservice;

import com.web.chesswebsite.model.MovementModel;
import com.web.chesswebsite.sql.MovementSQLTable;
import com.web.chesswebsite.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class MovementControllerService {
    private final
    MovementSQLTable movementSQLTable;

    @Autowired
    public MovementControllerService(MovementSQLTable movementSQLTable) {
        this.movementSQLTable = movementSQLTable;
    }

    public boolean insert(MovementModel movementModel) {
        return movementSQLTable.insert(movementModel);
    }

    public List<MovementModel> selectByTableId(UUID tableId) {
        return movementSQLTable.selectByTableId(tableId);
    }

    public Result selectLastMoveByTableId(UUID tableId) {
        Result result = new Result();
        result.put("lastMove", movementSQLTable.selectLastMoveByTableId(tableId));
        return result;
    }

    public List<MovementModel> select() {
        return movementSQLTable.select();
    }

}
