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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;
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
    private static boolean playSounds = true;
    
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
    
    private static final BlockingQueue<String> soundQueue = new LinkedBlockingQueue<>();
    
    private static Thread audioThread;
    
    public static void loadSound()
    {   
        try {                   
            loadSoundKindMap();
                
            loadAudioThread();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
        
    private static void loadSoundKindMap()
    {
        soundKindMap.put(BOUNCE_WALL, 
                "bounce_wall_1.wav,bounce_wall_2.wav,bounce_wall_3.wav");
        soundKindMap.put(BOUNCE_PAD, 
                "bounce_pad_1.wav,bounce_pad_2.wav,bounce_pad_3.wav");
        soundKindMap.put(BREAK_BRICK, 
                "break_brick_1.wav,break_brick_2.wav,break_brick_3.wav");
        soundKindMap.put(EXPLOSION, 
                "explosion_1.wav,explosion_2.wav,explosion_3.wav");
        soundKindMap.put(PLAYER_LOST_LIFE, 
                "player_lost_life.wav");
        soundKindMap.put(POWERUP_ACTIVATED, 
                "powerup_activated_1.wav,powerup_activated_2.wav,powerup_activated_3.wav");
        soundKindMap.put(POWERUP_KILL_SPAWN, 
                "powerup_kill_spawn_1.wav,powerup_kill_spawn_2.wav,powerup_kill_spawn_3.wav");
        soundKindMap.put(POWERUP_SPAWN, 
                "powerup_spawn_1.wav,powerup_spawn_2.wav,powerup_spawn_3.wav");
    }
    
    private static void loadAudioThread()
    {
        if(audioThread != null) 
            throw new IllegalStateException("The audioThread must not be null!");
        
        audioThread = new Thread (()-> {
            while(true) {
                try {
                    String soundKind = soundQueue.take();
                                        
                    doPlaySoundKind(soundKind);
                    Thread.sleep(1);
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        audioThread.setName("Audio Thread");
        
        audioThread.start();
    }
    
    public static void playSoundKind(String soundName)
    {
        if(soundQueue.contains(soundName)) return;
        
        soundQueue.add(soundName);
    }
    
    public static void doPlaySoundKind(String soundName)
    {   
        String entries = soundKindMap.get(soundName);
        if(entries == null) {
            System.err.println("playSoundKind('"+soundName+"')"
                    + " returning because the sound was null!");
            return;
        }
        
        String[] soundEntries = entries.split(",");
        
        int index = (int) System.currentTimeMillis() % soundEntries.length;
        
        String soundEntry = soundEntries[index];
                
        playSound(soundEntries[index]);
    }

    public static void playSound(String soundName)
    {       
        String fullName = SOUND_DIR+"/"+soundName;
                        
        URL url = AudioController.class.getClassLoader().getResource(fullName);
        playSound(url);
    }
    
    public static void playSound(URL url) 
    {
        if(!playSounds) return;
        
        try {            
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);

            Clip clip = AudioSystem.getClip();
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