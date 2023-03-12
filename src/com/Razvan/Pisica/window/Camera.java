package com.Razvan.Pisica.window;

import com.Razvan.Pisica.framework.GameObject;

public class Camera {
    private float x,y;
    public Camera(float x, float y){
        this.x=x;
        this.y=y;
    }
    public void setX(float x){
        this.x=x;
    }
    public void setY(float y){
        this.y=y;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public void tick(GameObject player){
        x=-player.getX()+Game.WIDTH/2;
        if(x>0)
            x=0;
        /*y=-player.getY()+Game.HEIGHT/2;
        if(y>0)
            y=0;
        */

    }
}
