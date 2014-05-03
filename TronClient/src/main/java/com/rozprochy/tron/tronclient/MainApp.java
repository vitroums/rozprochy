package com.rozprochy.tron.tronclient;
import com.rozprochy.tron.troncommon.Direction;
import com.rozprochy.tron.troncommon.Map;
import com.rozprochy.tron.troncommon.Move;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        public int id = 1;
        
        //strzałki z jakiegoś powodu nie działają
        @Override
        public void handle(KeyEvent key) {
            
            Move move = null;
            
            if (key.getCode() == KeyCode.W) {
                System.out.println("Key Pressed: " + key.getCode());
                move = new Move(id, Direction.UP);
            } else if (key.getCode() == KeyCode.S) {
                System.out.println("Key Pressed: " + key.getCode());
                move = new Move(id, Direction.DOWN);
            } else if (key.getCode() == KeyCode.D) {
                System.out.println("Key Pressed: " + key.getCode());
                move = new Move(id, Direction.RIGHT);
            } else if (key.getCode() == KeyCode.A) {
                System.out.println("Key Pressed: " + key.getCode());
                move = new Move(id, Direction.LEFT);
            } 
             try {
                   Socket client = new Socket("localhost", 6066);
                //Socket client = new Socket("localhost", 6066);
                ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
                os.writeObject(move);
                InputStream inFromServer = client.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(inFromServer);

                Map map = null;
                try {
                    map = (Map) ois.readObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                MapView.Display(map);
            } catch (IOException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }

}
