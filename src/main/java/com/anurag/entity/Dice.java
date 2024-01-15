package com.anurag.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Random;

@Data
@Component
public class Dice {

    public int throwDice() {
        int min = 1;
        int max = 6;
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;

    }

}
