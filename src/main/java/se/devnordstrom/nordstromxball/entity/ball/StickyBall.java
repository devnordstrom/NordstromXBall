/*
 * Copyright (C) 2020 User
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
 * @author User
 */
public class StickyBall extends Ball 
{
    private static Color STICKY_BALL_DEFAULT_COLOR = Color.YELLOW;
    
    public StickyBall()
    {
        super();
        
        this.setColor(STICKY_BALL_DEFAULT_COLOR);
        
        this.setSticky(true);
    }
}