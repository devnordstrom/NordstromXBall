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
package se.devnordstrom.nordstromxball.entity.ball;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;
import se.devnordstrom.nordstromxball.entity.brick.Brick;
import se.devnordstrom.nordstromxball.entity.pad.Pad;

/**
 *
 * @author Orville Nordström
 */
public class Ball implements PaintableEntity 
{
    public static final int DEFAULT_DIAMETER = 10;

    public static final double DEFAULT_SPEED = 300;

    public static final Color DEFAULT_COLOR = Color.GREEN;

    protected double x, y, xDistanceLastMove, yDistanceLastMove, 
            xSpeedMod, ySpeedMod, speed;
    
    protected static double highestXDistanceLastMove, highestYDistanceLastMove;
    
    protected int xDir, yDir, diameter;

    protected Color color;

    protected boolean vital, lethal, attached;
    
    public Ball() 
    {
        this.xDir = 1;
        this.yDir = 1;
        this.diameter = DEFAULT_DIAMETER;
        this.speed = DEFAULT_SPEED;
        this.color = DEFAULT_COLOR;
        
        this.xSpeedMod = Math.sin(45);
        this.ySpeedMod = Math.sin(45);
    }
    
    @Override
    public void paint(Graphics g) 
    {
        Color color = getColor();
        
        if(color != null) {
            g.setColor(color);
            g.fillOval(getX(), getY(), diameter, diameter);
            //g.fillRect((int) getX(), (int) getY(), diameter, diameter);   
        }
        
        /*
            Rectangle xHitBox = this.getXHitbox();
            Rectangle yHitBox = this.getYHitbox();

            g.setColor(Color.WHITE);
            g.fillRect((int) xHitBox.getX(), (int) xHitBox.getY(), (int) xHitBox.getWidth(), (int) xHitBox.getHeight());
            g.fillRect((int) yHitBox.getX(), (int) yHitBox.getY(), (int) yHitBox.getWidth(), (int) yHitBox.getHeight());
        */
    }

    @Override
    public void move(double delta) 
    {        
        if(isAttached()) {
            return;
        }
        
        xDistanceLastMove = speed * xSpeedMod * getxDir() * delta; 
        yDistanceLastMove = speed * ySpeedMod * getyDir() * delta;
        
        if(highestXDistanceLastMove < xDistanceLastMove) {
            highestXDistanceLastMove = xDistanceLastMove;
        }
        
        if(highestYDistanceLastMove < yDistanceLastMove) {
            highestYDistanceLastMove = yDistanceLastMove;
        }
        
        this.x += xDistanceLastMove;
        this.y += yDistanceLastMove;
    }

    public int getX() 
    {
        return (int) Math.round(x);
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public int getY() 
    {
        return (int) Math.round(y);
    }

    public void setY(double y)
    {
        this.y = y;
    }

    /**
     * @return the xDir
     */
    public int getxDir() 
    {
        return xDir;
    }

    public boolean isMovingToTheRight() 
    {
        return xDir == 1;
    }

    public void invertxDir() 
    {
        setxDir(getxDir() * -1);
    }

    /**
     * @param xDir the xDir to set
     */
    public void setxDir(int xDir) 
    {
        this.xDir = xDir;
    }

    /**
     * @return the yDir
     */
    public int getyDir() 
    {
        return yDir;
    }

    public boolean isMovingDown() 
    {
        return yDir == 1;
    }

    public void invertyDir() 
    {
        setyDir(getyDir() * -1);
    }

    /**
     * @param yDir the yDir to set
     */
    public void setyDir(int yDir) 
    {
        this.yDir = yDir;
    }

    public void setDiameter(int diameter) 
    {
        this.diameter = diameter;
    }

    public int getDiameter() 
    {
        return diameter;
    }

    public void setSpeed(double speed) 
    {
        this.speed = speed;
    }

    public double getSpeed() 
    {
        return speed;
    }

    public void setXspeedMod(double xSpeedMod)
    {
        this.xSpeedMod = xSpeedMod;
    }
    
    public double getXspeedMod()
    {
        return this.xSpeedMod;
    }
    
    public void setYspeedMod(double ySpeedMod)
    {
        this.ySpeedMod = ySpeedMod;
    }
    
    public double getYspeedMod()
    {
        return this.ySpeedMod;
    }
    
    // xSpeedMod ySpeedMod
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

    public Rectangle getHitbox() 
    {
        Rectangle hitBox = new Rectangle(getX(), getY(), diameter, diameter);

        return hitBox;
    }

    public Rectangle getXHitbox() 
    {
        int x = getX();
        
        
        
        //This means the ball is travelling to the right.
        if (this.isMovingToTheRight()) {
            x += diameter + 1;
        } else {
            x -= 1;
        }

        Rectangle xHitBox = new Rectangle(x, getY()+2, 1, diameter-4);
        return xHitBox;
    }

    public Rectangle getYHitbox() 
    {
        int y = getY();

        if (this.isMovingDown()) {
            y += diameter + 1;
        } else {
            y -= 1;
        }

        Rectangle yHitBox = new Rectangle(getX()+2, y, diameter - 4, 1);
        return yHitBox;
    }

    public Point getCenterPoint() 
    {
        int centerX = (int) Math.round(x + (diameter / 2.0));
        int centerY = (int) Math.round(y + (diameter / 2.0));

        Point centerPoint = new Point(centerX, centerY);
        return centerPoint;
    }

    public void setAngle(int angle)
    {
        this.xSpeedMod = Math.sin(angle);
        this.ySpeedMod = Math.sin(angle);
        
        int realAngle = angle % 360;
        
        if(90 <= realAngle && realAngle >= 270) {
            this.setyDir(-1);
        } else {
            this.setyDir(1);
        }
        
        if(0 <= realAngle && realAngle >= 180) {
            this.setxDir(1);
        } else {
            this.setxDir(-1);
        }
    }
    
    public boolean collidesWith(Brick brick) 
    {
        if (brick == null || brick.isDestroyed()) {
            return false;
        }
        
        Rectangle brickHitbox = brick.getHitBox();
        
        boolean horizontalCollission = this.isHorizontalCollission(brickHitbox);
        boolean verticalCollission = this.isVerticalCollission(brickHitbox);
        
        if(!horizontalCollission && !verticalCollission) {
            return false;
        }
        
        if(horizontalCollission) {
            this.invertxDir();
        }

        if(verticalCollission) {
            this.invertyDir();
        }

        if (horizontalCollission && verticalCollission) {
            System.out.println("The ball hit the brick both horizontally and vertically!");
        }
        
        addBounce();

        return true;
    }

    public boolean isVerticalCollission(Rectangle hitbox) 
    {
        Rectangle yHitbox = this.getYHitbox();

        return (yHitbox.intersects(hitbox));
    }

    public boolean isHorizontalCollission(Rectangle hitbox) 
    {
        Rectangle xHitbox = this.getXHitbox();
        
        return (xHitbox
                .intersects(hitbox));
    }
    
    
    
    /**         ^
     *           \
     *            \        Opposite
     *             \       |
     *              \      | 
     *               \     v  Pad Middle point
     *                B_____________
     *                 \ PAD |
     *                  \    |
     * Hypotenuse --->   \   |     <---Adjacent
     *                    \  |  
     *                     \_|  
     *                      \|
     *                       v   <--- Pad "center" determining the angle by which ball
     *                                is launched away from the pad. This is underneath
     *                                so as to give the pad more even "physics".
     * 
     *  the Cosin determines the ySpeed modifyer and the Sin determines the
     *  xSpeed modifyer.
     * 
     *  As an example for the degree 90(If the pad is hit in the middle) cos(90) is 0 and sin(90) is 1
     *  meaning that the xSpeedMod will be 0 and ySpeedMod will be 1 so the ball will
     *  travel with full speed upwards but not to the sides.
     * 
     * @param pad
     */
    public boolean collidesWith(Pad pad) 
    {
        if (pad == null || yDir == -1) {
            return false;
        }

        Rectangle hitbox = getHitbox();
        if (!hitbox.intersects(pad.getHitBox())) {
            return false;
        }
        
        if(pad.isSticky()) {
            pad.attachBall(this);
        } else {
            launchFromPad(pad);
        }
        
        addBounce();
        
        return true;
    }
    
    /**
     * 
     * @param pad 
     */
    public void launchFromPad(Pad pad)
    {
        Point padLaunchPoint = pad.getPadLaunchPoint();
        
        Point ballCenterPoint = getCenterPoint();
        
        int padLaunchX = (int) Math.round(padLaunchPoint.getX());
        
        Point padSurfaceMiddlePoint = new Point(padLaunchX, pad.getY());

        double oppositeSide = Math.abs(ballCenterPoint.getX() - padSurfaceMiddlePoint.getX());
        double adjacentSide = Math.abs(pad.getY() - padLaunchPoint.getY());
        double hypotenuse = Math.sqrt(Math.pow(oppositeSide, 2) + Math.pow(adjacentSide, 2));
        
        /*
            Now we can use the side lengths to calculate the x and y speed modifyers. 
            This can be done without knowing the angle.
         */
        xSpeedMod = oppositeSide / hypotenuse;
        ySpeedMod = adjacentSide / hypotenuse;

        yDir = -1;  //The ball should always be going up after contact with the pad.

        if (ballCenterPoint.getX() < padSurfaceMiddlePoint.getX()) {
            xDir = -1;   //The ball should be going to the left.
        } else {
            xDir = 1;    //The ball should be going to the right.
        }
    }
    
    public void addBounce()
    {   
        //Do nothing
    }
    
    public boolean isLethal()
    {
        return lethal;
    }
    
    public void setLethal(boolean lethal)
    {
        this.lethal = lethal;
    }
    
    public boolean isVital()
    {
        return vital;
    }
    
    public void setVital(boolean vital)
    {
        this.vital = vital;
    }
    
    public boolean isAttached()
    {
        return attached;
    }
    
    public void setAttached(boolean attached)
    {
        this.attached = attached;
    }
}