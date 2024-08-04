package com.anurag.dto;

import com.anurag.entity.Player;
import com.anurag.entity.Players;
import lombok.Data;

import java.util.HashMap;

@Data
public class NextMove {
    Players players;
    Player winner;
    int currentPlayerTurn;
    int nextPlayerTurn;
    int diceOutcome;
    int previousPositionOfCurrentPlayer;
    HashMap<Integer, Integer> snakeOrLadderPointer = new HashMap<>();
    String message ="Good Move.";

}
