package com.Razvan.Pisica.window;

import com.Razvan.Pisica.Exceptions.NullPointerException;
import com.Razvan.Pisica.Exceptions.UnsupportedAudioFileException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    static Clip clip;
    static URL soundURL[]=new URL[30];

    public Sound(){
        soundURL[0]=getClass().getResource("/soundtrack joc.wav");
    }
    public void setFile(){
        try{
            AudioInputStream ais= AudioSystem.getAudioInputStream(soundURL[0]);
            clip=AudioSystem.getClip();
            if(clip==null){
                throw new UnsupportedAudioFileException();
            }
            clip.open(ais);
        }catch (Exception e){

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
