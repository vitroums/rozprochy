package com.rozprochy.tron.tronclient;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.rozprochy.tron.troncommon.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends Application {
    private final int x = 640;
    private final int y = 480;
    private final int unit = 10;
    private final int offset = 20;
    private GameView view;
    
    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(x, y);
        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, x, y);
        scene.setOnKeyPressed(new ControllerEventHandler());
        primaryStage.setResizable(false);
        primaryStage.setTitle("Tron");
        primaryStage.setScene(scene);
        primaryStage.show();
        view = new GameView(canvas, unit, x/unit, (y - offset)/unit, offset);
        ServerListener listener = new ServerListener(view);
        listener.start();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private class ControllerEventHandler implements EventHandler<KeyEvent> {
        //strzałki z jakiegoś powodu nie działają
        @Override
        public void handle(KeyEvent key){
                Move move = null;
                if (key.getCode() == KeyCode.W)
                    move = new Move(0, Direction.UP);
                else if (key.getCode() == KeyCode.S)
                    move = new Move(0, Direction.DOWN);
                else if (key.getCode() == KeyCode.D)
                    move = new Move(0, Direction.RIGHT);
                else if (key.getCode() == KeyCode.A)
                    move = new Move(0, Direction.LEFT);
                else if (key.getCode() == KeyCode.P)
                    move = new Move(true);
                else if (key.getCode() == KeyCode.N)
                    move = new Move(false);  
                    Socket client = null;
                    try {
                        client = new Socket("localhost", 6066);
                        ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
                        os.writeObject(move);
                    } catch (IOException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    InputStream inFromServer = null;
                    try {
                        inFromServer = client.getInputStream();
                        ObjectInputStream ois = new ObjectInputStream(inFromServer);
                        Map map = null;
                        try {
                            map = (Map) ois.readObject();
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        view.Display(map);
                    } catch (IOException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            inFromServer.close();
                        } catch (IOException ex) {
                            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
        }
    }
}