package com.example.gamelove.ServiceImpl;

import com.example.gamelove.exception.CustomException;
import com.example.gamelove.exception.UnexpectedException;
import com.example.gamelove.model.GameLove;
import com.example.gamelove.model.MostLovedGameResponse;
import com.example.gamelove.repository.GameLoveRepository;
import com.example.gamelove.service.GameLoveService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameLoveServiceImpl implements GameLoveService {

    @Autowired
    private GameLoveRepository gameLoveRepository;



    @Override
    public List<MostLovedGameResponse> fetchMostLovedGames(int limit) {

        if(limit <= 0){
            throw new CustomException("Limit should be > 0");
        }
        return gameLoveRepository.findMostLovedGames(limit);
    }

    @Override
    public void createLoveEntry(String playerId, String gameId) {
        GameLove gameLove = new GameLove();
        if (gameLoveRepository.existsByPlayerIdAndGameId(playerId, gameId)) {
            String message = "Player " + playerId + " has already loved game " + gameId;
            throw new CustomException(message);
        }
        gameLove.setGameId(gameId);
        gameLove.setPlayerId(playerId);
        gameLoveRepository.save(gameLove);
    }

    @Override
    @Transactional
    public void unloveGame(String playerId, String gameId) {
        //TODO we can do a player and game id check in respective table
        if (!gameLoveRepository.existsByPlayerIdAndGameId(playerId, gameId)) {
            String message = "Player "+playerId+" has not loved the game "+gameId+" yet";
            throw new CustomException(message);
        }
        gameLoveRepository.deleteByPlayerIdAndGameId(playerId, gameId);

    }

    @Override
    public List<String> fetchLovedGames(String playerId) {
        //TODO We can do a playerId check here from the player table and handle the outcome accordingly
        List<GameLove> gameIdsByPlayerId = gameLoveRepository.findGameIdsByPlayerId(playerId);
        if(gameIdsByPlayerId.isEmpty()){
            String message = "Either player "+playerId+" is not present or player has not loved any games";
            throw new UnexpectedException(message);
        }
        return  gameIdsByPlayerId.stream().map(GameLove::getGameId).collect(Collectors.toList());

    }
}
