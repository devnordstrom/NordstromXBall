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
package se.devnordstrom.nordstromxball.entity.brick;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;
import se.devnordstrom.nordstromxball.entity.powerup.Powerup;

/**
 *
 * @author Orville Nordström
 */
public class Brick implements PaintableEntity
{
    public static final Color DEFAULT_COLOR = Color.GREEN;
    public static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
    
    public static final int DEFAULT_WIDTH = 50;
    public static final int DEFAULT_HEIGHT = 25;
    public static final int DEFAULT_SCORE = 50;
    
    
    protected Color brickColor, borderColor;
    
    protected int x, y, width, height, points;
    
    protected boolean destroyed, indestructable, visible;
    
    public Brick()
    {
        this(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public Brick(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.points = DEFAULT_SCORE;
        this.visible = true;
        
        brickColor = DEFAULT_COLOR;
        borderColor = DEFAULT_BORDER_COLOR;
    }
    
    @Override
    public void paint(Graphics g)
    {
        if(isDestroyed() || !isVisible()) return;
        
        Color color = getColor();
        if(color != null) {
            g.setColor(getColor());
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }
        
        
        Color borderColor = getBorderColor();
        if(borderColor != null) {
            g.setColor(getBorderColor());
            g.drawRect(getX(), getY(), getWidth(), getHeight());   
        }
    }
    
    public Rectangle getHitBox()
    {
        if(isDestroyed()) return null;
        
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    public void setColor(Color color)
    {
        this.brickColor = color;
    }
    
    public Color getColor()
    {
        return brickColor;
    }
    
    public void setBorderColor(Color borderColor)
    {
        this.borderColor = borderColor;
    }
    
    public Color getBorderColor()
    {
        return borderColor;
    }
    
    public int getPoints()
    {
        return points;
    }
    
    public Powerup getPowerUp()
    {
        return null;
    }
    
    public boolean hasPowerUp()
    {
        return false;
    }
    
    public boolean isDestroyed()
    {
        return destroyed;
    }
    
    public boolean isIndestructable()
    {
        return this.indestructable;
    }
    
    public void setIndestructable(boolean indestructable)
    {
        this.indestructable = indestructable;
    }
    
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    
    public boolean isVisible()
    {
        return visible;
    }
    
    public int hit()
    {
        destroyed = true;
        return this.getPoints();
    }
    
    /**
     * @return the x
     */
    public int getX() 
    {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) 
    {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() 
    {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) 
    {
        this.y = y;
    }

    /**
     * @return the width
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) 
    {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() 
    {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) 
    {
        this.height = height;
    }

    public Point getCenterPoint()
    {
        int centerX = (int) Math.round((width/2.0) + x);
        int centerY = (int) Math.round((height/2.0) + y);
        
        Point centerPoint = new Point();
        centerPoint.setLocation(centerX, centerY);
        return centerPoint;
    }
    
    @Override
    public void move(double delta) 
    {
        //This implementation can't move.
    }
}