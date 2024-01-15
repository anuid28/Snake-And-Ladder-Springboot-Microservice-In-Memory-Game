package com.anurag.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Player {

    private int playerId;

    private String name;

    private int currentPosition;

}
