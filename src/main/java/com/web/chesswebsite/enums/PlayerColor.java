package com.web.chesswebsite.enums;

public enum PlayerColor {
    BLACK,
    WHITE;

    public static PlayerColor of(int i) {
        return i == 0 ? WHITE : BLACK;

    }

    public static int to(PlayerColor playerColor) {
        return playerColor == WHITE ? 0 : 1;
    }
}
