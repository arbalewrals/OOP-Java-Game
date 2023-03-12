package com.Razvan.Pisica.framework;

import com.Razvan.Pisica.objects.Player;
import com.Razvan.Pisica.window.Game;
import com.Razvan.Pisica.window.Handler;
import com.Razvan.Pisica.window.UI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
    Handler handler;
    public KeyInput(Handler handler){
        this.handler=handler;
    }
    private boolean[] keys = new boolean[4];
    private boolean space = false;
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(Game.gameState==Game.titleState){
            if (key == KeyEvent.VK_W){
                UI.commandNum--;
                if(UI.commandNum<0){
                    UI.commandNum=2;
                }
            }
            if(key==KeyEvent.VK_S){
                UI.commandNum++;
                if(UI.commandNum>2){
                    UI.commandNum=0;
                }
            }
            if(key==KeyEvent.VK_ENTER){
                if(UI.commandNum==0){
                    Game.gameState=Game.playState;
                    if(Game.gameState==Game.playState){
                        Game.playMusic();
                        Game.soundOn=1;
                    }
                }
                if(UI.commandNum==2){
                    System.exit(0);
                }
            }


        }
        //Play state
        if(Game.gameState==Game.playState){
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getId() == ObjectId.Player) {

                    if (key == KeyEvent.VK_A) {
                        keys[0] = true;
                        tempObject.setVelX(-3);
                    }
                    if(key==KeyEvent.VK_SHIFT){
                        keys[3]=true;
                        tempObject.setSprinting(true);
                    }
                    if (key == KeyEvent.VK_D) {
                        keys[1] = true;

                        tempObject.setVelX(3);
                    }
                    if(key==KeyEvent.VK_W&&!tempObject.isJumping()&& Player.canJump){
                        keys[2]=true;
                        tempObject.setJumping(true);
                        tempObject.setVelY(-10);
                    }
                    if(keys[3]&&keys[1]&&!tempObject.isJumping()){
                        tempObject.setVelX(5);
                    }
                    if(keys[3]&&keys[0]&&!tempObject.isJumping()){
                        tempObject.setVelX(-5);
                    }
                    if(key==KeyEvent.VK_SPACE){
                        space = true;
                    }
                    ((Player) tempObject).setAttacking(space);
                }
            }
        }


    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();
        for(int i=0;i<handler.object.size();i++){
            GameObject tempObject=handler.object.get(i);
            if(tempObject.getId()==ObjectId.Player){
                if (key == KeyEvent.VK_A)
                {
                    keys[0] = false;
                }
                if(key==KeyEvent.VK_SHIFT){
                    keys[3]=false;
                    tempObject.setSprinting(false);
                }
                if (key == KeyEvent.VK_D)
                {
                    keys[1] = false;
                }

                if (!keys[0] && !keys[1])
                {
                    tempObject.setVelX(0);
                }
                if (!keys[3]&&keys[1])
                {
                    tempObject.setVelX(3);
                }
                if(!keys[3]&&keys[0]){
                    tempObject.setVelX(-3);
                }
                if (!keys[0] && !keys[1]&&!keys[3])
                {
                    tempObject.setVelX(0);
                }
                if(key==KeyEvent.VK_SPACE){
                    space = false;
                }
                ((Player) tempObject).setAttacking(space);
            }
        }

    }
}
