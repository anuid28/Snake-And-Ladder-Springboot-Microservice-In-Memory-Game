package com.anurag.service;


import com.anurag.dto.NewGame;
import com.anurag.dto.NextMove;
import com.anurag.entity.*;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class GameService {

    private Players players;

    private Player winner;

    private int currentPlayerTurn = 0;
    private int nextPlayerTurn = 0;
    private int diceOutcome = 0;

    private int currentPlayerCurrentPositionBeforeDiceThrow;

    private NextMove nextMoveObj;

    private void presetForLoadGame() {
        this.setCurrentPlayerTurn(0);
        this.setNextPlayerTurn(0);
        this.setCurrentPlayerCurrentPositionBeforeDiceThrow(0);
        this.setDiceOutcome(0);
        this.setWinner(null);

    }

    public NewGame loadGame() {
        this.presetForLoadGame();
        NewGame newGame = new NewGame();

        GameBoard gameBoard = new GameBoard();
        gameBoard.setBoard();
        newGame.setGameBoard(gameBoard);

        SnakeAndLadder snakeAndLadderObj = new SnakeAndLadder();
        snakeAndLadderObj.setSnakeAndLadderPointer();
        newGame.setSnakeAndLadder(snakeAndLadderObj);

        Players players = new Players();
        players.setPlayerList();
        newGame.setPlayers(players);

        this.setPlayers(players);
        return newGame;

    }

    private void preSetForNextMove() {
        this.setCurrentPlayerTurn(this.getNextPlayerTurn());
        Players players = this.getPlayers();
        List<Player> playerList = players.getPlayerList();
        Player CurrentPlayer = playerList.get(this.getCurrentPlayerTurn());
        int currentPlayerCurrentPosition = CurrentPlayer.getCurrentPosition();
        this.setCurrentPlayerCurrentPositionBeforeDiceThrow(currentPlayerCurrentPosition);
        Dice dice = new Dice();
        this.setDiceOutcome(dice.throwDice());

    }

    public NextMove nextMove() {
        this.setNextMoveObj(new NextMove());
        if (this.getWinner() != null) {
            String playerName = this.getWinner().getName();
            NextMove nextMove = this.getNextMoveObj();
            nextMove.setMessage(playerName + " already won the game.Please start a new game.");

            nextMove.setPlayers(null);
            nextMove.setCurrentPlayerTurn(0);
            nextMove.setNextPlayerTurn(0);
            nextMove.setDiceOutcome(0);
            nextMove.setPreviousPositionOfCurrentPlayer(0);
            return nextMove;
        }

        this.preSetForNextMove();

        if (this.getCurrentPlayerCurrentPositionBeforeDiceThrow() == 0) {
            this.currentPlayerPositionZero();
        } else {
            this.currentPlayerPositionGreaterThanZero();
        }
        return this.outputDto();
    }

    private void currentPlayerPositionZero() {
        if (this.getDiceOutcome() != 6) {
            this.nextPlayerTurn(false);
            this.getNextMoveObj().setMessage("Better Luck, Next Time");
        } else {
            this.getPlayers().getPlayerList().get(this.getCurrentPlayerTurn()).setCurrentPosition(1);
            this.nextPlayerTurn(true);
            this.getNextMoveObj().setMessage("Your position started. You got another chance to roll the dice");
        }

    }

    private void currentPlayerPositionGreaterThanZero() {
        int newPosition = this.getCurrentPlayerCurrentPositionBeforeDiceThrow() + this.getDiceOutcome();
        SnakeAndLadder snakeAndLadder = new SnakeAndLadder();
        snakeAndLadder.setSnakeAndLadderPointer();

        if (snakeAndLadder.getSnakeAndLadderPointer().containsKey(newPosition)) {
            int SnakeAndLadderValue = snakeAndLadder.getSnakeAndLadderPointer().get(newPosition);
            if (newPosition > SnakeAndLadderValue) {
                this.getNextMoveObj().setMessage("Snake Bite");
            } else {
                this.getNextMoveObj().setMessage("Ladder Ride");
            }
            newPosition = SnakeAndLadderValue;
        }
        String message = this.getNextMoveObj().getMessage();
        if (newPosition > 100) {

            if (this.getDiceOutcome() == 6) {
                this.nextPlayerTurn(true);
                this.getNextMoveObj().setMessage(message + " You are close to win and You got one more change to roll the dice");
            } else {
                this.nextPlayerTurn(false);
                this.getNextMoveObj().setMessage(message + " You are close to win.");
            }
            this.getPlayers().getPlayerList().get(this.getCurrentPlayerTurn()).setCurrentPosition(this.getCurrentPlayerCurrentPositionBeforeDiceThrow());

        } else if (newPosition < 100) {

            if (this.getDiceOutcome() == 6) {
                this.nextPlayerTurn(true);
                this.getNextMoveObj().setMessage(message + " Woo, You got one more change to roll the dice");
            } else {
                this.nextPlayerTurn(false);
            }
            this.getPlayers().getPlayerList().get(this.getCurrentPlayerTurn()).setCurrentPosition(newPosition);
        } else {
            this.getPlayers().getPlayerList().get(this.getCurrentPlayerTurn()).setCurrentPosition(newPosition);
            Player currentPlayerObj = this.getPlayers().getPlayerList().get(this.getCurrentPlayerTurn());
            this.setWinner(currentPlayerObj);
            this.getNextMoveObj().setWinner(currentPlayerObj);
            this.setNextPlayerTurn(-1);
            String playerName = currentPlayerObj.getName();
            this.getNextMoveObj().setMessage(playerName + " won the game.");
        }

    }

    private void nextPlayerTurn(Boolean keepCurrentPlayer) {
        if (keepCurrentPlayer) {
            this.setNextPlayerTurn(this.getCurrentPlayerTurn());
        } else {
            int nextPlayerturn = this.getCurrentPlayerTurn() + 1;
            int size = this.getPlayers().getPlayerList().size();
            if (nextPlayerturn >= size) {
                nextPlayerturn = 0;
            }
            this.setNextPlayerTurn(nextPlayerturn);
        }
    }

    private NextMove outputDto() {
        NextMove nextMove = this.getNextMoveObj();
        nextMove.setPlayers(this.getPlayers());
        nextMove.setCurrentPlayerTurn(this.getCurrentPlayerTurn() + 1);
        nextMove.setNextPlayerTurn(this.getNextPlayerTurn() + 1);
        nextMove.setDiceOutcome(this.getDiceOutcome());
        nextMove.setPreviousPositionOfCurrentPlayer(this.getCurrentPlayerCurrentPositionBeforeDiceThrow());
        return nextMove;
    }
}
