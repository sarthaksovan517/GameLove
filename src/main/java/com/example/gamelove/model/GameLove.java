package com.example.gamelove.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "playerId", "gameId" }) })
public class GameLove {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_id")
    private String playerId;

    @Column(name = "game_id")
    private String gameId;

    public GameLove(String gameId, String playerId) {
        this.playerId = playerId;
        this.gameId = gameId;
    }
}
