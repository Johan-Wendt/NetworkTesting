/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moverectanglenetwork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author johanwendt
 */
public class MoveRectangleNetwork extends Application implements MoveRectangleConstants{
    private int sessionNumber = 1;
    
    @Override
    public void start(Stage primaryStage) {
        TextArea log = new TextArea();
        
        Scene scene = new Scene(new ScrollPane(log), 450, 200);
        
        primaryStage.setTitle("move rectangle");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        new Thread( () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8016);
                Platform.runLater(() -> log.appendText(new Date() + ": ServerSocket started at socket 8000\n"));

                
                while(true) {
                    Platform.runLater(() -> log.appendText(new Date() + "Waiting for player to join session " + sessionNumber + '\n'));
                    Socket player = serverSocket.accept();
                    Platform.runLater(() -> log.appendText(new Date() + "Player joined session\n"));

                    new Thread(new HandleASession(player)).start();
                }
            }   
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
