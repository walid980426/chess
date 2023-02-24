package com.web.chesswebsite.controllerservice;

import com.web.chesswebsite.board.*;
import com.web.chesswebsite.model.MovementModel;
import com.web.chesswebsite.model.SessionModel;
import com.web.chesswebsite.model.TableModel;
import com.web.chesswebsite.model.User;
import com.web.chesswebsite.sql.MovementSQLTable;
import com.web.chesswebsite.sql.SessionSqlService;
import com.web.chesswebsite.sql.TableSqlService;
import com.web.chesswebsite.sql.UserSQLService;
import com.web.chesswebsite.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameControllerService {
    @Autowired
    private final MovementSQLTable movementSQLTable;
    final private
    UserSQLService userSQLService;
    final private
    TableSqlService tableSqlService;
    private final SessionSqlService sessionSqlService;

    public int[][] grid_matrix; // The grid of the board
    protected int grid_size; // A variable to store the
    protected Piece[] pieces; // Array List to store all 32 pieces in the

    @Autowired
    public GameControllerService(MovementSQLTable movementSQLTable, UserSQLService userSQLService, TableSqlService tableSqlService, SessionSqlService sessionSqlService) {
        this.movementSQLTable = movementSQLTable;
        this.userSQLService = userSQLService;
        this.tableSqlService = tableSqlService;
        this.sessionSqlService = sessionSqlService;
    }

    public void board_construct(UUID tableId, String piece, String fen, boolean white) {
        movementSQLTable.insert(new MovementModel(tableId, piece, fen, white));
        createPieces();
        this.grid_size = 8; // by default. On changing to custom, make this.grid_size = size
        pieces = fromFEN(fen);
        this.grid_matrix = new int[8][8];
        try {
            for (Piece piiece : pieces) {
                this.grid_matrix[piiece.xCoordinate][piiece.yCoordinate] = 1;
            }
        } catch (Exception ignored) {

        }
    }

    public void createPieces() {
        pieces = new Piece[32];
        //pieces.ensureCapacity(32);
        for (int i = 0; i < 8; i++) {
            Pawn pawn_white = new Pawn(0, i, 1);
            pieces[2 * i] = pawn_white;
            Pawn pawn_black = new Pawn(1, i, 6);
            pieces[2 * i + 1] = pawn_black;
        }

        Rook rook_white1 = new Rook(0, 0, 0);
        Rook rook_white2 = new Rook(0, 7, 0);
        pieces[16] = rook_white1;
        pieces[18] = rook_white2;
        Rook rook_black1 = new Rook(1, 0, 7);
        Rook rook_black2 = new Rook(1, 7, 7);
        pieces[17] = rook_black1;
        pieces[19] = rook_black2;
        Knight knight_white1 = new Knight(0, 1, 0);
        Knight knight_white2 = new Knight(0, 6, 0);
        pieces[20] = knight_white1;
        pieces[22] = knight_white2;
        Knight knight_black1 = new Knight(1, 1, 7);
        Knight knight_black2 = new Knight(1, 6, 7);
        pieces[21] = knight_black1;
        pieces[23] = knight_black2;
        Bishop bishop_white1 = new Bishop(0, 2, 0);
        Bishop bishop_white2 = new Bishop(0, 5, 0);
        pieces[24] = bishop_white1;
        pieces[26] = bishop_white2;
        Bishop bishop_black1 = new Bishop(1, 2, 7);
        Bishop bishop_black2 = new Bishop(1, 5, 7);
        pieces[25] = bishop_black1;
        pieces[27] = bishop_black2;
        Queen white_1 = new Queen(0, 3, 0);
        Queen black_1 = new Queen(1, 3, 7);
        pieces[28] = white_1;
        pieces[29] = black_1;
        King white_2 = new King(0, 4, 0);
        King black_2 = new King(1, 4, 7);
        pieces[30] = white_2;
        pieces[31] = black_2;
    }

    public boolean move(String sourceSquare, String targetSquare, String stringPiece, UUID tableId, String fen, boolean white) {
        board_construct(tableId, stringPiece, fen, white);
        int curr_x = chartoInt(sourceSquare.charAt(0));
        int curr_y = sourceSquare.charAt(1) - 49;
        int x = chartoInt(targetSquare.charAt(0));
        int y = targetSquare.charAt(1) - 49;
        int color = stringPiece.charAt(0) == 'w' ? 0 : 1;
        Piece posibelMove = getPieceByPozition(x, y);
        Piece piece = pieceFromChar(stringPiece.charAt(1), color, curr_x, curr_y);
        if (!checkMate(color, tableId) && piece.isMoveLegal(x, y, grid_matrix)) {
            grid_matrix[curr_x][curr_y] = 0;
            piece.setCoordinates(x, y);
            grid_matrix[x][y] = 1;
            return checkAndUndo(piece, tableId);
        } else if (posibelMove != null && posibelMove.color != piece.color && piece.killPiece(x, y, grid_matrix)) {
            grid_matrix[x][y] = 1;
            return true;
        }
        return false;
    }

    public boolean checkAndUndo(Piece piece, UUID tableId) {
        int curr_x = piece.xCoordinate;
        int curr_y = piece.yCoordinate;
        if (piece.color == 1) {
            if (isCheck(pieces[31])) { //check if the black king is in check, if so undo the step.
                piece.setCoordinates(curr_x, curr_y);
                return false;
            }
        } else {
            if (isCheck(pieces[30]))  //check if the white king is in check, if so undo the step.
            {
                piece.setCoordinates(curr_x, curr_y);
                return false;
            }
        }
        return true;
    }


    public boolean isCheck(Piece king) {
        int coordinates_king_x = king.xCoordinate;
        int coordinates_king_y = king.yCoordinate;
        for (int i = 0; i < 32; i++) {
            if (pieces[i].getColor() != king.getColor()) {
                if (pieces[i].isMoveLegal(coordinates_king_x, coordinates_king_y, grid_matrix)) {
                    if (pieces[i].getType().equals("Q")) {
                        return !((pieces[i].isVerticallyObstructed(coordinates_king_x, coordinates_king_y, grid_matrix) || pieces[i].isHorizontallyObstructed(coordinates_king_x, coordinates_king_y, grid_matrix) || pieces[i].isDiagonallyObstructed(coordinates_king_x, coordinates_king_y, grid_matrix))); //check by queen
                    }
                    if (pieces[i].getType().equals("R")) {
                        return !pieces[i].isVerticallyObstructed(coordinates_king_x, coordinates_king_y, grid_matrix);
                    }
                    if (pieces[i].getType().equals("N")) {
                        return true;
                    }
                    if (pieces[i].getType().equals("B")) {
                        return !pieces[i].isDiagonallyObstructed(coordinates_king_x, coordinates_king_y, grid_matrix);

                    }
                    if (pieces[i].getType().equals("P")) //Pawn
                    {
                        return !(pieces[i].isVerticallyObstructed(coordinates_king_x, coordinates_king_y, grid_matrix));
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean checkMate(int color, UUID tableId) {
        King king = findKing(color);
        int curr_x = king.xCoordinate;
        int curr_y = king.yCoordinate;
        if (isCheck(king)) {
            move(curr_x + 1, curr_y, king, tableId);
            move(curr_x + 1, curr_y + 1, king, tableId);
            move(curr_x, curr_y + 1, king, tableId);
            move(curr_x - 1, curr_y + 1, king, tableId);
            move(curr_x - 1, curr_y, king, tableId);
            move(curr_x - 1, curr_y - 1, king, tableId);
            move(curr_x, curr_y - 1, king, tableId);
            move(curr_x + 1, curr_y - 1, king, tableId);
            int temp_x = king.xCoordinate;
            int temp_y = king.yCoordinate;
            if ((curr_x == temp_x) && (curr_y == temp_y)) {
                king.setCoordinates(curr_x, curr_y);//Get the king to its original position
                return true;
            } else {
                king.setCoordinates(curr_x, curr_y);
                return false;
            }
        } else {
            king.setCoordinates(curr_x, curr_y);
            return false;
        }
    }

    public void move(int x, int y, Piece piece, UUID tableId) {
        String sourceSquare = intToChar(x) + Integer.toString(y);
        String targetSquare = intToChar(piece.xCoordinate) + Integer.toString(piece.yCoordinate);
        String stringPiece = (piece.color == 0 ? "w" : "b") + piece.getType();
        move(sourceSquare, targetSquare, stringPiece, tableId, null, true);
    }

    public String toFEN(Piece[] pieces) {
        String[] lines = new String[8];
        for (int i = 0; i < 8; i++) {
            lines[i] = "8";
        }
        return "";
        //todo implement this
    }

    public Piece[] fromFEN(String FEN) {
        Piece[] pieces = new Piece[32];
        int pieceIndex = 0;
        String[] temp = FEN.split(" ");
        String[] lines = temp[0].split("\\/");
        for (int i = 0; i < 8; i++) {
            char[] squares = lines[0].toCharArray();
            int j = 0;
            while (j < 8) {
                if (!Character.isDigit(squares[j])) {
                    if (squares[j] == 'r') {
                        pieces[pieceIndex] = new Rook(0, i, j);
                    } else if (squares[j] == 'n') {
                        pieces[pieceIndex] = new Knight(0, i, j);
                    } else if (squares[j] == 'b') {
                        pieces[pieceIndex] = new Bishop(0, i, j);
                    } else if (squares[j] == 'q') {
                        pieces[pieceIndex] = new Queen(0, i, j);
                    } else if (squares[j] == 'k') {
                        pieces[pieceIndex] = new King(0, i, j);
                    } else if (squares[j] == 'R') {
                        pieces[pieceIndex] = new Rook(1, i, j);
                    } else if (squares[j] == 'N') {
                        pieces[pieceIndex] = new Knight(1, i, j);
                    } else if (squares[j] == 'B') {
                        pieces[pieceIndex] = new Bishop(1, i, j);
                    } else if (squares[j] == 'Q') {
                        pieces[pieceIndex] = new Queen(1, i, j);
                    } else if (squares[j] == 'K') {
                        pieces[pieceIndex] = new King(1, i, j);
                    }
                    pieceIndex++;
                    j++;
                } else {
                    int index = squares[j];
                    j += index;
                }
                //rnbqkbnr

            }
        }
        //todo contiun implement this
        return pieces;

    }


    public Result start(String token) {
        Result result = new Result();
        SessionModel sessionModel = sessionSqlService.get(token);
        User user = userSQLService.getUserById(sessionModel.userId);
        UUID tableId = UUID.randomUUID();
        int tableType = tableSqlService.addUserToTableOrCreateOne(user, tableId);
        if (tableType == 0) {
            result.put("id", tableId);
        } else {
            result.put("id", tableSqlService.getTableByPlayer(user.id).id);
        }
        result.put("table_type", tableType);// 0 = new one 1 = update table with 1 player
        return result;

    }

    public int chartoInt(char x) {
        switch (x) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
        }
        return -1;
    }

    public char intToChar(int x) {
        switch (x) {
            case 0:
                return 'a';
            case 1:
                return 'b';
            case 2:
                return 'c';
            case 3:
                return 'd';
            case 4:
                return 'e';
            case 5:
                return 'f';
            case 6:
                return 'g';
            case 7:
                return 'h';
        }
        return '\n';
    }

    public void setNewPosition(int curr_x, int curr_y, int x, int y) {
        for (int i = 0; i < 32; i++) {
            if (pieces[i] != null && pieces[i].xCoordinate == curr_x && pieces[i].yCoordinate == curr_y) {
                pieces[i].xCoordinate = x;
                pieces[i].yCoordinate = y;
                toFEN(pieces);
            }
        }
    }

    public Piece pieceFromChar(char charPiece, int color, int x, int y) {
        switch (charPiece) {
            case 'R':
                return new Rook(color, x, y);
            case 'N':
                return new Knight(color, x, y);
            case 'B':
                return new Bishop(color, x, y);
            case 'Q':
                return new Queen(color, x, y);
            case 'K':
                return new King(color, x, y);
            case 'P':
                return new Pawn(color, x, y);
        }
        return null;
    }

    public Piece findPieceByColorAndTypeAndPosition(int color, String type, int x, int y) {
        for (int i = 0; i < pieces.length; i++) {
            Piece piece = pieces[i];
            if (piece != null && piece.color == color && piece.xCoordinate == x && piece.yCoordinate == y && piece.getType().equals(type.toUpperCase())) {
                return piece;
            }
        }
        return null;
    }

    public King findKing(int color) {
        for (int i = 0; i < pieces.length; i++) {
            Piece piece = pieces[i];
            if (piece != null && piece.color == color && piece.getType().equals("K")) {
                return (King) piece;
            }
        }
        return null;
    }

    public Piece getPieceByPozition(int x, int y) {
        for (Piece piece : pieces) {
            if (piece.xCoordinate == x && piece.yCoordinate == y) {
                return piece;
            }
        }
        return null;
    }

    public Result getStartedGame(String token) {
        Result result = new Result();
        UUID userId = sessionSqlService.get(token).userId;
        TableModel tableModel = tableSqlService.getTableByPlayer(userId);
        if (tableModel != null) {
            result.put("have2Player", tableModel.user1Id != null && tableModel.user2Id != null);
            assert tableModel.user1Id != null;
            if (tableModel.user1Id.equals(userId)) {
                result.put("white", true);
            } else {
                result.put("white", false);
            }
            result.put("tableId", tableModel.id);
            MovementModel movementModel = movementSQLTable.selectLastMoveByTableId(tableModel.id);
            if (movementModel != null) {
                result.put("fen", movementModel.fen);
            } else {
                result.put("fen", null);

            }
            return result;
        }
        return null;
    }

    public void end(String token) {
        UUID loserId = sessionSqlService.get(token).userId;
        TableModel tableModel = tableSqlService.getTableByPlayer(loserId);
        UUID winerId = tableModel.user1Id.equals(loserId) ? tableModel.user2Id : tableModel.user1Id;
        userSQLService.updateLoserRank(loserId);
        userSQLService.updateWinerRank(winerId);
        tableSqlService.updateState(tableModel.id, 2);
    }
}
