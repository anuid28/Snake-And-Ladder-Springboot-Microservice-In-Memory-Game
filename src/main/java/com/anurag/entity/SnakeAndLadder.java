package com.anurag.entity;

import java.util.HashMap;

public class SnakeAndLadder {

    public final HashMap<Integer, Integer> snakeAndLadderPointer = new HashMap<>();


    public final void setSnakeAndLadderPointer() {
        //ladder
        this.snakeAndLadderPointer.put(4, 14);
        this.snakeAndLadderPointer.put(9, 31);
        this.snakeAndLadderPointer.put(20, 38);
        this.snakeAndLadderPointer.put(28, 84);
        this.snakeAndLadderPointer.put(40, 59);
        this.snakeAndLadderPointer.put(51, 67);
        this.snakeAndLadderPointer.put(63, 81);
        this.snakeAndLadderPointer.put(71, 91);

        //snake
        this.snakeAndLadderPointer.put(99, 78);
        this.snakeAndLadderPointer.put(95, 75);
        this.snakeAndLadderPointer.put(93, 73);
        this.snakeAndLadderPointer.put(87, 24);
        this.snakeAndLadderPointer.put(62, 19);
        this.snakeAndLadderPointer.put(64, 60);
        this.snakeAndLadderPointer.put(54, 34);
        this.snakeAndLadderPointer.put(17, 7);
    }

    public HashMap<Integer, Integer> getSnakeAndLadderPointer() {
        return this.snakeAndLadderPointer;
    }
}
