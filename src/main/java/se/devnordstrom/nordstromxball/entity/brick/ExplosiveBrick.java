/*
 * Copyright (C) 2020 Orville N
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
import se.devnordstrom.nordstromxball.entity.powerup.Explosion;
import se.devnordstrom.nordstromxball.entity.powerup.Powerup;
import se.devnordstrom.nordstromxball.entity.powerup.PowerupKind;

/**
 *
 * @author Orville Nordström
 */
public class ExplosiveBrick extends Brick
{   
    @Override
    public Color getColor()
    {
        if(System.currentTimeMillis() % 100 <= 50) {
            return Explosion.DEFAULT_COLOR;
        } else {
            return Explosion.FLASH_COLOR;
        }
    }
    
    @Override
    public Powerup getPowerUp()
    {
        Powerup powerup = super.getPowerUp();
        
        int powerupX = getX() - getWidth();
        int powerupY = getY() - getHeight();
        
        powerup.setX(powerupX);
        powerup.setY(powerupY);
        
        powerup.setPowerUpKind(PowerupKind.EXPLOSION);
        return powerup;
    }
    
    @Override
    public boolean hasPowerUp(Random random)
    {
        return true;
    }
}