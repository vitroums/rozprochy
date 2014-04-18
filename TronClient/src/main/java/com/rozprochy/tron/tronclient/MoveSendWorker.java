package com.rozprochy.tron.tronclient;

import com.rozprochy.tron.troncommon.Move;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.concurrent.Task;



//przy każdym naciśnieciu WSAD tworzony jest nowy MoveSendWorker, pewnie jest to zbędne, ale na razie niech bedzie tak
public class MoveSendWorker extends Task<Void> {

    public static final int PORT = 9876;
    
    private Move move;

    public MoveSendWorker(Move move) {
        this.move = move;
    }

    @Override
    protected Void call() throws Exception {

        try (Socket socket = new Socket("localhost", PORT); ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream())) {
            os.writeObject(move);
        }
        
        return null;
    }
}
