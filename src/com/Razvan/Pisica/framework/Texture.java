package com.Razvan.Pisica.framework;

import com.Razvan.Pisica.Exceptions.IllegalArgumentException;
import com.Razvan.Pisica.Exceptions.NullPointerException;
import com.Razvan.Pisica.window.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Texture {
    SpriteSheet bs,ps,es,es2,bd,ck,at;
    private BufferedImage block_sheet=null;
    private BufferedImage player_sheet=null;
    private BufferedImage wolf_sheet=null;
    private BufferedImage wolf_sheet2=null;
    private BufferedImage border_block=null;
    private BufferedImage chicken_sheet=null;
    private BufferedImage attacking_sheet=null;
    public BufferedImage[] block=new BufferedImage[10];
    public BufferedImage[] player=new BufferedImage[18];
    public BufferedImage[] enemy=new BufferedImage[6];
    public BufferedImage[] border=new BufferedImage[1];
    public BufferedImage[] chicken=new BufferedImage[4];
    public BufferedImage[] attack=new BufferedImage[2];
    public Texture(){
        BufferedImageLoader loader=new BufferedImageLoader();
        try {

            block_sheet=loader.loadImage("/oak_woods_tileset.png");
            player_sheet=loader.loadImage("/catspritesx4.png");
            wolf_sheet=loader.loadImage("/wolf_sit.png");
            wolf_sheet2=loader.loadImage("/wolf_run.png");
            border_block=loader.loadImage("/block.png");
            chicken_sheet=loader.loadImage("/chicken_walk.png");
            attacking_sheet=loader.loadImage("/claw 2.png");
        }catch (Exception e){
            e.printStackTrace();
        }
        bs=new SpriteSheet(block_sheet);
        ps=new SpriteSheet(player_sheet);
        es=new SpriteSheet(wolf_sheet);
        es2=new SpriteSheet(wolf_sheet2);
        bd=new SpriteSheet(border_block);
        ck=new SpriteSheet(chicken_sheet);
        at=new SpriteSheet(attacking_sheet);
        getTextures();
    }
    private void getTextures(){
        block[0]=bs.grabImage(2,1,24,24);//iarba mijloc
        block[1]=bs.grabImage(7,1,24,24);//piatra mijloc
        block[2]=bs.grabImage(4,1,24,24);//iarba piatra colt dreapta
        block[3]=bs.grabImage(1,1,24,24);//iarba piatra colt stanga
        block[4]=bs.grabImage(4,2,24,24);//iarba piatra perete
        block[5]=bs.grabImage(13,1,24,24);//piatra
        block[6]=bs.grabImage(1,2,24,24);//piatra perete dreapta
        player[0]=ps.grabImage(1,1,76,60);//Matza idle
        player[1]=ps.grabImage(1,3,80,60);//Matza fuge
        player[2]=ps.grabImage(2,3,80,60);//Matza fuge
        player[3]=ps.grabImage(3,3,80,60);//Matza fuge
        player[4]=ps.grabImage(4,3,80,60);//Matza fuge
        player[5]=ps.grabImage(5,3,80,60);//Matza fuge
        player[6]=ps.grabImage(6,3,80,60);//Matza fuge
        player[7]=ps.grabImage(1,1,80,60);//matza idle
        player[8]=ps.grabImage(2,1,84,60);//Matza idle animation
        player[9]=ps.grabImage(3,1,84,60);//Matza idle animation
        player[10]=ps.grabImage(4,1,84,60);//Matza idle animation
        player[11]=ps.grabImage(1,2,88,60);//Matza walk
        player[12]=ps.grabImage(2,2,88,60);//Matza walk
        player[13]=ps.grabImage(3,2,88,60);//Matza walk
        player[14]=ps.grabImage(4,2,88,60);//Matza walk
        player[15]=ps.grabImage(5,2,88,60);//Matza walk
        player[16]=ps.grabImage(6,2,88,60);//Matza walk
        enemy[0]=es.grabImage(1,1,43,29);
        enemy[1]=es2.grabImage(1,1,53,26);
        enemy[2]=es2.grabImage(2,1,53,26);
        enemy[3]=es2.grabImage(3,1,53,26);
        enemy[4]=es2.grabImage(4,1,53,26);
        enemy[5]=es2.grabImage(5,1,53,26);
        border[0]=bd.grabImage(1,1,24,24);
        chicken[0]=ck.grabImage(1,1,32,26);
        chicken[1]=ck.grabImage(2,1,32,26);
        chicken[2]=ck.grabImage(3,1,32,26);
        chicken[3]=ck.grabImage(4,1,32,26);
        attack[0]=at.grabImage(1,1,30,30);
        attack[1]=at.grabImage(2,1,30,30);

    }
    public static BufferedImage tint(BufferedImage img) {
        //inroseste spriteul
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                Color pixelColor = new Color(img.getRGB(x, y), true);
                int r = pixelColor.getRed();
                int g = (int) (pixelColor.getGreen()/1.6);
                int b = (int) (pixelColor.getBlue()/1.6);
                int a = pixelColor.getAlpha();
                int rgba = (a << 24) | (r << 16) | (g << 8) | b;
                img.setRGB(x, y, rgba);
            }
        }
        return img;
    }
    public static BufferedImage deepCopy(BufferedImage source) {
        //face o copie in intregime a unei poze pentru a nu fi modificata la toti monstri/ toate obiectele simultan
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }
}
