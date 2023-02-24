package com.web.chesswebsite.sql;

import com.web.chesswebsite.model.MessageModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Service
public class MassagedSqlService {
    public class MassageMapper implements RowMapper<MessageModel> {
        @Override
        public MessageModel mapRow(ResultSet resultSet, int i) throws SQLException {
            MessageModel messageModel = new MessageModel();
            messageModel.time = resultSet.getDate("time");
            messageModel.content = resultSet.getString("content");
            messageModel.tableId = UUID.fromString(resultSet.getString("table_id"));
            messageModel.fromUserId =UUID.fromString(resultSet.getString("from_user_id"));
            messageModel.toUserId =UUID.fromString(resultSet.getString("to_user_id"));

            return messageModel;
        }
    }
}
