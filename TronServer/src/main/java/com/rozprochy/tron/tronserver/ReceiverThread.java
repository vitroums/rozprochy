package com.rozprochy.tron.tronserver;

import com.rozprochy.tron.troncommon.*;
import java.io.IOException;
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
    public void run() {

        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream()); ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());) {

            Move move = (Move) ois.readObject();

            if (move != null) {
                System.out.println("Odebrano " + move);
                model.Change(move);
            }

            out.writeObject(ServerThread.m);

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ReceiverThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
