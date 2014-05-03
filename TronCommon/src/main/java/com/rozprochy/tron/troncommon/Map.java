package com.rozprochy.tron.troncommon;

import java.io.Serializable;

public class Map implements Serializable{    
    private static int xSize = 47;
    private static int ySize = 31;
    
    private int x[] = new int[4];
    private int y[] = new int[4];

    public static int getxSize() {
        return xSize;
    }

    public static int getySize() {
        return ySize;
    }
    private int[][] map;

    
    public Map(){
        map = new int[xSize][ySize];
        for(int i = 0; i < xSize; i++)
            for(int j = 0; j < ySize; j++)
                map[i][j] = -1;
        x[0] = 0;
        y[0] = 0;
        map[x[0]][y[0]] = 0;
        
        x[1] = xSize - 1;
        y[1] = 0;
        map[x[1]][y[1]] = 1;
        
        x[2] = 0;
        y[2] = ySize - 1;
        map[x[2]][y[2]] = 2;
        
        x[3] = xSize - 1;
        y[3] = ySize - 1;
        map[x[3]][y[3]] = 3;
    }
    
    public void Change(Move move){
        Direction direction = move.getDirection();
        int id = move.getPlayerId();
        if(direction == Direction.UP /*&& (y[id] - 1) == (-1)*/)
            y[id]--;
        if(direction == Direction.DOWN /*&& (y[id] + 1) == (-1)*/)
            y[id]++;
        if(direction == Direction.LEFT /*&& (x[id] - 1) == (-1)*/)
            x[id]--;
        if(direction == Direction.RIGHT /*&& (x[id] + 1) == (-1)*/)
            x[id]++;
        map[x[id]][y[id]] = id;
    }
    
    public int ReadMap(int x, int y){
        return map[x][y];
    }
}