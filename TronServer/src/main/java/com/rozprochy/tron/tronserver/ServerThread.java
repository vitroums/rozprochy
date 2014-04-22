package com.rozprochy.tron.tronserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread implements Runnable {

    public static final int PORT = 9876;

    private static final int TIME_OUT = 1000;

    private static final Logger log = Logger.getLogger(ServerThread.class.getName());

    private boolean running = true;

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(PORT)) {
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
                        ReceiverThread receiver = new ReceiverThread(client);
                        Thread th = new Thread(receiver);
                        th.start();
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
}
