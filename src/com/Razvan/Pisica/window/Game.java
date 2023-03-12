package com.Razvan.Pisica.window;

import com.Razvan.Pisica.framework.GameObject;
import com.Razvan.Pisica.framework.KeyInput;
import com.Razvan.Pisica.framework.ObjectId;
import com.Razvan.Pisica.framework.Texture;
import com.Razvan.Pisica.objects.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    private boolean running=false;
    public static int WIDTH,HEIGHT;
    private Thread thread;
    public static int gameState;
    static Sound sound=new Sound();
    public static int soundOn=0;
    public static final int titleState=0;
    public static final int playState=1;
    public static final int deadState=2;
    public static final int level2State=3;
    public static final int endState=4;
    public static int diedFrames=0;
    public static int winFrames=0;
    public static BufferedImage level1=null;
    public static BufferedImage level2=null;
    private BufferedImage background=null;
    private boolean gameStarted = false;
    Handler handler;
    static Texture tex;
    Camera cam;
    public synchronized void start(){
        if(running){
            return;
        }
        running=true;
        thread=new Thread(this);
        thread.start();
        gameState=titleState;
    }
    private void init(){
        WIDTH=getWidth();
        HEIGHT=getHeight();
        tex=new Texture();
        BufferedImageLoader loader=new BufferedImageLoader();
        handler=new Handler();
        cam=new Camera(0,0);
        //handler.addObject(new Player(100,100,handler,ObjectId.Player));
       // handler.createLevel();

        this.addKeyListener(new KeyInput(handler));
        background=loader.loadImage("/background.png");
        level1=loader.loadImage("/level.png");
        level2=loader.loadImage("/level2.png");
        LoadImageLevel(level1);

    }
    public static void playMusic(){
        sound.setFile();
        sound.play();
        sound.loop();
    }
    public static void stopMusic(){
        sound.stop();
    }
    public void run(){
        init();
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(running){
            if(gameState == playState) {
                if(!gameStarted) {

                    gameStarted = true;
                }
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                while (delta >= 1) {
                    tick();
                    updates++;
                    delta--;
                }


                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    System.out.println("FPS:" + frames + "Ticks:" + updates);
                    frames = 0;
                    updates = 0;
                }
            }
            render();
            /*if(frames>120)
                frames=120;*/
        }
    }
    private void tick(){
        handler.tick();
        for(int i=0;i<handler.object.size();i++){
            if(handler.object.get(i).getId()==ObjectId.Player)
                cam.tick(handler.object.get(i));
            }
    }

    private void render(){
        BufferStrategy bs=this.getBufferStrategy();
        if(bs==null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g= bs.getDrawGraphics();
        Graphics2D g2d=(Graphics2D) g;
        UI.setGraphics(g2d);
        //////Draw//////
        //g.setColor(Color.black);
        g.fillRect(0,0,getWidth(),getHeight());

        /////Draw///////
        //BufferedImageLoader loader= new BufferedImageLoader();
        if(gameState==endState){
            UI.endScreen();
            winFrames++;
        }
        if(winFrames==1000){
            gameState=titleState;
            winFrames=0;
            resetGame();
            Player.Score=0;
            Player.health=6;
        }
        if(gameState==level2State){
            resetGame2();
            gameState=playState;
        }
        if(gameState==deadState){
            UI.drawDeadScreen();
            diedFrames++;
        }
        if(diedFrames==1000){
            gameState=titleState;
            diedFrames=0;
        }
        if(gameState==titleState){
            UI.draw(g2d);
        }
        else if(gameState==playState){
            g2d.translate(cam.getX(), cam.getY());

            for (int i=0;i<800*15;i+= 800){
                g.drawImage(background,i,0,800,600,null);
            }
            handler.render(g);

            g2d.translate(-cam.getX(),-cam.getY());

            UI.drawScore(g2d);
            if(Player.getHealth()==6){
                BufferedImage viata=null;
                BufferedImageLoader loader=new BufferedImageLoader();
                viata=loader.loadImage("/6 health.png");
                g.drawImage(viata,50,20,110,37,null);
            }
            if(Player.getHealth()==5){
                BufferedImage viata=null;
                BufferedImageLoader loader=new BufferedImageLoader();
                viata=loader.loadImage("/5 health.png");
                g.drawImage(viata,50,20,110,37,null);
            }
            if(Player.getHealth()==4){
                BufferedImage viata=null;
                BufferedImageLoader loader=new BufferedImageLoader();
                viata=loader.loadImage("/4 health.png");
                g.drawImage(viata,50,20,110,37,null);
            }
            if(Player.getHealth()==3){
                BufferedImage viata=null;
                BufferedImageLoader loader=new BufferedImageLoader();
                viata=loader.loadImage("/3 health.png");
                g.drawImage(viata,50,20,110,37,null);
            }
            if(Player.getHealth()==2){
                BufferedImage viata=null;
                BufferedImageLoader loader=new BufferedImageLoader();
                viata=loader.loadImage("/2 health.png");
                g.drawImage(viata,50,20,110,37,null);
            }
            if(Player.getHealth()==1){
                BufferedImage viata=null;
                BufferedImageLoader loader=new BufferedImageLoader();
                viata=loader.loadImage("/1 health.png");
                g.drawImage(viata,50,20,110,37,null);
            }

        }
        if(gameState==titleState&&soundOn==1){
            stopMusic();
            soundOn=0;
        }
        g.dispose();
        bs.show();

    }

    public static void LoadImageLevel(BufferedImage levelx){
        BufferedImage image = levelx;
        int w=image.getWidth();
        int h=image.getHeight();
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                int pixel=image.getRGB(i,j);
                int red=(pixel>>16)&0xff;
                int green=(pixel>>8)&0xff;
                int blue=(pixel)&0xff;
                if(red==255&&green==255&&blue==255) Handler.addObject(new Block(i*32,j*32,0,ObjectId.Block));
                if(red==0&&green==255&&blue==255) Handler.addObject(new Block(i*32,j*32,4,ObjectId.Block));
                if(red==255&&green==0&&blue==0) Handler.addObject(new Block(i*32,j*32,3,ObjectId.Block));
                if(red==127&&green==0&&blue==0) Handler.addObject(new Block(i*32,j*32,2,ObjectId.Block));
                if(red==255&&green==255&&blue==0) Handler.addObject(new Block(i*32,j*32,5,ObjectId.Block));
                if(red==0&&green==127&&blue==0) Handler.addObject(new Block(i*32,j*32,6,ObjectId.Block));
                if(red==0&&green==0&&blue==127) Handler.addObject(new Block(i*32,j*32,1,ObjectId.Block));
                if(red==0&&green==0&&blue==255) Handler.addObject(new Player(i*32,j*32,ObjectId.Player));
                if(red==255&&green==60&&blue==255) Handler.addObject(new Enemy(i*32,j*32,ObjectId.Enemy));
                if(red==0&&green==255&&blue==100) Handler.addObject(new Border(i*32,j*32,0,ObjectId.Border));
                if(red==127&&green==127&&blue==127) Handler.addObject(new Chicken(i*32,j*32,ObjectId.Chicken));

            }
        }
    }
    public static void resetGame(){
        Handler.removeAllObj();
        LoadImageLevel(level1);
    }
    public static void resetGame2(){
        Handler.removeAllObj();
        LoadImageLevel(level2);
    }
    public static Texture getInstance(){
        return tex;
    }
    public static void main(String args[]){
        new Window(800,600,"Pisica Curajoasa",new Game());
    }
}
