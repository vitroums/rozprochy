
package com.rozprochy.tron.tronserver;

import com.rozprochy.tron.troncommon.Move;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerGameLoop implements Runnable{
    private ConcurrentLinkedQueue<Move> moves;
    boolean modifiedMap;
    GameModel model;
    private final String message;
    private final long simulationFrameTime = 50;//ms
    
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
                
                
                if(model.whoWin() != 0){
                    if(model.whoWin() == -1)
                        SendingThread.message = "Remis";
                    else
                        SendingThread.message = "Gracz " + model.whoWin() + " wygral";
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ServerGameLoop.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    SendingThread.message = "";
                    model.InitGame();
                    last = 0;
                    
                }
                        
            }
        }
    }

    
}
