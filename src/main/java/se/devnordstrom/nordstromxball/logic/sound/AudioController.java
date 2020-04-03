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

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
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
    /*
        Sound effects needed,
        
        When bouncing against a pad
    
        When bouncing against a wall
    
        When breaking a brick
    
        When a good powerup is spawned
    
        When a negative powerup is spawned
    
        When a powerup is activated.
    
        When an explosion occurs
    */
    
    private static boolean playSounds = true;
    
    private static final String[] BOUNCE_SOUND_NAMES = new String[]{"bounce_1.wav", 
            "bounce_2.wav", "bounce_3.wav"};
    
    public static final String SOUND_DIR = "sound";
    
    public static final String BOUNCE_WALL = "bounce_wall";
    public static final String BOUNCE_PAD = "bounce_pad";
    public static final String BREAK_BRICK = "break_brick";
    public static final String EXPLOSION = "explosion";
    public static final String PLAYER_LOST_LIFE = "player_lost_life";
    public static final String POWERUP_ACTIVATED = "powerup_activated";
    public static final String POWERUP_KILL_SPAWN = "powerup_kill_spawn";
    public static final String POWERUP_SPAWN = "powerup_spawn";
    
    public static final Map<String, String> soundKindMap = new HashMap<String, String>();
    
    public static String[] SOUND_NAMES = new String[]{
            BOUNCE_WALL, BOUNCE_PAD, BREAK_BRICK,
            EXPLOSION, PLAYER_LOST_LIFE, POWERUP_ACTIVATED,
            POWERUP_KILL_SPAWN, POWERUP_SPAWN
    };
    
    public static void loadSound()
    {
        long startTimeMs = System.currentTimeMillis();
                        
        try {
            URL url = AudioController.class.getClassLoader().getResource(SOUND_DIR);

            File soundDir = Paths.get(url.toURI()).toFile();
            
            String[] soundFiles = soundDir.list();
            
            for(String soundName : SOUND_NAMES) {
                String entry = "";

                for(String soundFile : soundFiles) {
                    if(soundFile.startsWith(soundName)) {
                        if(entry.isEmpty()) {
                            entry = soundFile;
                        } else {
                            entry += "," + soundFile;
                        }
                    }
                }
                
                soundKindMap.put(soundName, entry);
            }
            
            long execTimeMs = System.currentTimeMillis()-startTimeMs;
            System.out.println("loadSound() now finished! ExecTimeMS: "+execTimeMs);
            System.out.println("soundKindMap:");
            System.out.println(soundKindMap);
            
        } catch(Exception ex) {
            ex.printStackTrace();
        }
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
    
    public static void playSoundKind(String soundName)
    {
        String entries = soundKindMap.get(soundName);
        if(entries == null) {
            System.err.println("playSoundKind(...)"
                    + " returning because the sound was null!");
            return;
        }
        
        String[] soundEntries = entries.split(",");
        
        int index = (int) System.currentTimeMillis() % soundEntries.length;
        
        playSound(soundEntries[index]);
    }
            
            
    public static void playSound(String soundName)
    {       
        String fullName = SOUND_DIR+File.separator+soundName;
        
        System.out.println("playSound('"+fullName+"')");
        
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