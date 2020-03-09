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
package se.devnordstrom.nordstromxball.entity;

import java.awt.Rectangle;

/**
 *
 * @author Orville N
 */
public abstract class MovableEntity implements PaintableEntity
{
    protected double x, y, xSpeed, ySpeed;
    
    @Override
    public void move(double delta) 
    {
        this.x += getxSpeed() * delta;
        this.y += getySpeed() * delta;      
    }
    
    public double getxSpeed()
    {
        return xSpeed;
    }
    
    public double getySpeed()
    {
        return ySpeed;
    }
    
    /**
     * @return the x
     */
    public int getX() 
    {
        return (int) Math.round(x);
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) 
    {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() 
    {
        return (int) Math.round(y);
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) 
    {
        this.y = y;
    }

    /**
     * @param xSpeed the xSpeed to set
     */
    public void setxSpeed(double xSpeed) 
    {
        this.xSpeed = xSpeed;
    }

    /**
     * @param ySpeed the ySpeed to set
     */
    public void setySpeed(double ySpeed) 
    {
        this.ySpeed = ySpeed;
    }
    
    public boolean isMovingToTheRight() 
    {
        return getxSpeed() > 0;
    }
    
    public boolean isMovingToTheLeft()
    {
        return getxSpeed() < 0;
    }
    
    public boolean isMovingDown()
    {
        return getySpeed() > 0;
    }
    
    public boolean isMovingUp()
    {
        return getySpeed() < 0;
    }
}