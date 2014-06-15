package com.rozprochy.tron.tronclient.model;

import com.rozprochy.tron.tronclient.MainApp;
import com.rozprochy.tron.tronclient.view.GameView;
import com.rozprochy.tron.troncommon.Map;
import com.rozprochy.tron.troncommon.Move;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

public class ClientGameLoop implements Runnable{
    private final GameView view;
    private boolean gameIsRunning = false;
    private ConcurrentLinkedQueue<Move> moves;
    private int playerID;
   
    public ClientGameLoop( GameView view, ConcurrentLinkedQueue<Move> moves ){
        this.view = view;
        this.moves = moves;
    }
    
    @Override
    public void run()
    {
        Socket client = null;
        while(client == null)
        {
            try
            {
                client = new Socket("localhost", 6066);
            }
            catch(java.net.ConnectException ex)
            {   
            }
            catch(IOException ex)
            {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try(
                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(client.getInputStream());) {   
            
            playerID = ois.readInt();
            view.setPlayerId(playerID);
            while(true)
            {
                while(!moves.isEmpty())
                {
                    Move move = new Move(playerID, moves.poll().getDirection());
                    oos.writeObject(move);
                }
                final String mapString = (String)ois.readObject();
                final String message = (String)ois.readObject();
                
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        view.Display(mapString);
                        view.print(message);
                    }
                });

            }
                
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try {
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientGameLoop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
