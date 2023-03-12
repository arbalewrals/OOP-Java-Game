package com.Razvan.Pisica.objects;

import com.Razvan.Pisica.framework.GameObject;
import com.Razvan.Pisica.framework.ObjectId;
import com.Razvan.Pisica.framework.Texture;
import com.Razvan.Pisica.window.Game;

import java.awt.*;
import java.util.LinkedList;


public class Block extends GameObject {
    Texture tex= Game.getInstance();
    private int type;
    public Block(float x, float y,int type, ObjectId id) {
        super(x, y, id);this.type=type;
    }

    public void tick(LinkedList<GameObject> object) {

    }
    public void render(Graphics g) {
        if(type==0)//iarba mijloc 255 255 255
            g.drawImage(tex.block[0],(int)x,(int)y,32,32,null);
        if(type==1)//piatra mijloc 0 0 127
            g.drawImage(tex.block[1],(int)x,(int)y,32,32,null);
        if(type==2)//iarba piatra colt dreapta 127 0 0
            g.drawImage(tex.block[2],(int)x,(int)y,32,32,null);
        if(type==3)//iarba piatra colt stanga 255 0 0
            g.drawImage(tex.block[3],(int)x,(int)y,32,32,null);
        if(type==4)//iarba piatra perete 0 255 255
            g.drawImage(tex.block[4],(int)x,(int)y,32,32,null);
        if(type==5)//piatra 255 255 0
            g.drawImage(tex.block[5],(int)x,(int)y,32,32,null);
        if(type==6)//piatra perete dreapta 0 127 0
            g.drawImage(tex.block[6],(int)x,(int)y,32,32,null);

    }
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }
    
}
