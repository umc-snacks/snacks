package com.example.demo;

public enum Games {
    LeagueOfLegends, FIFA, BattleGround, ETC;

    public static Games fromValue(String value) {
        for (Games game : Games.values()) {
            if (game.name().equalsIgnoreCase(value)) {
                return game;
            }
        }
        return null; // 유효하지 않은 값인 경우 null 반환
    }
}
