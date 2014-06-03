package com.rozprochy.tron.tronclient;

import com.rozprochy.tron.troncommon.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

public class ServerListener implements Runnable {

    private Thread t;
    private GameView view;

    ServerListener(GameView view) {
        this.view = view;
    }

    @Override
    public void run() {
        while (true) {
            try {
                t.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
            }

            try (Socket socket = new Socket("localhost", 6066); ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

                oos.writeObject((Move) null);

                final Map map = (Map) ois.readObject();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        view.Display(map);
                    }
                });

            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void start() {
        t = new Thread(this);
        t.start();
    }
}
