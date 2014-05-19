package com.rozprochy.tron.tronserver;

import com.rozprochy.tron.troncommon.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiverThread implements Runnable {

    private static Socket socket;
    
    private GameModel model;

    private static final Logger log = Logger.getLogger(ReceiverThread.class.getName());

    public ReceiverThread(Socket socket, GameModel model) {
        this.socket = socket;
        this.model = model;
    }

    @Override
    public void run(){
        InputStream is = null;
        try {
            ObjectOutputStream out = null;
            is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            Move move = null;     
            try {
                move = (Move) ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ReceiverThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(move != null){
                System.out.println("Odebrano " + move);
                model.Change(move);
            }
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(ServerThread.m);
            
        } catch (IOException ex) {
            Logger.getLogger(ReceiverThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(ReceiverThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       }
}