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
import java.awt.image.BufferedImage;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;
import se.devnordstrom.nordstromxball.entity.brick.Brick;
import se.devnordstrom.nordstromxball.entity.pad.Pad;
import se.devnordstrom.nordstromxball.util.ImageController;

/**
 *
 * @author Orville Nordström
 */
public class Ball implements PaintableEntity 
{    
    private static final boolean DRAW_ROUND_BALL = false;
        
    private static final int DEFAULT_DIAMETER = 10;
    
    private static final double DEFAULT_SPEED = 500;

    private static final Color DEFAULT_COLOR = Color.GREEN;
    
    protected double x, y, xDistanceLastMove, yDistanceLastMove, 
            xSpeedMod, ySpeedMod, speed;

    private Point newMovePoint;
    
    protected int xDir, yDir, diameter;

    protected Color color;

    private boolean vital, lethal, attached, sticky;
        
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
        Color ballColor = getColor();
        
        g.setColor(ballColor);
        
        if(DRAW_ROUND_BALL) {
            g.fillOval(getX(), getY(), diameter, diameter);
        } else {
            g.fillRect(getX(), getY(), diameter, diameter);
        }
    }
    
    @Override
    public void move(double delta) 
    {   
        if(isAttached()) {
            return;
        }
        
        xDistanceLastMove = speed * xSpeedMod * getxDir() * delta; 
        yDistanceLastMove = speed * ySpeedMod * getyDir() * delta;
        
        if(isNewMovePointSet()) {
            this.x = newMovePoint.getX();
            this.y = newMovePoint.getY();
            newMovePoint = null;
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
    
    protected void setNewMovePoint(double x, double y)
    {        
        this.newMovePoint = new Point();
        newMovePoint.setLocation(x, y);
    }
        
    protected boolean isNewMovePointSet()
    {
        return newMovePoint != null;
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
    
    protected Rectangle getHorizontalHitbox() 
    {
        int x = getX();
        int width = 1;
        int height = diameter;
        
        //So if the ball moved 20 steps horizontally, then the entire area is evaluated.
        if (Math.abs(xDistanceLastMove) > width) {
            width = (int) Math.ceil(Math.abs(xDistanceLastMove));
        }
        
        if (isMovingToTheRight()) {
            x += diameter - width;
        }
           
        Rectangle xHitBox = new Rectangle(x, getY(), width, height);
        return xHitBox;
    }
    
    public Point getCenterPoint() 
    {
        int centerX = (int) Math.round(x + (diameter / 2.0));
        int centerY = (int) Math.round(y + (diameter / 2.0));

        Point centerPoint = new Point(centerX, centerY);
        return centerPoint;
    }
    
    public boolean collidesWith(Brick brick) 
    {
        if (brick == null || brick.isDestroyed()) {
            return false;
        }
                
        return collidesWith(brick.getHitbox());
    }
        
    public boolean collidesWith(Rectangle hitbox)
    {
        Rectangle ballHitbox = getHitbox();
        
        if(!hitbox.intersects(ballHitbox)) {
            return false;
        }
        
        //Now we know that a colission has ocurred.        
        if(isNewMovePointSet()) {
            /**
             * It's possible for a ball to collide with several adjacent
             * hitboxes in one turn, and if the ball collides with several objects
             * such as adjacent bricks all the bricks should be hit, however
             * only once should the balls position/direction be adjusted.
             */
            return true;
        }
                
        addBounce();
        
        double newX = x, newY = y;
        
        if(isHorizontalCollision(hitbox)) {
            
            if(isMovingToTheRight()) {
                //Forces the ball to be outside the hitbox to the left.
                newX = hitbox.getX() - ballHitbox.getWidth() - 1;
            } else {
                //Forces the ball to be outside the hitbox to the right.
                newX = hitbox.getX() + hitbox.getWidth() + 1;
            }
            
            invertxDir();
        } else {
            if(isMovingDown()) {
                newY = hitbox.getY() - ballHitbox.getHeight() - 1;
            } else {
                newY = hitbox.getY() + hitbox.getHeight() + 1;                
            }
            
            invertyDir();
        }
        
        setNewMovePoint(newX, newY);
        
        return true;
    }

    /**
     * Evaluates if the ball hit the Rectangle horizontally.
     * 
     * The reason as to why this doesn't simply check the
     * intersection is that the ball could've moved several
     * units/pixels per frame. With 60 FPS and a move speed
     * of 500 steps/second then the ball will move 8(8.33) steps.
     * 
     * Normally this isn't an issue but if the FPS drops temporarily
     * the ball could theoretically jump over a brick, or move into
     * it so that the colission is evaluated to be horizontal when
     * it should've been evaluated to be vertical and hence the distance
     * moved in the current frame is taken into account when evaluating if
     * a horizontal collision has occured.
     * 
     * @param hitbox
     * @return 
     */
    protected boolean isHorizontalCollision(Rectangle hitbox)
    {
        //Creates the target hitbox.
        int thx, thy = (int) Math.round(hitbox.getY()), 
                thwidth = 1, thheight = (int) hitbox.getHeight();
        if(isMovingToTheRight()) {
            thx = (int) Math.round(hitbox.getX());
        } else {
            thx = (int) Math.round(hitbox.getX() + hitbox.getWidth()) - thwidth;
        }
        
        Rectangle targetHorizontalHitbox = new Rectangle(thx, thy, thwidth, thheight);
        
        return getHorizontalHitbox().intersects(targetHorizontalHitbox);
    }
    
    protected boolean isVerticalCollision(Rectangle hitbox)
    {
        Rectangle ballHitbox = this.getHitbox();
        Rectangle intersection = ballHitbox.intersection(hitbox);
        
        return intersection.getHeight() <= intersection.getWidth();
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
        //Ignore if the ball is moving away from the pads launch direction.
        if(pad == null || yDir == -1) {
            return false;
        }
        
        if(!getHitbox().intersects(pad.getHitbox())) {
            return false;
        }
        
        //Now we know a colission has occurred with the pad.
        
        //If the ball or pad is "sticky" then attach the ball to the pad.
        if(pad.isSticky() || this.isSticky()) {
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

        //The ball should always be going up after contact with the pad.
        yDir = -1;

        if (ballCenterPoint.getX() < padSurfaceMiddlePoint.getX()) {
            xDir = -1;   //The ball should be going to the left.
        } else {
            xDir = 1;    //The ball should be going to the right.
        }
    }
    
    /**
     * This may be usefull for implementations that make the 
     * ball faster for every bounce.
     */
    public void addBounce()
    {   
        //Does nothing in this implementation, but this may be overriden.
    }
    
    /**
     * 
     * @return 
     */
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
    
    /**
     * @return the sticky
     */
    public boolean isSticky()
    {
        return sticky;
    }

    /**
     * @param sticky the sticky to set
     */
    public void setSticky(boolean sticky) 
    {
        this.sticky = sticky;
    }
}