package com.rozprochy.tron.tronserver;

public class App 
{
    public static void main( String[] args ){
        ServerThread serverThread =  new ServerThread();
        Thread thread = new Thread(serverThread);
        thread.start();
    }
}
