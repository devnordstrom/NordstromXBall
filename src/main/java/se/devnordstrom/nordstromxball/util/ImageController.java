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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author Orville N
 */
public class ImageController 
{
    /**
     * 
     */
    private static final String IMAGE_DIR = "img";
    
    /**
     * 
     */
    private static Map<String, BufferedImage> imgSet = new HashMap<>();
    
    /**
     * 
     */
    public static void load()
    {
        try {
            URL url = ImageController.class.getClassLoader().getResource(IMAGE_DIR);

            File imgDir = Paths.get(url.toURI()).toFile();
            String[] imgFiles = imgDir.list();
                
            for(String imgName : imgFiles) {
                BufferedImage image = readImageResource(imgName);
                
                imgSet.put(imgName, image);
            }
            
            System.out.println("ImageController: " + imgSet.keySet().size());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
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
     * 
     * @param imageName
     * @return
     * @throws IOException 
     */
    public static BufferedImage readImageResource(String imageName) throws IOException
    {
        if(imgSet.containsKey(imageName)) {
            return imgSet.get(imageName);
        }
        
        String fullName = IMAGE_DIR + File.separator + imageName;        
        URL imageUrl = Utils.class.getClassLoader().getResource(fullName);
        
        System.out.println("fullName: '"+fullName+"', imageUrl: '"+imageUrl+"'");
        
        BufferedImage image = ImageIO.read(imageUrl);
        
        imgSet.put(imageName, image);
        
        return image;
    }
}