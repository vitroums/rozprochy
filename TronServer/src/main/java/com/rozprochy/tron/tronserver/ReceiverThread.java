package com.rozprochy.tron.tronserver;

import com.rozprochy.tron.troncommon.Move;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiverThread implements Runnable {

    private Socket socket;

    private static final Logger log = Logger.getLogger(ReceiverThread.class.getName());

    public ReceiverThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        try (InputStream is = socket.getInputStream(); ObjectInputStream ois = new ObjectInputStream(is)){
            
            Move move = (Move) ois.readObject();
                  
            System.out.println("Odebrano " + move);
            
        } catch (IOException ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            log.log(Level.SEVERE, null, ex);
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}