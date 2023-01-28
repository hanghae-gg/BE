package com.hhgg.hhggbe.riotAPI.service;

import com.hhgg.hhggbe.riotAPI.dto.ApiRequestDto;
import com.hhgg.hhggbe.riotAPI.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApiService {
    @Value("${RIOT_API_KEY}")
    private String apiKey;
    public String getPuuid(ApiRequestDto apiRequestDto) {
        String url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + apiRequestDto.getNickname()
                + "?api_key="
                + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Map> resultMap = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        return resultMap.getBody().get("puuid").toString();
    }

    public List getMatchId(String puuid){
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + puuid
                + "/ids?queue=420&start=0&count=10&api_key="
                + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<List> matchIds = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        return matchIds.getBody();
    }

    public List<ApiResponseDto> match(ApiRequestDto apiRequestDto) {
        String puuid = getPuuid(apiRequestDto);
        List<String> matchIds = getMatchId(puuid);
        List<ApiResponseDto> matchList = new ArrayList<>();
        String nickname;
        Boolean win;
        String championName;
        int deaths;
        int kills;
        int assists;
        for(int i = 0; i<matchIds.size(); i++){
            String url ="https://asia.api.riotgames.com/lol/match/v5/matches/"
                    + matchIds.get(i)
                    + "?api_key="
                    + apiKey;
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
            ResponseEntity<Map> matches = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            List<HashMap<?, ?>> participants = (ArrayList<HashMap<?, ?>>) ((HashMap<?, ?>) matches.getBody().get("info")).get("participants");
            for (HashMap<?, ?> participant: participants) {
                String getPuuid = (String) participant.get("puuid");
                if(getPuuid.equals(puuid)){
                    nickname = (String)participant.get("summonerName");
                    win = (Boolean) participant.get("win");
                    championName = (String)participant.get("championName");
                    deaths = (int) participant.get("deaths");
                    kills = (int) participant.get("kills");
                    assists = (int) participant.get("assists");
                    ApiResponseDto apiResponseDto = new ApiResponseDto(nickname, win, championName, deaths, kills, assists);
                    matchList.add(apiResponseDto);
                }
            }
        }
        return matchList;
    }
}
