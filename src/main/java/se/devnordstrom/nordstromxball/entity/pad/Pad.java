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
package se.devnordstrom.nordstromxball.entity.pad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;
import se.devnordstrom.nordstromxball.entity.ball.Ball;

/**
 *
 * @author Orville Nordström
 */
public class Pad implements PaintableEntity
{
    public static final Color DEFAULT_COLOR = Color.GREEN;
    
    public static final int DEFAULT_WIDTH = 50;
    public static final int DEFAULT_HEIGHT = 10;
    
    protected Color color;
    
    protected double x, y;

    protected int targetX, targetY, lastTargetX, lastTargetY, width, height;
        
    protected final Collection<Ball> attachedBalls;
    
    protected volatile boolean sticky, releaseAllAttachedBalls;
        
    public Pad()
    {
        color = DEFAULT_COLOR;
        
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        
        attachedBalls = new LinkedList<>();
        
        lastTargetX = -1;
        lastTargetY = -1;
    }
    
    public void reset()
    {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        
        this.setSticky(false);
    }
    
    @Override
    public void paint(Graphics g) 
    {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), width, height);
    }
    
    /**
     * Moves the pad to the position.
     * 
     * @param x 
     * @param y 
     */
    public void move(int x, int y)
    {
        //Dividing the width/height in half so the Pad will be centered.
        targetX = x - (width / 2);
        targetY = y - (height / 2);
        
        if(lastTargetX == -1) {
            lastTargetX = targetX;
        }
        
        if(lastTargetY == -1) {
            lastTargetY = targetY;
        }
    }
    
    /**
     * This method will instantly move the pad to the target x.
     * 
     * @param delta 
     */
    @Override
    public void move(double delta)
    {
        int targetX = getTargetX();
        setX(targetX);
        
        //This implementation can only move horizontally and instantly so targetY is ignored.
        
        moveAttachedBalls();
        
        lastTargetX = targetX;
    }
    
    /**
     * @return the color
     */
    public Color getColor() 
    {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) 
    {
        this.color = color;
    }
    
    /**
     * @return the targetX
     */
    public int getTargetX() 
    {
        return targetX;
    }

    /**
     * @return the targetY
     */
    public int getTargetY() 
    {
        return targetY;
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
    
    public boolean isSticky()
    {
        return sticky;
    }
    
    public void setSticky(boolean sticky)
    {
        this.sticky = sticky;
    }
    
    public Rectangle getHitbox()
    {
        Rectangle hitbox = new Rectangle((int) x, (int) y, width, height);
        
        return hitbox;
    }
    
    public Point getPadLaunchPoint()
    {
        int centerPointX = (int) Math.round(x + (width/2.0));
        int centerPointY = (int) Math.round(y + (width/4.0));
        
        Point centerPoint = new Point(centerPointX, centerPointY);
        return centerPoint;
    }
    
    public void attachBall(Ball ball)
    {
        ball.setAttached(true);
        attachedBalls.add(ball);
    }
    
    public void moveAttachedBalls()
    {   
        if(releaseAllAttachedBalls) {
            doReleaseAttachedBalls();
            releaseAllAttachedBalls = false;
            return;
        }
        
        int targetXDifference = targetX - lastTargetX;
        
        Collection<Ball> balls = new ArrayList<>(attachedBalls);
        
        for(Ball ball : balls) {
            int newBallX = ball.getX() + targetXDifference; 
            
            ball.setX(newBallX);
        }
    }
        
    /**
     * Settings this variable instead so as to be thread safe since
     * this may be called from another thread.
     */
    public void releaseBalls()
    {
        releaseAllAttachedBalls = true;
    }
    
    private void doReleaseAttachedBalls()
    {
        for(Ball ball : attachedBalls) {
            ball.setAttached(false);
            ball.launchFromPad(this);
        }
        
        attachedBalls.clear();
    }
}