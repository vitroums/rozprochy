package com.rozprochy.tron.tronserver;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.rozprochy.tron.troncommon.*;

public class GameLoop implements Runnable{
   private Thread t;
   private final GameModel model;
   private boolean gameIsRunning = false;
   private boolean pause = false;
   
   public GameLoop(GameModel model){
       this.model = model;
   }
   
   @Override
   public void run(){
       gameIsRunning = true;
       pause = false;
       model.InitGame();
       while(gameIsRunning){
           try { 
               Thread.sleep(50);
               if(pause){
                   ServerThread.m = new Map(model.getMap(), true);
                   while(pause)
                       Thread.sleep(50);
               }
           } catch (InterruptedException ex) {
               Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
           }
           model.Go();
           if(model.GameWon()){
               gameIsRunning = false;
               ServerThread.m = (new Map(model.getMap(), true, 0));
           }
           if(model.GameLost()){
               gameIsRunning = false;
               ServerThread.m = new Map(model.getMap(), true, 1);
           }
       }
       stopGame();
   }
   public void start ()
   {
        t = new Thread (this);
        t.start ();
   }
   public  void changePause() {
       pause = !pause;
   }
   public  boolean isGameIsRunning() {
       return gameIsRunning;
   }
   public void stopGame(){
       t.stop();
       gameIsRunning = false;
       pause = false;
   }
}