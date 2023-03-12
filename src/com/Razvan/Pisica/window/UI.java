package com.Razvan.Pisica.window;

import com.Razvan.Pisica.objects.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI extends Game{
    static Graphics2D g2;
    static Font titlu;
    public static int commandNum=0;
    public UI(Graphics2D g2){
        try {
            InputStream is =getClass().getResourceAsStream("/galacticagrid.regular.ttf");
            titlu=Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
    }
    public static void setGraphics(Graphics2D g2){
        UI.g2=g2;
        try {
            InputStream is = UI.class.getResourceAsStream("/galacticagrid.regular.ttf");
            titlu=Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
    }
    public static void drawScore(Graphics2D g2d){
        g2.setColor(Color.WHITE);
        g2.setFont(titlu);
        String text= "Score: " + String.valueOf(Player.getScore());
        g2.setFont(g2.getFont().deriveFont(Font.TRUETYPE_FONT,20F));
        g2.drawString(text,50,100);
    }
    public static void drawDeadScreen(){
        g2.setFont(titlu);
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0,800,600);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);
        String text="You Died";
        int x=60;
        int y=200;
        //Umbra la text
        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);
        //Poza Pisica
        x=Game.WIDTH/2;
        int yPisica=250;
        BufferedImage pisica_poza=null;
        BufferedImage[] pisica=new BufferedImage[1];
        BufferedImageLoader loader=new BufferedImageLoader();
        pisica_poza=loader.loadImage("/pisica.png");
        g2.drawImage(pisica_poza,x,yPisica,304,240,null);
    }
    public static void endScreen(){
        g2.setFont(titlu);
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0,800,600);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);
        String text="You Win";
        int x=60;
        int y=200;
        //Umbra la text
        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);
        //Poza Pisica
        x=Game.WIDTH/2;
        int yPisica=250;
        BufferedImage pisica_poza=null;
        BufferedImage[] pisica=new BufferedImage[1];
        BufferedImageLoader loader=new BufferedImageLoader();
        pisica_poza=loader.loadImage("/pisica.png");
        g2.drawImage(pisica_poza,x,yPisica,304,240,null);
    }
    public static void drawTitleScreen(){
        //Nume Titlu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));

        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0,800,600);

        String text="Pisica Curajoasa";
        int x=20;
        int y=100;
        //Umbra la text
        g2.setColor(Color.BLUE);
        g2.drawString(text,x+5,y+5);
        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);
        //Poza Pisica
        x=Game.WIDTH/2;
        int yPisica=250;
        BufferedImage pisica_poza=null;
        BufferedImage[] pisica=new BufferedImage[1];
        BufferedImageLoader loader=new BufferedImageLoader();
        pisica_poza=loader.loadImage("/pisica.png");
        g2.drawImage(pisica_poza,x,yPisica,304,240,null);

        //Meniu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
        text="NEW GAME";
        x=80;
        y+=80;
        g2.drawString(text,x,y);
        if(commandNum==0){
            g2.drawString("-",x-40,y);
        }
        text="LOAD GAME";
        x=80;
        y+=80;
        g2.drawString(text,x,y);
        if(commandNum==1){
            g2.drawString("-",x-40,y);
        }
        text="QUIT";
        x=80;
        y+=80;
        g2.drawString(text,x,y);
        if(commandNum==2){
            g2.drawString("-",x-40,y);
        }
    }
    public static void draw(Graphics2D g2){
        g2.setFont(titlu);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);
        if(Game.gameState==Game.titleState){
            UI.drawTitleScreen();
        }

    }
}
