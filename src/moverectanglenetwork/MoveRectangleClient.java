/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moverectanglenetwork;

/**
 *
 * @author johanwendt
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author johanwendt
 */
public class MoveRectangleClient extends Application {
    
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    
    private Rectangle rectangle = new Rectangle(20, 20);
    
    private int movingDirection = 1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        connectToServer();
        
        rectangle.setFill(Color.RED);
        rectangle.setTranslateY(200);
        pane.getChildren().add(rectangle);
        
        scene.setOnKeyPressed(e -> {
            System.out.println("Pressed");
            movingDirection = 1;
        });
        scene.setOnKeyReleased(e -> {
            movingDirection = 1;
        });
    
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 8016);
            
            fromServer = new DataInputStream(socket.getInputStream());
            
            toServer = new DataOutputStream(socket.getOutputStream());
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
    
        new Thread(() -> {
            while(true) {
                
                try {
                    int apa = fromServer.readInt();
                    System.out.println("From server " + apa);
                    setPostion(apa);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    changeDirection();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }).start();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void setPostion(int readInt) {
        System.out.println(readInt);
        rectangle.setTranslateX(readInt);
    }
    private void changeDirection() throws IOException {
        System.out.println("Changing direction" + movingDirection);
        toServer.write(movingDirection);
    }
}
