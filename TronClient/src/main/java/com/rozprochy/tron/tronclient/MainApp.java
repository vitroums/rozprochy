package com.rozprochy.tron.tronclient;
import com.rozprochy.tron.troncommon.Direction;
import com.rozprochy.tron.troncommon.Move;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));

        Scene scene = new Scene(root);
        
        scene.setOnKeyPressed(new ControllerEventHandler());
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
    private static class ControllerEventHandler implements EventHandler<KeyEvent> {
  
        
        //strzałki z jakiegoś powodu nie działają
        @Override
        public void handle(KeyEvent key) {
            
            Move move = null;
            
            if (key.getCode() == KeyCode.W) {
                System.out.println("Key Pressed: " + key.getCode());
                move = new Move(0, Direction.UP);
            } else if (key.getCode() == KeyCode.S) {
                System.out.println("Key Pressed: " + key.getCode());
                move = new Move(0, Direction.DOWN);
            } else if (key.getCode() == KeyCode.D) {
                System.out.println("Key Pressed: " + key.getCode());
                move = new Move(0, Direction.RIGHT);
            } else if (key.getCode() == KeyCode.A) {
                System.out.println("Key Pressed: " + key.getCode());
                move = new Move(0, Direction.LEFT);
            } 
            
            MoveSendWorker moveSendWorker = new MoveSendWorker(move);
            Thread thread = new Thread(moveSendWorker);
            thread.start();;
            
        }
        
    }

}
