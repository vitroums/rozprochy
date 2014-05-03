package com.rozprochy.tron.troncommon;

import java.io.Serializable;


public class Move implements Serializable{
    private final int playerId;
    private final Direction direction;

    
    public Move(int playerId, Direction direction){
        this.playerId = playerId;
        this.direction = direction;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Move{" + "playerId=" + playerId + ", direction=" + direction + '}';
    }
    
    
    
}

