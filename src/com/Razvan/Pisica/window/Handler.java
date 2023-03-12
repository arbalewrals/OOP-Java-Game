package com.Razvan.Pisica.window;

import com.Razvan.Pisica.framework.GameObject;
import com.Razvan.Pisica.framework.ObjectId;
import com.Razvan.Pisica.objects.Block;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

public class Handler {
    static public LinkedList<GameObject> object= new LinkedList<GameObject>();
    private GameObject tempObject;
    public void tick(){
        for(int i=0; i< object.size();i++){
            tempObject=object.get(i);
            tempObject.tick(object);
        }
    }
    public void render(Graphics g){
        for(int i=0; i< object.size();i++){
            tempObject=object.get(i);
            tempObject.render(g);
        }
    }
    public void clearLevel(){
        object.clear();
    }
    public static void addObject(GameObject object){
        Handler.object.add(object);
    }
    public static void removeObject(GameObject object){
        Handler.object.remove(object);
    }
    public static void removeAllObj(){
        for(Iterator<GameObject> iterator = object.iterator(); iterator.hasNext();){
            GameObject i = iterator.next();
            iterator.remove();
        }
/*        for(Iterator<GameObject> iterator = Handler.object.iterator(); iterator.hasNext();){
            GameObject i = iterator.next();
            //i.update(player, thisFrame, monsterArray);
            if(i.dead){
                iterator.remove();
            }

          public void moare(){
          dead = true;
          Player.score += 100;
          }
          public void isHit(){
          knockback = true;
          obj.img.b.tint();
          }
        }*/
    }

}
