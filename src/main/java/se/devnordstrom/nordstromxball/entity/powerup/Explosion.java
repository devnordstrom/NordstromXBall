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
package se.devnordstrom.nordstromxball.entity.powerup;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashSet;
import se.devnordstrom.nordstromxball.entity.MovableEntity;

/**
 *
 * @author Orville Nordström
 */
public class Explosion extends MovableEntity
{   
    private static final int DEFAULT_WIDTH = 150;
    private static final int DEFAULT_HEIGHT = 75;
    
    private static final int OPACITY_RGBA = 255/2;
    private static final long DURATION_MS = 250;
    
    public static final Color DEFAULT_COLOR = Color.ORANGE;
    public static final Color FLASH_COLOR = new Color(255, 255, 255, OPACITY_RGBA);
    
    private int width, height;
    
    private long startTimeMs;
    
    private final HashSet hitObjects = new HashSet();
    
    public Explosion()
    {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
    }
    
    public void start()
    {        
        startTimeMs = System.currentTimeMillis();
    }
    
    /**
     * 
     * @return 
     */
    public boolean isActive()
    {
        return (startTimeMs+DURATION_MS) > System.currentTimeMillis();
    }
    
    @Override
    public void paint(Graphics g) 
    {    
        g.setColor(getColor());
        g.fillRect(getX(), getY(), width, height);
    }
    
    private Color getColor()
    {
        Color color = null;
        if(System.currentTimeMillis() % 200 >= 100) {
            color = DEFAULT_COLOR;
        } else {
            color = FLASH_COLOR;
        }
        return color;
    }
    
    public Rectangle getHitbox()
    {
        Rectangle hitbox = new Rectangle(getX(), getY(), width, height);
        
        return hitbox;
    }
    
    public void addHitObject(Object obj)
    {
        hitObjects.add(obj);
    }
    
    public boolean hasHitObject(Object obj)
    {
        return hitObjects.contains(obj);
    }
}