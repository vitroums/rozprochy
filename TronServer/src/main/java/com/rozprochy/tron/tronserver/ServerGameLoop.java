
package com.rozprochy.tron.tronserver;

import com.rozprochy.tron.troncommon.Move;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerGameLoop implements Runnable{
    private ConcurrentLinkedQueue<Move> moves;
    boolean modifiedMap;
    GameModel model;
    private final String message;
    private final long simulationFrameTime = 500;//ms
    
    public ServerGameLoop(ConcurrentLinkedQueue<Move> moves, GameModel model, String message) 
    {
        this.moves = moves;
        this.model = model;
        this.message = message;
    }
    
    @Override
    public void run() {
        while(true)
        {
            long last = 0;
            while(true)
            {
                if(!moves.isEmpty())
                {
                    while(!moves.isEmpty())
                    {
                        Move move = moves.poll();
                        model.change(move);
                    }
                }
                long now = System.nanoTime() / (long)1e6;
                if(now - last > simulationFrameTime)
                {
                    last = now;
                    model.Go();
                }
                
                
               /* if(model.whoWin() != 0){
                    break;
                }/*
                else if(model.gameLost()){
                    break;
                }
                */
                        
            }
        }
    }

    
}
