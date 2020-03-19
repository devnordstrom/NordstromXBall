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
import java.awt.Graphics;

/**
 *
 * @author Orville N
 */
public class SteelBrick extends Brick
{
    public static final Color DEFAULT_COLOR = Color.GRAY;
    
    public SteelBrick()
    {   
        super();
        
        this.points = 0;        
        
        this.setIndestructable(true);
        this.setMustBeDestroyed(false);
        
        brickColor = DEFAULT_COLOR;
    }
    
    @Override
    public int getPoints()
    {
        return 0;
    }
    
    @Override
    public int hit()
    {
        return 0;
    }
}