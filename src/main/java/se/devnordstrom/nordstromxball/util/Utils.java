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
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class Utils 
{
    /**
     * 
     * @param imageName
     * @return
     * @throws IOException 
     */
    public static BufferedImage readImageResource(String imageName) throws IOException
    {
        String fullName = "img/"+imageName;
        URL imageUrl = Utils.class.getClassLoader().getResource(fullName);
        BufferedImage image = ImageIO.read(imageUrl);
        return image;
    }
    
    /**
     * Shows an Information message using JOptionPane.showMessageDialog.
     * @param text
     * @param title 
     */
    public static void showMessage(String text, String title) {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * 
     * @param text
     * @param title 
     */
    public static void showErrorMesage(String text, String title) {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
    }
}