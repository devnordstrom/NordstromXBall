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

/**
 *
 * @author Orville Nordström
 */
public class ToughBrick extends Brick
{   
    private static final int POWERUP_CHANCE_PERCENTAGE = 10;
    
    public static final int DEFAULT_START_HP = 2;
    public static final Color FULL_HP_COLOR = new Color(0, 200, 0);
    public static final Color LOW_HP_COLOR = new Color(0, 150, 0);
    
    protected int hp;
    
    public ToughBrick()
    {
        this(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public ToughBrick(int x, int y, int width, int height)
    {
        super(x, y, width, height);
                
        this.hp = DEFAULT_START_HP;
        
        brickColor = FULL_HP_COLOR;
        borderColor = DEFAULT_BORDER_COLOR;
        
        this.setPowerupChangePercentage(POWERUP_CHANCE_PERCENTAGE);
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
    public boolean hasPowerUp(Random random)
    {
        if(hp != 0) {
            return false;
        }
        
        return super.hasPowerUp(random);        
    }
}