package com.Razvan.Pisica.objects;

//import com.Razvan.Pisica.DataBase.Database;
import com.Razvan.Pisica.framework.GameObject;
import com.Razvan.Pisica.framework.ObjectId;
import com.Razvan.Pisica.framework.Texture;
import com.Razvan.Pisica.window.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import static com.Razvan.Pisica.window.Game.level2;
import static com.Razvan.Pisica.window.Game.playState;

public class Player extends GameObject {
    public static boolean win=false;
    //public Database db;
    private float width=76,height=60;
    private float gravity=0.5f;
    private final float MAX_SPEED=10;
    public static int health=6;
    private int facing=1;
    private boolean invincibility=false;
    private int invincibilityFrame=0;
    private int attackingFrame=0;
    private int diedFrames=0;
    public static int Score=0;
    public static boolean dead=false;
    public static boolean isDead(){
        if(health<=0){
            return true;
        }
        return false;
    }
    public static  void addHealth(int amount){
        health+=amount;
    }
    public static void setScore(int amount){
        Score+=amount;
    }
    public static int getHealth(){
        return health;
    }
    public boolean isInvincible(){
        return invincibility;
    }
    //1=dreapta
    //-1=stanga
    private Handler handler;
    Texture tex= Game.getInstance();
    private Animation playerRun;
    private Animation playerIdle;
    private Animation playerWalk;
    private Animation playerAttack;
    private float prevY;
    private boolean isAttacking = false;
    public static boolean canJump = false;
    public static boolean canAttack = true;

    public int damage = 1;
    public Player(float x, float y, ObjectId id) {
        super(x, y, id);
        prevY = y;
        playerWalk=new Animation(10,tex.player[11],tex.player[12],tex.player[13],tex.player[14],tex.player[15],tex.player[16]);
        playerRun=new Animation(3,tex.player[1],tex.player[2],tex.player[3],tex.player[4],tex.player[5],tex.player[6]);
        playerIdle=new Animation(100,tex.player[0],tex.player[8],tex.player[9],tex.player[10]);
        playerAttack=new Animation(10,tex.attack[0],tex.attack[1]);
        //db=new Database(this);
    }
    public void tick(LinkedList<GameObject> object) {
        prevY = y;
        x+=velX;
        y+=velY;
        canJump = y - prevY <= 0.5;
        if(y>1000){
            health=0;
        }
        if(velX<0) facing=-1;
        else if(velX>0) facing=1;
        if(falling||jumping){
            velY+=gravity;
            if(velY>MAX_SPEED)
                velY=MAX_SPEED;
        }
        Collision(object);
        if(isInvincible()){
           invincibilityFrame++;
        }
        if(invincibilityFrame==180){
            invincibility=false;
            invincibilityFrame=0;
        }
        if(!canAttack){
            attackingFrame++;
        }

        if(attackingFrame==30){
            isAttacking=false;
            canAttack=true;
            attackingFrame=0;
        }
        if(health<=0){
            dead=true;
            win=false;
            //db.saveToDB();
            //db.loadFromDB();
            Game.gameState=Game.deadState;
            Score=0;
            Game.resetGame();

            health=6;

        }
        if(getScore()==450){
            Game.gameState=Game.level2State;
            Score=500;
            health=6;
        }
        if(getScore()==1250){
            Game.gameState=Game.endState;
            Score=1300;
            win=true;
            //db.saveToDB();
            //db.loadFromDB();

        }
        playerRun.runAnimation();
        playerIdle.runAnimation();
        playerWalk.runAnimation();
        playerAttack.runAnimation();
    }
    public static int getScore(){
        return Score;
    }
    private void Collision(LinkedList<GameObject>object){
        for(int i=0;i<Handler.object.size();i++){
            GameObject tempObject=Handler.object.get(i);
            if(tempObject.getId()==ObjectId.Block){

                if(getBounds().intersects(tempObject.getBounds())) {//Dreapta
                    if(velX>0){
                        //velX=0;
                        x=tempObject.getX()-width;
                    }else if(velX<0){//Stanga
                        //velX=0;
                        x=tempObject.getX()+32;
                    }
                }

                if(getBounds2().intersects(tempObject.getBounds())) {//jos
                    if(velY>0){
                        y=tempObject.getY()-height;
                        velY=0;
                        falling=false;
                        jumping=false;

                    }
                    else if(velY<0){//sus
                        y=tempObject.getY()+32;
                        velY=0;
                    }
                    falling=true;
                }



                    /*if(getBounds().intersects(tempObject.getBounds())){
                    y=tempObject.getY()-height;
                    velY=0;
                    falling=false;
                    jumping=false;
                }else{
                    falling=true;
                }
                if(getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX()-width;
                }
                if(getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX()+tempObject.getBounds().width;

                }*/
            }
        }
        for(int i=0;i<Handler.object.size();i++){
            GameObject tempObject=Handler.object.get(i);
            if(tempObject.getId()==ObjectId.Enemy){
                if(getBounds().intersects(tempObject.getBounds())) {//Dreapta
                    if(velX>0){
                        if(!isInvincible()){
                            health--;
                            invincibility=true;
                        }
                    }else if(velX<0){//Stanga
                        if(!isInvincible()){
                            health--;
                            invincibility=true;
                        }                    }
                }

                if(getBounds2().intersects(tempObject.getBounds())) {//jos
                    if(velY>0){
                        if(!isInvincible()){
                            health--;
                            invincibility=true;
                        }
                    }
                    else if(velY<0){//sus
                        if(!isInvincible()){
                            health--;
                            invincibility=true;
                        }                    }
                    falling=true;
                }

            }
        }
        /*for(int i=0;i<handler.object.size();i++){
            GameObject tempObject=handler.object.get(i);
            if(tempObject.getId()==ObjectId.Enemy){

                if(getBounds().intersects(tempObject.getBounds())) {//Dreapta
                    if(velX>0){
                        //velX=0;
                        x=tempObject.getX()-86;
                    }else if(velX<0){//Stanga
                        //velX=0;
                        x=tempObject.getX()+86;
                    }
                }

                if(getBounds2().intersects(tempObject.getBounds())) {//jos
                    if(velY>0){
                        y=tempObject.getY()-58;
                        velY=0;
                        falling=false;
                        jumping=false;

                    }
                    else if(velY<0){//sus
                        y=tempObject.getY()+58;
                        velY=0;
                    }
                    falling=true;
                }

                    *//*if(getBounds().intersects(tempObject.getBounds())){
                    y=tempObject.getY()-height;
                    velY=0;
                    falling=false;
                    jumping=false;
                }else{
                    falling=true;
                }
                if(getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX()-width;
                }
                if(getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX()+tempObject.getBounds().width;

                }*//*
            }
        }*/
    }
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        if(velX!=0){

            if(facing==1&&!isSprinting()){
                playerWalk.drawAnimation(g,(int)x,(int)y);
            }else if(facing==1&&isSprinting()){
                playerRun.drawAnimation(g,(int)x,(int)y);
            }
            else if(!isSprinting()){
                playerWalk.drawAnimation2(g,(int)x,(int)y);
            }else if(isSprinting()){
                playerRun.drawAnimation2(g,(int)x,(int)y);
            }

        }
        else{
            if(facing==1){
                g.drawImage(tex.player[0],(int)x+76,(int)y,-76,60,null);
                //playerIdle.drawAnimation(g,(int)x,(int)y);
            }
            else
            {
               // playerIdle.drawAnimation2(g,(int)x,(int)y);
                g.drawImage(tex.player[0],(int)x,(int)y,76,60,null);
            }
        }
        if(isAttacking) {
            if (facing == 1) {
                g.drawImage(tex.attack[0],((int) getBoundsAttacking().x)-20,(int) getBoundsAttacking().y,60,90,null);
                //playerAttack.drawAnimationAttack2(g, (int) getBoundsAttacking().x, (int) getBoundsAttacking().y);
            } else {
                g.drawImage(tex.attack[0],((int) getBoundsAttacking().x)-20,(int) getBoundsAttacking().y,60,90,null);
                //playerAttack.drawAnimationAttack(g, (int) getBoundsAttacking().x, (int) getBoundsAttacking().y);
            }
            /*Graphics2D g2d = (Graphics2D) g;
            g.setColor(Color.RED);
            g2d.draw(getBounds());
            g.setColor(Color.BLUE);
            g2d.draw(getBounds2());
            g.setColor(Color.BLUE);
            g2d.draw(getBoundsAttacking());*/
        /*
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsLeft());
        g2d.draw(getBoundsTop());*/
        }
    }
    public Rectangle getBounds() {
        float bx=x+velX+1;
        float by=y;
        float bw=76+velX/2-10+7;
        float bh=50;
        return new Rectangle((int)bx,(int)by,(int)bw,(int)bh);
    }
    public Rectangle getBounds2(){
        float bx=x+8;
        float by=y+velY;
        float bw=60;
        float bh=60+velY/2;
        return new Rectangle((int)bx,(int)by,(int)bw,(int)bh);
    }
    public Rectangle getBoundsAttacking(){
        float bx=x+80;
        float by=y;
        float bw=30;
        float bh=60+velY/2;
        if(facing==1)
        return new Rectangle((int)bx + 10,(int)by,(int)bw,(int)bh);
        return new Rectangle((int) (bx-100-bw),(int)by,(int)bw,(int)bh);
    }
    /*
public Rectangle getBounds() {
        return new Rectangle((int)((int)x+(width/2)-((width/2)/2))-25,(int)((int)y+(height)/2),(int)width-70,(int)height/2);
    }
    public Rectangle getBoundsTop() {
        return new Rectangle((int)((int)x+(width/2)-((width/2)/2))-25,(int)y,(int)width-70,(int)height/2);
    }
    public Rectangle getBoundsRight() {
        return new Rectangle((int)((int)x+width-59),(int)y+5,(int)5,(int)height-10);
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle((int)x,(int)y+5,(int)5,(int)height-10);
    }
 */

    public void setAttacking(boolean attacking) {
        if(canAttack){
            isAttacking = attacking;
            canAttack=false;
        }
        else isAttacking=false;
    }
    public boolean getAttacking(){
        return isAttacking;
    }
}
