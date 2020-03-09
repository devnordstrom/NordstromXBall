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

/**
 *
 * @author Orville N
 */
public class VitalBall extends Ball
{
    public VitalBall()
    {
        super();
        
        setColor(Color.WHITE);
        
        setVital(true);
    }
    
    public Color getBorderColor()
    {
        long millis = System.currentTimeMillis();
        
        if(millis % 100 <= 50) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }
    
    
    @Override
    public boolean isVital()
    {
        return true;
    }
}