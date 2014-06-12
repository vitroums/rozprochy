package com.rozprochy.tron.tronserver;

import com.rozprochy.tron.troncommon.Move;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendingThread implements Runnable {

    private Socket socket;

    private static final Logger log = Logger.getLogger(SendingThread.class.getName());
    
    private ConcurrentLinkedQueue<Move> moves;
    
    private int clientID;
    
    private final int[][] map;
    
    public static String message;
    
    private Lock mapReadingLock;

    public SendingThread(Socket socket, ConcurrentLinkedQueue<Move> moves, int clientID,
            int[][] map, String message, Lock mapReadingLock) {
        this.socket = socket;
        this.moves = moves;
        this.clientID = clientID;
        this.map = map;
        this.mapReadingLock = mapReadingLock;
        this.message = message;
        
    }
    
    

    @Override
    public void run() {
        
        ReceivingThread rec = new ReceivingThread(socket, moves);
        Thread recTh = new Thread(rec);
        recTh.start();
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());) {
            out.writeInt(clientID);
            while(true){
                
               StringBuilder sb = new StringBuilder(map.length);
                for (int[] j : map) 
                    for(int k : j)
                        sb.append(k);
                String mapString = sb.toString();
                
                mapReadingLock.lock();
                try{
                    out.writeObject(mapString);
                    out.writeObject(message);
                } finally {
                    mapReadingLock.unlock();
                }

                Thread.sleep(20);
            }
        } catch (IOException ex) {
            Logger.getLogger(SendingThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SendingThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
