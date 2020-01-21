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
import java.awt.Point;
import java.util.Random;
import se.devnordstrom.nordstromxball.entity.powerup.Powerup;
import se.devnordstrom.nordstromxball.entity.powerup.PowerupKind;

/**
 *
 * @author Orville Nordström
 */
public class ToughBrick extends Brick
{
    protected static final Color FULL_HP_COLOR = new Color(0, 200, 0);
    protected static final Color LOW_HP_COLOR = new Color(0, 150, 0);
    
    protected int hp;
    
    public ToughBrick()
    {
        super();
        
        this.hp = 2;
        
        brickColor = FULL_HP_COLOR;
        borderColor = DEFAULT_BORDER_COLOR;
    }
    
    public ToughBrick(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        
        this.hp = 2;
        
        brickColor = FULL_HP_COLOR;
        borderColor = DEFAULT_BORDER_COLOR;
    }
  
    @Override
    public boolean isDestroyed()
    {
        return hp <= 0;
    }
    
    @Override
    public int hit()
    {
        hp--;
        
        this.setColor(LOW_HP_COLOR);
        
        return getPoints();
    }
    
    public int getHp()
    {
        return hp;
    }
    
    
    @Override
    public Powerup getPowerUp()
    {
        Point centerPoint = getCenterPoint();
        
        Powerup powerup = new Powerup();
        double powerUpX = (centerPoint.getX() - (powerup.getDiameter() / 2.0));
        double powerUpY = (centerPoint.getY() - (powerup.getDiameter() / 2.0));
        
        powerup.setPowerUpKind(PowerupKind.RANDOM);
        
        powerup.setX(powerUpX);
        powerup.setY(powerUpY);

        return powerup;
    }
    
    @Override
    public boolean hasPowerUp()
    {
        if(hp != 0) {
            return false;
        }        
        
        return System.currentTimeMillis() % 5 == 1;
    }
}