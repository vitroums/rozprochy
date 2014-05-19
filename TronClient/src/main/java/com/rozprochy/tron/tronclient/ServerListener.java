package com.rozprochy.tron.tronclient;

import com.rozprochy.tron.troncommon.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerListener implements Runnable{
   private Thread t;
   private GameView view;
     
   ServerListener(GameView view){
       this.view = view;
   }
   @Override
   public void run(){
       while(true){
           try {
               t.sleep(50);
           } catch (InterruptedException ex) {
               Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
           }
                    Move move = null;
                    Socket client = null;
                    try {
                        client = new Socket("localhost", 6066);
                        ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
                        os.writeObject(move);
                    } catch (IOException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    InputStream inFromServer = null;
                    try {
                        inFromServer = client.getInputStream();
                        ObjectInputStream ois = new ObjectInputStream(inFromServer);
                        Map map = null;
                        try {
                            map = (Map) ois.readObject();
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        view.Display(map);
                    } catch (IOException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            inFromServer.close();
                        } catch (IOException ex) {
                            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
       }
       
   }
   public void start ()
   {
        t = new Thread (this);
        t.start ();
   }
}
