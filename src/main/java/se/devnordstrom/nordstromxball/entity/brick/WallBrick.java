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
 * @author Orville Nordström
 */
public class WallBrick extends Brick
{
    public WallBrick(int x, int y, int width, int height)
    {        
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        
        this.points = 0;        
        
        
        brickColor = Color.BLACK;
    }
    
    @Override
    public void paint(Graphics g)
    {
        g.setColor(brickColor);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        
        //Not painting a border for the WallBrick
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