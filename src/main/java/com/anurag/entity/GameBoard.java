package com.anurag.entity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GameBoard {

    private List <Integer> board = new ArrayList<>();


    public List getBoard() {
        return this.board;
    }

    public void setBoard() {
        for(int i =1; i<=100; i++) {
            this.board.add(i);
        }
    }



}
