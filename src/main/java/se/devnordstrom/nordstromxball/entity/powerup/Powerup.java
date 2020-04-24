/*
 * Copyright (C) 2020 Orville Nordström
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
package se.devnordstrom.nordstromxball.entity.powerup;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import se.devnordstrom.nordstromxball.entity.MovableEntity;
import se.devnordstrom.nordstromxball.util.ImageController;
import se.devnordstrom.nordstromxball.util.Utils;

/**
 *
 * @author Orville Nordström
 */
public class Powerup extends MovableEntity
{   
    public static final int DEFAULT_DIAMETER = 25;
    public static final int DEFAULT_SPEED = 200;
    public static final int DEFAULT_POINTS = 500;
    
    private int diameter, points;
    
    private PowerupKind powerUpKind;
    
    public Powerup()
    {
        this(0, 0);
    }
    
    public Powerup(int x, int y)
    {
        diameter = DEFAULT_DIAMETER;
        points = DEFAULT_POINTS;
        
        powerUpKind = PowerupKind.RANDOM;
        
        setX(x);
        setY(y);
        
        this.setxSpeed(0);
        this.setySpeed(DEFAULT_SPEED);
    }
    
    
    
    @Override
    public void paint(Graphics g) 
    {   
        BufferedImage img = getImage();
        
        if(img != null) {
            g.drawImage(img, getX(), getY(), null);
        } else {
            paintWithColors(g);
        }
    }

    private void paintWithColors(Graphics g)
    {
        Color fillColor = getPowerupFillColor();
        Color powerUpColor = getPowerupColor();
        
        if(isPossitive()) {
            g.setColor(powerUpColor);
            g.fillRect(getX(), getY(), diameter, diameter);
            
            g.setColor(fillColor);
            g.fillRect(getX()+5, getY()+5, diameter-10, diameter-10);
        } else {
            Color badColor = new Color(powerUpColor.getRed(), 
                    powerUpColor.getGreen(), powerUpColor.getBlue(), (int)(255*0.75));
            
            g.setColor(badColor);
            g.fillRect(getX(), getY(), diameter, diameter);
        }   
    }
    
    private BufferedImage getImage()
    {
        String imageName = "powerup/powerup_"
                + getPowerUpKind().toString().toLowerCase()+".png";
        
        return ImageController.quietReadImageResource(imageName);
    }
    
    private Color getPowerupFillColor()
    {
        return Color.BLACK;
    }
    
    private Color getPowerupColor()
    {
        if(isPossitive() 
                && (System.currentTimeMillis() % 200) <= 100) {
            return Color.WHITE;
        }
        
        Color powerUpColor;
        switch(getPowerUpKind()) {
            case REVEAL_INVISIBLE:
                powerUpColor = new Color(139, 0, 139);
                break;
            case SPLIT_BALLS:
                powerUpColor = new Color(25, 25, 25);
                break;
            case INCREASED_SPEED:
                powerUpColor = Color.ORANGE;
                break;
            case DECREASED_SPEED:
                powerUpColor = Color.MAGENTA;
                break;
            case STICKY_PAD:
                powerUpColor = Color.GRAY;
                break;
            case EXTRA_LIFE:
                powerUpColor = Color.GREEN;
                break;
            case BIGGER_PAD:
                powerUpColor = Color.YELLOW;
                break;
            case SMALLER_PAD:
                powerUpColor = new Color(204, 204, 0);
                break;
            case KILL_PLAYER:
                int red = 56, green = 128, blue = 4;
                
                if(System.currentTimeMillis() % 200 < 100) {
                    green = 148;
                }
                
                powerUpColor = new Color(red, green, blue);
                break;
            default:
                powerUpColor = Color.RED;
        }
        
        return powerUpColor;
    }
    
    @Deprecated
    private boolean isPossitive()
    {
        switch(getPowerUpKind()) {
            case KILL_PLAYER:
            case SMALLER_PAD:
            case INCREASED_SPEED:
            case RANDOM:
                return false;
            default:
                return true;
        }
    }
    
    public Rectangle getHitbox()
    {
        Rectangle hitbox = new Rectangle(getX(), getY(), getDiameter(), getDiameter());
        return hitbox;
    }
    
    /**
     * @return the diameter
     */
    public int getDiameter() 
    {
        return diameter;
    }

    /**
     * @param diameter the diameter to set
     */
    public void setDiameter(int diameter) 
    {
        this.diameter = diameter;
    }

    /**
     * @return the powerUpKind
     */
    public PowerupKind getPowerUpKind() 
    {
        return powerUpKind;
    }

    /**
     * @param powerUpKind the powerUpKind to set
     */
    public void setPowerUpKind(PowerupKind powerUpKind) 
    {
        this.powerUpKind = powerUpKind;
    }
    
    /**
     * @return the points
     */
    public int getPoints() 
    {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(int points) 
    {
        this.points = points;
    }
    
    /**
     * Returns the string represantation of the powerup.
     * 
     * @return 
     */
    @Override
    public String toString()
    {   
        String text = powerUpKind.toString();
        text = text.replace('_', ' ');
        text += "("+getPoints()+"+)";

        return text;        
    }
}