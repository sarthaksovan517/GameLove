package com.example.gamelove.controller;


import com.example.gamelove.model.GameLove;
import com.example.gamelove.model.MostLovedGameResponse;
import com.example.gamelove.service.GameLoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game-love")
public class GameController {

    @Autowired
    GameLoveService gameLoveService;

    @PostMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Success");
    }

    @PostMapping(value = "/create")
    public void createLoveEntry(@RequestParam String playerId, @RequestParam String gameId) {
        gameLoveService.createLoveEntry(playerId, gameId);
    }

    @DeleteMapping(value = "/unlove", produces = MediaType.APPLICATION_JSON_VALUE)
    public void unloveGame(@RequestParam String playerId, @RequestParam String gameId) {
        gameLoveService.unloveGame(playerId, gameId);
    }

    @GetMapping(value = "/loved-games/{playerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> fetchLovedGames(@PathVariable String playerId) {
        return gameLoveService.fetchLovedGames(playerId);
    }

    @GetMapping(value = "/most-loved-games", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MostLovedGameResponse> fetchMostLovedGames(@RequestParam int limit) throws Exception {
        return gameLoveService.fetchMostLovedGames(limit);
    }


}
