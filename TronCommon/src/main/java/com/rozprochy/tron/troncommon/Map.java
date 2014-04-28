package com.rozprochy.tron.troncommon;

import java.io.Serializable;

public class Map implements Serializable{    
    private static int xSize = 47;
    private static int ySize = 31;

    public static int getxSize() {
        return xSize;
    }

    public static int getySize() {
        return ySize;
    }
    private int[][] map;
    private int x[] = new int[4];
    private int y[] = new int[4];
    
    public Map(){
        map = new int[xSize][ySize];
        for(int i = 0; i < xSize; i++)
            for(int j = 0; j < ySize; j++)
                map[i][j] = -1;
        x[0] = 0;
        y[0] = 0;
        x[1] = xSize - 1;
        y[1] = 0;
        x[2] = 0;
        y[2] = ySize - 1;
        x[3] = xSize - 1;
        y[3] = ySize - 1;
        map[x[0]][y[0]] = 0;
        map[x[1]][y[1]] = 1;
        map[x[2]][y[2]] = 2;
        map[x[3]][y[3]] = 3;
    }
    
    public void Change(Move move){
        Direction direction = move.getDirection();
        if(direction == Direction.UP)
            y[move.getPlayerId()]--;
        if(direction == Direction.DOWN)
            y[move.getPlayerId()]++;
        if(direction == Direction.LEFT)
            x[move.getPlayerId()]--;
        if(direction == Direction.RIGHT)
            x[move.getPlayerId()]++;
        map[x[0]][y[0]] = 0;
        map[x[1]][y[1]] = 1;
        map[x[2]][y[2]] = 2;
        map[x[3]][y[3]] = 3;
    }
    
    public int ReadMap(int x, int y){
        return map[x][y];
    }
}