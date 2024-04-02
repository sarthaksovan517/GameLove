package com.example.gamelove.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MostLovedGameResponse {

    private String gameId;
    private Long count;


    @Override
    public String toString() {
        return "{"+
                "gameId='" + gameId + '\'' +
                ", count=" + count +
                '}';
    }
}
