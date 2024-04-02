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
        try{
            if(limit > 0){
                System.out.println("here");
                return gameLoveRepository.findMostLovedGames(limit);

            }else{
                throw new CustomException("Limit should be > 0");
            }
        }catch (Exception e){
            if(e instanceof CustomException){
                throw e;
            }
            throw new UnexpectedException(e);
        }
    }

    @Override
    public void createLoveEntry(String playerId, String gameId) {
        GameLove gameLove = new GameLove();
        if (!gameLoveRepository.existsByPlayerIdAndGameId(playerId, gameId)) {
            gameLove.setGameId(gameId);
            gameLove.setPlayerId(playerId);
            gameLoveRepository.save(gameLove);
        }else{
            String message = "Player "+playerId+" has already loved game "+ gameId;
            throw new CustomException(message);
        }
    }

    @Override
    @Transactional
    public void unloveGame(String playerId, String gameId) {
        //TODO we can do a player and game id check in respective table
        if (gameLoveRepository.existsByPlayerIdAndGameId(playerId, gameId)) {
            gameLoveRepository.deleteByPlayerIdAndGameId(playerId, gameId);
        }else {
            String message = "Player "+playerId+" has not loved the game "+gameId+" yet";
            throw new CustomException(message);
        }
    }

    @Override
    public List<String> fetchLovedGames(String playerId) {
        //TODO We can do a playerId check here from the player table and handle the outcome accordingly
        List<GameLove> gameIdsByPlayerId = gameLoveRepository.findGameIdsByPlayerId(playerId);
        if(!gameIdsByPlayerId.isEmpty()){
            return  gameIdsByPlayerId.stream().map(GameLove::getGameId).collect(Collectors.toList());
        }else{
            String message = "Either player "+playerId+" is not present or player has not loved any games";
            throw new UnexpectedException(message);
        }

    }
}
