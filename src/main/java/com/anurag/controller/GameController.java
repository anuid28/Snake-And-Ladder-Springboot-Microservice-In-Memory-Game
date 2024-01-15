package com.anurag.controller;


import com.anurag.dto.NewGame;
import com.anurag.dto.NextMove;
import com.anurag.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

@RestController
@RequestMapping("/Game")
public class GameController {

    @Autowired
    GameService gameService;

    @GetMapping("/startNewGame")
    private ResponseEntity<?> startNewGame() {
        NewGame NewGame = gameService.loadGame();
        return new ResponseEntity<NewGame>(NewGame, HttpStatus.OK);
    }

    @GetMapping("/nextMove")
    private ResponseEntity<?> nextMove() {
        NextMove nextmove = gameService.nextMove();
        return new ResponseEntity<NextMove>(nextmove, HttpStatus.OK);
    }

}
