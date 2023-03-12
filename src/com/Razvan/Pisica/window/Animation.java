package com.Razvan.Pisica.window;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation {
    private int speed;
    private int frames;
    private int index=0;
    private int count=0;
    private BufferedImage[] images;
    private BufferedImage currentImg;

    public Animation(int speed, BufferedImage... args){
        this.speed=speed;
        images=new BufferedImage[args.length];
        for(int i=0;i<args.length;i++){
            images[i]=args[i];
        }
        frames= args.length;
    }

    public void runAnimation(){
        index++;
        if(index>speed){
            index=0;
            nextFrame();
        }
    }
    private void nextFrame(){
        for(int i=0;i<frames;i++){
            if(count==i)
                currentImg=images[i];
        }
        count++;
        if(count>frames){
            count=0;
        }
    }
    public BufferedImage returnCurrImg(){
        return currentImg;
    }
    public void drawAnimationEnemy(Graphics g, int x, int y){
        //g.drawImage(currentImg,x,y,null);
        g.drawImage(currentImg,(int)x+46,(int)y,-120,60,null);
    }
    public void drawAnimation2Enemy(Graphics g, int x, int y){
        //g.drawImage(currentImg,x,y,null);
        g.drawImage(currentImg,(int)x-40,(int)y,120,60,null);
    }
    public void drawAnimation(Graphics g, int x, int y){
         //g.drawImage(currentImg,x,y,null);
        g.drawImage(currentImg,(int)x+76,(int)y,-88,60,null);
    }
    public void drawAnimationChicken(Graphics g, int x, int y){
        //g.drawImage(currentImg,x,y,null);
        g.drawImage(currentImg,(int)x+50,(int)y,-50,50,null);
    }
    public void drawAnimationChicken2(Graphics g, int x, int y){
        //g.drawImage(currentImg,x,y,null);
        g.drawImage(currentImg,(int)x,(int)y,50,50,null);
    }
    public void drawAnimation2(Graphics g, int x, int y){
        //g.drawImage(currentImg,x,y,null);
        g.drawImage(currentImg,(int)x,(int)y,88,60,null);
    }
    public void drawAnimationAttack(Graphics g, int x, int y){
        g.drawImage(currentImg,(int)x+20,(int)y,-20,31,null);
    }
    public void drawAnimationAttack2(Graphics g, int x, int y){
        g.drawImage(currentImg,(int)x,(int)y,20,31,null);
    }
    public void drawAnimation(Graphics g, int x, int y,int scaleX, int scaleY){
        g.drawImage(currentImg,x,y,scaleX,scaleY,null);
    }

}
