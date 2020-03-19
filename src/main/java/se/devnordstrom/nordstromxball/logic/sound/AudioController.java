/*
 * Copyright (C) 2020 User
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.devnordstrom.nordstromxball.logic.sound;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author User
 */
public class AudioController 
{    
    private static boolean playSounds = false;
    
    private static final String[] BOUNCE_SOUND_NAMES = new String[]{"bounce_1.wav", 
            "bounce_2.wav", "bounce_3.wav"};
    
    public static void loadSound()
    {
        playBounceSound();
    }
    
    /**
     * AudioController.playBounceSound();
     */
    public static void playBounceSound()
    {
        int numberOfBounceSounds = BOUNCE_SOUND_NAMES.length;
        
        //Simple randomization
        int index = (int) (System.currentTimeMillis() % numberOfBounceSounds);
        String bounceSound = BOUNCE_SOUND_NAMES[index];
                        
        playSound(bounceSound);
    }
    
    public static void playBrickExplosionSound()
    {
        //Do nothing as of now.
    }
    
    public static void playSound(String soundName)
    {       
        String fullName = "sound/"+soundName;
        
        URL url = AudioController.class.getClassLoader().getResource(fullName);
        playSound(url);
    }
    
    public static void playSound(URL url) 
    {
        if(!playSounds) return;
        
        AudioInputStream ais = null;
        Clip clip = null;
        
        try {
            ais = AudioSystem.getAudioInputStream(url);

            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
            
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
                        
        }
    }
    
    public static void setPlaySounds(boolean newPlaySounds)
    {
        playSounds = newPlaySounds;
    }
    
    public static boolean shouldPlaySounds()
    {
        return playSounds;
    }
}