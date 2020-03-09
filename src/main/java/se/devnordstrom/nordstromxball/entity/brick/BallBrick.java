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
import java.util.Random;
import se.devnordstrom.nordstromxball.entity.ball.Ball;

/**
 *
 * @author Orville N
 */
public class BallBrick extends Brick
{
    private static final int RELEASE_BALL_CHANCE_PERCENTAGE = 33;
    
    public BallBrick()
    {
        super();
        
        brickColor = Color.RED;
        
        setPowerupChangePercentage(RELEASE_BALL_CHANCE_PERCENTAGE);
    }
    
    public Ball releaseBall()
    {
        Ball extraBall = new Ball();
        extraBall.setColor(Color.RED);
        
        int centerX = (int) Math.round(this.getX() + (this.getWidth()/2.0));
        int centerY = (int) Math.round(this.getY() + (this.getHeight()/2.0));
        
        int ballX = centerX - extraBall.getDiameter();
        int ballY = centerY - extraBall.getDiameter();
        
        extraBall.setX(ballX);
        extraBall.setY(ballY);
        
        return extraBall;
    }
    
    /**
     * This brick will release balls instead of powerups.
     * 
     * @param random
     * @return 
     */
    @Override
    public boolean hasPowerUp(Random random)
    {
        return false;
    }
    
    public boolean hasBall(Random random)
    {
        return random.nextInt(101) < getPowerupChangePercentage();        
    }
}