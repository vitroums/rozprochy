package com.rozprochy.tron.tronserver;

import com.rozprochy.tron.troncommon.*;

public class Player {
    private final GameModel model;
    private int x;
    private int y;
    private boolean alive = true;
    private Direction direction;
    
    Player(GameModel model, int x, int y, boolean human, Direction direction){
        this.model = model;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    public void incX(){
        x++;
        if(x >= model.getxSize() || model.ReadMap(x, y) != -1){
            alive = false;
        }
    }
    public void decX(){
        x--;
        if(x < 0 || model.ReadMap(x, y) != -1){
            alive = false;
        }
    }
    public void incY(){
        y++;
        if(y >= model.getySize() || model.ReadMap(x, y) != -1){
            alive = false;
        }
    }
    public void decY(){
        y--;
        if(y < 0 || model.ReadMap(x, y) != -1){
            alive = false;
        }
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public boolean isAlive() {
        return alive;
    }
    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        if(!(this.direction == Direction.UP && direction == Direction.DOWN ||
           this.direction == Direction.DOWN && direction == Direction.UP ||
           this.direction == Direction.RIGHT && direction == Direction.LEFT ||
           this.direction == Direction.LEFT && direction == Direction.RIGHT
           ))
        {
            this.direction = direction;
        }
    }
}