package com.rozprochy.tron.tronclient;

import com.rozprochy.tron.troncommon.Map;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerListener implements Runnable{
   private Thread t;
   private GameView view;
     
   ServerListener(GameView view){
       this.view = view;
   }
   /* teraz jest źle i nie działa. Trzeba zrobić żeby co kilka razy na sekunde łapało mape z serwera i ją wyswietlało*/
   
   @Override
   public void run(){
       InputStream inFromServer = null;
       while(true){
           try {
               t.sleep(100);
           } catch (InterruptedException ex) {
               Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
           }
           try {
               Socket client = new Socket("localhost", 6066);
               inFromServer = client.getInputStream();
               ObjectInputStream ois = new ObjectInputStream(inFromServer);
               Map map = null;
               try {
                   map = (Map) ois.readObject();
               } catch (ClassNotFoundException ex) {
                   Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
               }
               view.Display(map); 
               inFromServer.close();
           } catch (IOException ex) {
               Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       
   }
   public void start ()
   {
        t = new Thread (this);
        t.start ();
   }
}
