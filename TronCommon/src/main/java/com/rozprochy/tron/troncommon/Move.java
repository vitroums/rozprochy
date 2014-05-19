package com.rozprochy.tron.troncommon;

import java.io.Serializable;

public class Move implements Serializable{
    private int playerId;
    private Direction direction;
    private boolean move = true; //true - przesyłany jest WSAD, false - N lub P
    private boolean pause; //true - pause, false - start/stop
    
    public Move(int playerId, Direction direction){
        this.playerId = playerId;
        this.direction = direction;
    }
    public Move(boolean pause){
        this.move = false;
        this.pause = pause;
    }
    public int getPlayerId() {
        return playerId;
    }
    public Direction getDirection() {
        return direction;
    }
    public boolean isMove() {
        return move;
    }
    public boolean isPause() {
        return pause;
    }
    @Override
    public String toString() {
        return "Move{" + "playerId=" + playerId + ", direction=" + direction + ", move=" + move + ", pause=" + pause + '}';
    }
}