package com.Razvan.Pisica.window;

import com.Razvan.Pisica.Exceptions.NullPointerException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BufferedImageLoader {
    private BufferedImage image;

    public BufferedImage loadImage(String path){
        try {
            image= ImageIO.read(getClass().getResource(path));
            if(image==null){
                throw new NullPointerException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return image;
    }
}
