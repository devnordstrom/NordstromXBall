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
import se.devnordstrom.nordstromxball.entity.ball.Ball;

/**
 *
 * @author Orville Nordström
 */
public class BallBrick extends Brick
{
    public BallBrick()
    {
        super();
        
        brickColor = Color.RED;
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
        
        /*
        int angle = 225;
        extraBall.setAngle(angle);
        */
              
        
        return extraBall;
    }
}