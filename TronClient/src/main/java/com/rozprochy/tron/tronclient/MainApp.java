package com.rozprochy.tron.tronclient;

import com.rozprochy.tron.tronclient.model.*;
import com.rozprochy.tron.tronclient.view.GameView;
import com.rozprochy.tron.troncommon.Direction;
import com.rozprochy.tron.troncommon.Move;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends Application {
    private final int x = 640;
    private final int y = 480;
    private ConcurrentLinkedQueue<Move> moves = new ConcurrentLinkedQueue<Move>();
    private final int unit = 10;
    private final int offset = 20;
    
    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(x, y);
        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, x, y);
        scene.setOnKeyPressed(new ControllerEventHandler(moves));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Tron");
        primaryStage.setScene(scene);
        primaryStage.show();
        GameView view = new GameView(canvas, unit, x, y ,offset);
        Thread loopTh = new Thread(new ClientGameLoop(view, moves));
        loopTh.start();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private class ControllerEventHandler implements EventHandler<KeyEvent> {
        
        private ConcurrentLinkedQueue<Move> moves;
        
        public ControllerEventHandler(ConcurrentLinkedQueue<Move> moves) {
            this.moves = moves;
        }
        
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
            
            if(move != null)
                moves.add(move);
        }
    }
    
}