package com.rozprochy.tron.tronserver;

import com.rozprochy.tron.troncommon.Move;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerThread implements Runnable {
    
    private static final int MAX_AVAILABLE = 1; // 1 to binarny, jeden watek i nara, reszta czeka
    
    public Semaphore available = new Semaphore(MAX_AVAILABLE);

    public static final int PORT = 6066;
   
    private static final int TIME_OUT = 10000;

    private static final Logger log = Logger.getLogger(ServerThread.class.getName());

    private boolean running = true;
    
    private ConcurrentLinkedQueue<Move> moves = new ConcurrentLinkedQueue<Move>();
    
    private int playerID = 0;
    
    private ReadWriteLock mapLock = new ReentrantReadWriteLock();
    
    private final int[][] map;
    
    private String message = "";
    
    private GameModel model;
            
    ServerThread()
    {
        model = new GameModel(640, 480, mapLock.writeLock());
        map = model.getMap();
    }
    
    @Override
    public void run() {
        
        try (ServerSocket server = new ServerSocket(PORT)) 
        {
            ServerGameLoop loop = new ServerGameLoop(moves, model, message);
            Thread loopTh = new Thread(loop);
            loopTh.start();
            server.setSoTimeout(TIME_OUT);
            while (true) {
                synchronized (this) {
                    if (!running) {
                        break;
                    }
                }
                try {
                    Socket client = server.accept();
                    if (client != null) {
                        client.setTcpNoDelay(true);
                        semService(available, client);
                    }
                } catch (SocketTimeoutException ex) {}

            }
        } catch (IOException ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
        }
    }

    public synchronized void stop() {
        running = false;
    }
    
    public void semService(Semaphore sem, Socket client){
        try {
            sem.acquire();
            try {
                SendingThread receiver = new SendingThread(client, moves, playerID++, map, message, mapLock.readLock());
                Thread th = new Thread(receiver);
                th.start();
                System.out.printf("Started client thread #%d\n", playerID);
            } finally {
                sem.release();
            }
        } catch(final InterruptedException ie) {
            System.out.println("Oczekiwanie za zwolnienie zasobu");
        }
    }
}
