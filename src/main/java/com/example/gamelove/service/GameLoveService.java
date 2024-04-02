package com.example.gamelove.service;

import com.example.gamelove.model.MostLovedGameResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameLoveService {

    public List<MostLovedGameResponse> fetchMostLovedGames(int limit);

    public void createLoveEntry(String playerId, String gameId);

    public void unloveGame(String playerId, String gameId);

    public List<String> fetchLovedGames(String playerId);
}
