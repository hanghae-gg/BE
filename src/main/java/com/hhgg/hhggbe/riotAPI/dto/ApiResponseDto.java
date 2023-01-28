package com.hhgg.hhggbe.riotAPI.dto;

import lombok.Getter;

@Getter
public class ApiResponseDto {
    private final String nickname;
    private final Boolean win;
    private final String championName;
    private final int deaths;
    private final int kills;
    private final int assists;
    private final double kda;

    public ApiResponseDto(String nickname, Boolean win, String championName, int deaths, int kills, int assists) {
        this.nickname = nickname;
        this.win = win;
        this.championName = championName;
        this.deaths = deaths;
        this.kills = kills;
        this.assists = assists;
//        this.kda = (kills + assists) / (double) deaths;
        this.kda = (double) Math.round((kills + assists) / (double) deaths * 100) / 100;
    }
}
