package com.rozprochy.tron.tronserver;

import com.rozprochy.tron.troncommon.Direction;
import com.rozprochy.tron.troncommon.Move;
import java.util.concurrent.locks.Lock;

public final class GameModel {

    private final int unit = 10;
    private final int offset = 20;
    private final int xSize;
    private final int ySize;
    private final int[][] map;
    private final Player[] players = new Player[4];
    private Lock mapLock;

    public GameModel( int x, int y, Lock mapLock) {
        xSize = x / unit;
        ySize = (y - offset) / unit;
        map = new int[xSize][ySize];
        InitGame();
        this.mapLock = mapLock;
    }

    public void InitGame() {
        players[0] = new Player(0, this, 0, 0, Direction.RIGHT);
        players[1] = new Player(1, this, xSize - 1, 0, Direction.DOWN);
        players[2] = new Player(2, this, xSize - 1, ySize - 1, Direction.LEFT);
        players[3] = new Player(3, this, 0, ySize - 1, Direction.UP);

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                map[i][j] = -1;
            }
        }
        map[0][0] = 0;
        map[xSize - 1][0] = 1;
        map[xSize - 1][ySize - 1] = 2;
        map[0][ySize - 1] = 3;
    }

    public void change(Move move) {
        players[move.getPlayerId()].setDirection(move.getDirection());
    }

    public int[][] cloneMap() {
        return map.clone();
    }
    
    public int[][] getMap()
    {
        return map;
    }

    public void Go() {

        /*for (int i = 0; i < 4; i++) {
            if (players[i].isAlive()) {
                players[i].decide();
            }
        }*/

        mapLock.lock();
        try
        {
            for (int i = 0; i < 4; i++) 
            {
                if (players[i].isAlive()) 
                {
                    if (players[i].getDirection() == Direction.UP) {
                        players[i].decY();
                    }
                    if (players[i].getDirection() == Direction.DOWN) {
                        players[i].incY();
                    }
                    if (players[i].getDirection() == Direction.LEFT) {
                        players[i].decX();
                    }
                    if (players[i].getDirection() == Direction.RIGHT) {
                        players[i].incX();
                    }
                    if (players[i].isAlive()) {
                        map[players[i].getX()][players[i].getY()] = i;
                    }
                }
            }
        }
        finally
        {
            mapLock.unlock();
        }
        

    }

    public int readMap(int x, int y) {
        return map[x][y];
    }

    public boolean gameWon() {
        return (players[0].isAlive() && !players[1].isAlive() && !players[2].isAlive() && !players[3].isAlive());
    }
    
    public int whoWin() {
        if(players[0].isAlive() && !players[1].isAlive() && !players[2].isAlive() && !players[3].isAlive())
            return 1;
        else if(players[1].isAlive() && !players[0].isAlive() && !players[2].isAlive() && !players[3].isAlive())
            return 2;
        else if(players[2].isAlive() && !players[0].isAlive() && !players[1].isAlive() && !players[3].isAlive())
            return 3;
        else if(players[3].isAlive() && !players[0].isAlive() && !players[2].isAlive() && !players[1].isAlive())
            return 4;
        else if(!players[3].isAlive() && !players[0].isAlive() && !players[2].isAlive() && !players[1].isAlive())
            return -1;
        return 0;
    }

    public boolean gameLost() {
        return !players[0].isAlive();
    }

    public int getUnit() {
        return unit;
    }

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Lock getMapLock() {
        return mapLock;
    }
    

}
