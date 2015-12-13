/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moverectanglenetwork;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author johanwendt
 */
public class HandleASession implements Runnable {
    private Socket player;
    private DataInputStream fromPlayer;
    private DataOutputStream toPlayer;
    
    private boolean isRight;
    
    public static final int MOVE_RIGHT = 1;
    
    private int xPosition = 100;
    
    
    public HandleASession(Socket player) {
        this.player = player;
        
    }

    @Override
    public void run() {
        try {
            fromPlayer = new DataInputStream(player.getInputStream());
            toPlayer = new DataOutputStream(player.getOutputStream());
            
            toPlayer.write(1);
            
            while(true) {
                System.out.println("Called");
                handleOutput();
                handleInput(fromPlayer);
                upDateWolrd();
                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void handleInput(DataInputStream fromPlayer) {
        try {
            if(fromPlayer.readInt() == 1) {
                isRight = true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void upDateWolrd() {
        if(isRight) {
            xPosition ++;
        }
    }

    private void handleOutput() {
        try {
            toPlayer.writeInt(xPosition);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
