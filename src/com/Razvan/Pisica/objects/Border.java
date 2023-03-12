package com.Razvan.Pisica.objects;

import com.Razvan.Pisica.framework.GameObject;
import com.Razvan.Pisica.framework.ObjectId;
import com.Razvan.Pisica.framework.Texture;
import com.Razvan.Pisica.window.Game;

import java.awt.*;
import java.util.LinkedList;


public class Border extends GameObject {
    Texture tex= Game.getInstance();
    private int type;
    public Border(float x, float y,int type, ObjectId id) {
        super(x, y, id);this.type=type;
    }

    public void tick(LinkedList<GameObject> object) {

    }
    public void render(Graphics g) {
        if(type==0)//border
            g.drawImage(tex.border[0],(int)x,(int)y,32,32,null);
    }
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }

}
