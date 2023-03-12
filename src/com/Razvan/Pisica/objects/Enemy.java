package com.Razvan.Pisica.objects;

import com.Razvan.Pisica.framework.GameObject;
import com.Razvan.Pisica.framework.ObjectId;
import com.Razvan.Pisica.framework.Texture;
import com.Razvan.Pisica.window.Animation;
import com.Razvan.Pisica.window.BufferedImageLoader;
import com.Razvan.Pisica.window.Game;
import com.Razvan.Pisica.window.Handler;
import org.w3c.dom.Text;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Enemy extends GameObject {
    private float width=43,height=29;
    private float gravity=0.5f;
    private final float MAX_SPEED=10;
    private int facing=1;
    private int health=3;
    private boolean dead=false;
    private boolean isHit=false;
    private int invincibilityFrame=0;
    private int hitFrame=0;

    private boolean invincibility=false;
    private int actionLockCounter=0;
    //1=dreapta
    //-1=stanga
    Texture tex= Game.getInstance();
    private Animation enemyRun;
    private Animation enemyIdle;
    public boolean isDead(){
        if(health<=0){
            dead=true;
        }
        else dead=false;

        return dead;
    }
    public Enemy(float x, float y, ObjectId id) {
        super(x, y, id);
        enemyRun=new Animation(3,tex.enemy[1],tex.enemy[2],tex.enemy[3],tex.enemy[4],tex.enemy[5]);
        //enemyIdle=new Animation(100,tex.player[0],tex.player[8],tex.player[9],tex.player[10]);
    }
    public void tick(LinkedList<GameObject> object) {
        x+=velX;
        y+=velY;
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
        if(invincibilityFrame==60){
            invincibility=false;
            invincibilityFrame=0;
        }
        for(Iterator<GameObject> iterator = Handler.object.iterator(); iterator.hasNext();) {
                GameObject i = iterator.next();
                //i.update(player, thisFrame, monsterArray);
                if (i.getId()==ObjectId.Enemy&&((Enemy)i).isDead()) {
                    Player.setScore(100);
                    iterator.remove();
            }

        }
        //randMove();
        move();

        enemyRun.runAnimation();
        //enemyIdle.runAnimation();
    }
    private void move(){
        if(facing==1)
            setVelX(3);
        else
            setVelX(-3);
    }
    private void randMove(){
        if(actionLockCounter == 0){
            Random random = new Random();
            int j = random.nextInt(100)+1;

            if(j<=50){
                System.out.println("dreapta");
                facing=1;
                setVelX(3);
            }
            else{
                System.out.println("stanga");
                facing=-1;
                setVelX(-3);
            }
        }

        actionLockCounter++;

        if(actionLockCounter == 10)
            actionLockCounter = 0;
    }
    private void Collision(LinkedList<GameObject>object){
        for(int i=0;i<Handler.object.size();i++){
            GameObject tempObject=Handler.object.get(i);
            if(tempObject.getId()==ObjectId.Block){

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
                    /*else if(velY<0){//sus
                        y=tempObject.getY()+32;
                        velY=0;
                    }*/
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
            if(tempObject.getId()==ObjectId.Border){

                if(getBounds().intersects(tempObject.getBounds())) {//Dreapta
                    if(velX>0){
                        //velX=0;
                        x=tempObject.getX()-86;
                        facing=-1;
                        setVelX(-3);
                    }else if(velX<0){//Stanga
                        //velX=0;
                        x=tempObject.getX()+86;
                        facing=1;
                        setVelX(3);
                    }
                }

            }
        }
        for(int i=0;i<Handler.object.size();i++){
            GameObject tempObject=Handler.object.get(i);
            if(tempObject.getId()==ObjectId.Player){
                if(getBounds().intersects(((Player) tempObject).getBoundsAttacking()) && !isInvincible() && ((Player) tempObject).getAttacking()) {//Dreapta
                    health -= ((Player) tempObject).damage;
                    invincibility=true;
                    isHit=true;
                    System.out.println("Hit");
                }
                else   isHit=false;
                if(getBounds2().intersects(((Player) tempObject).getBoundsAttacking()) && !isInvincible() && ((Player) tempObject).getAttacking()) {//Dreapta
                    health -= ((Player) tempObject).damage;
                    invincibility=true;
                    isHit=true;
                    System.out.println("Hit");
                }
                else   isHit=false;
            }

        }

    }

    private boolean isInvincible() {
        return invincibility;
    }

    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        if(velX!=0){

            if(facing==1){
                //g.drawImage(tex.enemy[0],(int)x+43,(int)y,-86,58,null);
                enemyRun.drawAnimationEnemy(g,(int)x,(int)y);
                /*BufferedImage aux=enemyRun.returnCurrImg();
                if(aux==null){
                    BufferedImageLoader loader=new BufferedImageLoader();
                    aux=loader.loadImage("block.png");
                }
                BufferedImage aux2=Texture.deepCopy(aux);
                aux=Texture.tint(aux2);
                if(isHit){
                    g.drawImage(aux,(int)x+43,(int)y,-120,58,null);
                }*/
                //enemyRun.drawAnimation(g,(int)x,(int)y);
            }else if(facing==-1){
                enemyRun.drawAnimation2Enemy(g,(int)x,(int)y);
                /*BufferedImage aux=enemyRun.returnCurrImg();
                if(aux==null){
                    BufferedImageLoader loader=new BufferedImageLoader();
                    aux=loader.loadImage("block.png");
                }
                BufferedImage aux2=Texture.deepCopy(aux);
                aux=Texture.tint(aux2);
                if(isHit){
                    g.drawImage(aux,(int)x-43,(int)y,120,58,null);
                }*/
            }
        }
        else{
            if(facing==1){
                g.drawImage(tex.enemy[0],(int)x+43,(int)y,-86,58,null);
                //playerIdle.drawAnimation(g,(int)x,(int)y);
            }
            else
            {
                // playerIdle.drawAnimation2(g,(int)x,(int)y);
                g.drawImage(tex.enemy[0],(int)x,(int)y,86,58,null);
            }
        }

        /*Graphics2D g2d=(Graphics2D) g;
        g.setColor(Color.RED);
        g2d.draw(getBounds());
        g.setColor(Color.BLUE);
        g2d.draw(getBounds2());*/
        /*
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsLeft());
        g2d.draw(getBoundsTop());*/
    }
    public Rectangle getBounds() {
        //float bx=x+velX+1-38;
        float bx=x+velX+1-38-10;
        float by=y+5;
        float bw=80+velX/2+15;
        float bh=53;
        return new Rectangle((int)bx,(int)by,(int)bw,(int)bh);
    }
    public Rectangle getBounds2(){
        float bx=x+2-43;
        float by=y+velY;
        float bw=86;
        float bh=60+velY/2;
        return new Rectangle((int)bx,(int)by,(int)bw,(int)bh);
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
}
