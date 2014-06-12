
package com.rozprochy.tron.tronserver;


import com.rozprochy.tron.troncommon.Move;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceivingThread implements Runnable
{
    private Socket socket;
    private ConcurrentLinkedQueue<Move> moves;

    public ReceivingThread(Socket sock, ConcurrentLinkedQueue<Move> moves) 
    {
        this.socket = sock;
        this.moves = moves;
    }
    
    @Override
    public void run() 
    {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());)
        {
            while(true)
            {
                Move move = (Move) ois.readObject();
                moves.add(move);
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(ReceivingThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReceivingThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
