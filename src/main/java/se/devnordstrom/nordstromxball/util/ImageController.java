/*
 * Copyright (C) 2020 Orville N
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
package se.devnordstrom.nordstromxball.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author Orville Nordström
 */
public class ImageController 
{
    /**
     * 
     */
    private static final String EXTENSION = "png";
    
    /**
     * 
     */
    private static final String IMAGE_DIR = "img";
    
    /**
     * 
     */
    private static final String BALL_DIR = "ball";
    
    /**
     * 
     */
    private static final String BRICK_DIR = "brick";
    
    /**
     * 
     */
    private static final String POWERUP_DIR = "powerup";
    
    /**
     * 
     */
    private static Map<String, BufferedImage> imgSet = new HashMap<>();
    
    private static final String[] BALL_RESOURCES = new String[]{
        "ball_default",
    };
    
    private static final String[] BRICK_RESOURCES = new String[]{
        "brick_ball_brick", "brick_default", 
        "brick_steel_brick", "brick_sticky_ball_brick", 
        "brick_tough_brick _hit", "brick_tough_brick"
    };
    
    private static final String[] POWERUP_RESOURCES = new String[] {
            "powerup_bigger_pad", "powerup_decreased_speed", 
            "powerup_extra_life", "powerup_increased_speed", 
            "powerup_kill_player", "powerup_reveal_invisible",
            "powerup_smaller_pad", "powerup_split_balls", 
            "powerup_sticky_pad"
    };
    
    
    /**
     * Loads the image resources used so that they can be
     * quickly retrieved later on.
     */
    public static void load()
    {
        try {
            List<String> resources = listAllResources();
            
            for(String resource : resources) {
                loadImageResource(resource);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static List<String> listAllResources()
    {
        List<String> resources = new ArrayList<>();
        
        for(String ballResource : BALL_RESOURCES) {
            String fullName = BALL_DIR + "/" + ballResource + "." + EXTENSION;
            resources.add(fullName);
        }
        
        for(String brickResource : BRICK_RESOURCES) {
            String fullName = BRICK_DIR + "/" + brickResource + "." + EXTENSION;
            resources.add(fullName);
        }
        
        for(String powerupResource : POWERUP_RESOURCES) {
            String fullName = POWERUP_DIR + "/" + powerupResource + "." + EXTENSION;
            resources.add(fullName);
        }
                
        return resources;
    }
    
    /**
     * 
     * @param imageName
     * @return 
     */
    public static BufferedImage quietReadImageResource(String imageName)
    {
        try {
            return readImageResource(imageName);
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * This calls readImageResource which will buffer the image.
     * 
     * @param imageName 
     */
    private static void loadImageResource(String imageName) throws IOException
    {
        readImageResource(imageName);
    }
    
    /**
     * 
     * @param imageName
     * @return
     * @throws IOException 
     */
    public static BufferedImage readImageResource(String imageName) throws IOException
    {
        if(imageName == null) 
            throw new NullPointerException();
        
        
        if(imgSet.containsKey(imageName)) {
            return imgSet.get(imageName);
        }
        
        String fullName = IMAGE_DIR + "/" + imageName;
                
        URL imageUrl = Utils.class.getClassLoader().getResource(fullName);
                
        BufferedImage image = ImageIO.read(imageUrl);
        
        imgSet.put(imageName, image);
        
        return image;
    }
}