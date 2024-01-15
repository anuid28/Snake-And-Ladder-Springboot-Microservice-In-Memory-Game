package com.anurag.dto;

import com.anurag.entity.Player;
import com.anurag.entity.Players;
import lombok.Data;

@Data
public class NextMove {
    Players players;
    Player winner;
    int currentPlayerTurn;
    int nextPlayerTurn;
    int diceOutcome;
    int previousPositionOfCurrentPlayer;
    String message ="Good Move";

}
