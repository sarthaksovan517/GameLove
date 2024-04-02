package com.example.gamelove.repository;

import com.example.gamelove.model.GameLove;
import com.example.gamelove.model.MostLovedGameResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameLoveRepository extends JpaRepository<GameLove, Long> {

    @Query("SELECT new com.example.gamelove.model.MostLovedGameResponse(g.gameId, COUNT(g))FROM GameLove g GROUP BY g.gameId ORDER BY COUNT(g) DESC LIMIT :limit")
    List<MostLovedGameResponse> findMostLovedGames(int limit);

    boolean existsByPlayerIdAndGameId(String playerId, String gameId);

    void deleteByPlayerIdAndGameId(String playerId, String gameId);

    List<GameLove> findGameIdsByPlayerId(String playerId);
}
