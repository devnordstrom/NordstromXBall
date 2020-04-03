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
package se.devnordstrom.nordstromxball.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class Utils 
{
    /**
     * Shows an Information message using JOptionPane.showMessageDialog.
     * @param text
     * @param title 
     */
    public static void showMessage(String text, String title) 
    {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * 
     * @param text
     * @param title 
     */
    public static void showErrorMesage(String text, String title) 
    {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * 
     * @param targetFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static Object unserialize(File targetFile) throws FileNotFoundException, 
            IOException, ClassNotFoundException
    {
        if(targetFile == null)
            throw new NullPointerException();
        
        if(targetFile.isDirectory())
            throw new IllegalArgumentException("The targetFile must not be a directory!");
        
        FileInputStream fis = new FileInputStream(targetFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object unserializedObject = ois.readObject();
        
        fis.close();
        ois.close();
        
        return unserializedObject;
    }
    
    /**
     * 
     * @param targetFile
     * @param object
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void serialize(File targetFile, Object object) throws FileNotFoundException, IOException
    {
        if(targetFile == null)
            throw new NullPointerException();
        
        if(targetFile.isDirectory())
            throw new IllegalArgumentException("The targetFile must not be a directory!");
        
        if(object == null)
            throw new IllegalArgumentException("The object must not be null!");
        
        if(!(object instanceof Serializable))
            throw new IllegalArgumentException("The object must be an instance of serializable.");
        
        FileOutputStream fos = new FileOutputStream(targetFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        
        oos.writeObject(object);
        
        fos.close();
        oos.close();
    }
}