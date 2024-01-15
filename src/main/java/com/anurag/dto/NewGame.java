package com.anurag.dto;

import com.anurag.entity.GameBoard;
import com.anurag.entity.Players;
import com.anurag.entity.SnakeAndLadder;
import lombok.Data;

import java.util.HashMap;

@Data
public class NewGame {
    GameBoard gameBoard;
    SnakeAndLadder snakeAndLadder;
    Players players;
}
