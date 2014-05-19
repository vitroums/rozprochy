package com.rozprochy.tron.tronserver;

import com.rozprochy.tron.troncommon.*;

public final class GameModel { 
    private final int x = 640;
    private final int y = 480;
    private final int unit = 10;
    private final int offset = 20;
    private final int xSize;
    private final int ySize;
    private final int[][] map;
    private final Player player[];
    private final GameLoop loop;
    
    public GameModel(){
        xSize = x/unit;
        ySize = (y - offset)/unit;
        map = new int[xSize][ySize];
        player = new Player[4];
        InitGame();
        loop = new GameLoop(this);
    }
    public void InitGame(){
        player[0] = new Player(this,0,0,true,Direction.RIGHT);
        player[1] = new Player(this,xSize - 1,0,false,Direction.DOWN);
        player[2] = new Player(this,xSize - 1,ySize - 1,false,Direction.LEFT);
        player[3] = new Player(this,0,ySize - 1,false,Direction.UP);
     
        for(int i = 0; i < xSize; i++)
            for(int j = 0; j < ySize; j++)
                map[i][j] = -1;
        map[0][0] = 0;
        map[xSize - 1][0] = 1;
        map[xSize - 1][ySize - 1] = 2;
        map[0][ySize - 1] = 3;
        ServerThread.m = new Map(map);
    }
    public void Change(Move move){
        if(move.isMove())
            player[move.getPlayerId()].setDirection(move.getDirection());
        else{
            if(move.isPause())
                changePause();
            else{
                if(!gameRunning())
                    newGame();
                else
                    stopGame();
            }
        }
    }    
    public  void Go(){
        for(int i = 0; i < 4; i++){
            if(player[i].isAlive()){
                if(player[i].getDirection() == Direction.UP)
                    player[i].decY();
                if(player[i].getDirection() == Direction.DOWN)
                    player[i].incY();
                if(player[i].getDirection() == Direction.LEFT)
                    player[i].decX();
                if(player[i].getDirection() == Direction.RIGHT)
                    player[i].incX();
                if(player[i].isAlive())
                    map[player[i].getX()][player[i].getY()] = i;
             }
        }
    }    
    public  int ReadMap(int x, int y){
        return map[x][y];
    }
    public  boolean GameWon(){
        return (player[0].isAlive() && !player[1].isAlive() && !player[2].isAlive() && !player[3].isAlive());
    }
    public  boolean GameLost(){
           return !player[0].isAlive();
    }
    public int getUnit() {
        return unit;
    }
    public void changePause(){
        loop.changePause();
    }
    public boolean gameRunning(){
        return loop.isGameIsRunning();
    }
    public void newGame(){
        loop.start();
    }
    public void stopGame(){
        loop.stopGame();
        InitGame();
    }
    public  int getxSize() {
        return xSize;
    }
    public  int getySize() {
        return ySize;
    }
    public int[][] getMap() {
        return map;
    }
}