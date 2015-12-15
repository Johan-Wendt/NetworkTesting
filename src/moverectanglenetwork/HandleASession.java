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
            
            toPlayer.writeInt(1);
            
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
        System.out.println("Trying to handle input");
        try {
            int nextInt = fromPlayer.readInt();
            System.out.println(nextInt);
            if(nextInt == 1) {
                
                isRight = true;
            }
            System.out.println("succes handle input");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("succes handle input");
    }

    private void upDateWolrd() {
        System.out.println("Update world");
        if(isRight) {
            xPosition ++;
        }
    }

    private void handleOutput() {
        try {
            System.out.println("x.Position from handle " + xPosition);
            toPlayer.writeInt(xPosition);
            System.out.println("Written");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
