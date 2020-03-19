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
import se.devnordstrom.nordstromxball.entity.powerup.Powerup;
import se.devnordstrom.nordstromxball.entity.powerup.PowerupKind;

/**
 *
 * @author Orville N
 */
public class ToxicBrick extends Brick
{
    private static final int RELEASE_TOXIC_POWERUP_PERCENTAGE = 100;
    
    public ToxicBrick()
    {
        super();
                
        setPowerupChangePercentage(RELEASE_TOXIC_POWERUP_PERCENTAGE);
    }
    
    @Override
    public Color getColor()
    {
        int red = 56, green = 128, blue = 4;

        if(System.currentTimeMillis() % 200 < 100) {
            green = 148;
        }

        return new Color(red, green, blue);
        //This will return a "breathing" green color between 100-150
    }
    
    @Override
    public Powerup getPowerUp()
    {
        Powerup powerup = super.getPowerUp();        
        powerup.setPowerUpKind(PowerupKind.KILL_PLAYER);
        return powerup;
    }
}