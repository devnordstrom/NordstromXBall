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
import se.devnordstrom.nordstromxball.entity.ball.StickyBall;
import se.devnordstrom.nordstromxball.entity.powerup.Powerup;
import se.devnordstrom.nordstromxball.entity.powerup.PowerupKind;

/**
 *
 * @author Orville Nordström
 */
public class BallBrick extends Brick
{
    private static final Color BALL_BRICK_COLOR = Color.RED;
    
    private static final int RELEASE_BALL_CHANCE_PERCENTAGE = 33;
    
    public BallBrick()
    {
        super();
        
        setColor(BALL_BRICK_COLOR);
        
        setPowerupChangePercentage(RELEASE_BALL_CHANCE_PERCENTAGE);
    }
    
    @Override
    public Powerup getPowerUp()
    {
        Powerup powerup = super.getPowerUp();
        powerup.setPowerUpKind(PowerupKind.EXTRA_BALL);
        return powerup;
    }
}