package com.anurag.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class Players {
    private List<Player> playerList = new ArrayList<>();
    public void setPlayerList() {
        for (int i = 1; i < 5; i++) {
            Player player = new Player();
            player.setPlayerId(i);
            player.setName("Mr. PlayerName " + i);
            player.setCurrentPosition(0);
            this.playerList.add(player);
        }

    }


}
