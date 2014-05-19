package com.rozprochy.tron.troncommon;

import java.io.Serializable;

public class Map implements Serializable{    
    private int[][] field;
    private boolean pause = false;
    private boolean won = false;
    private int winningPlayer = -1;
    
    
    public Map(int[][]field){
        this.field = field.clone();
    }
    public Map(int[][] field, boolean pause){
        this.field = field.clone();
        this.pause = pause;
    }
    public Map(int[][] field, boolean won, int winningPlayer){
        this.field = field.clone();
        this.won = won;
        this.winningPlayer = winningPlayer;
    }
    public int read(int x, int y){
        return field[x][y];
    }
    public boolean isPause() {
        return pause;
    }
    public boolean isWon() {
        return won;
    }
    public int getWinningPlayer() {
        return winningPlayer;
    }
}