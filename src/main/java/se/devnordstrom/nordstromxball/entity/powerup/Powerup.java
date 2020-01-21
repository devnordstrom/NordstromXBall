/*
 * Copyright (C) 2019 Orville Nordström
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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import se.devnordstrom.nordstromxball.entity.MovableEntity;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;

/**
 *
 * @author Orville Nordström
 */
public class Powerup extends MovableEntity
{   
    public static final int DEFAULT_WIDTH = 25;
    public static final int DEFAULT_SPEED = 40;
    public static final int DEFAULT_POINTS = 500;
    
    //public static final Font DEFAULT_FONT = new Font("Tahoma", Font.BOLD, DEFAULT_WIDTH);
    
    private int diameter, points;
    
    private PowerupKind powerUpKind;
    
    public Powerup()
    {
        this(0, 0);
    }
    
    public Powerup(int x, int y)
    {
        diameter = DEFAULT_WIDTH;
        points = DEFAULT_POINTS;
        
        powerUpKind = PowerupKind.DEFAULT;
        
        setX(x);
        setY(y);
        
        this.setxSpeed(0);
        this.setySpeed(DEFAULT_SPEED);
    }
    
    
    
    @Override
    public void paint(Graphics g) 
    {        
        Color powerUpColor;
        switch(getPowerUpKind()) {
            case REVEAL_INVISIBLE:
                powerUpColor = new Color(139, 0, 139);
                break;
            case SPLIT_BALLS:
                powerUpColor = new Color(25, 25, 25);
                break;
            case DOUBLE_SPEED:
                powerUpColor = Color.ORANGE;
                break;
            case HALF_SPEED:
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
            case DEFAULT:
            default:
                powerUpColor = Color.RED;
        }
        
        
        
        Color color = Color.WHITE;
        if((System.currentTimeMillis() % 200) <= 100) {
            color = powerUpColor;
        }
        
        g.setColor(color);
        g.fillRect(getX(), getY(), diameter, diameter);
        
        
        g.setColor(Color.BLACK);
        g.fillRect(getX()+5, getY()+5, diameter-10, diameter-10);
        
        
        
        
        
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
}