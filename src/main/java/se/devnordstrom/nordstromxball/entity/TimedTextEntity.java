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
package se.devnordstrom.nordstromxball.entity;

import java.awt.Graphics;

/**
 *
 * @author Orville N
 */
public class TimedTextEntity extends TextEntity 
{
    public static final long DEFAULT_DISPLAY_INTERVAL = 5 * 1000;
    
    protected long createdAt, displayInterval;
    
    public TimedTextEntity(int x, int y, String text)
    {
        super(x, y, text);
        
        createdAt = System.currentTimeMillis();
        displayInterval = DEFAULT_DISPLAY_INTERVAL;
    }
    
    /**
     * @return the displayInterval
     */
    public long getDisplayInterval() 
    {
        return displayInterval;
    }

    /**
     * @param displayInterval the displayInterval to set
     */
    public void setDisplayInterval(long displayInterval) 
    {
        this.displayInterval = displayInterval;
    }
    
    @Override
    public void paint(Graphics g)
    {
        if(!shouldDisplay()) {
            return;
        }
        
        super.paint(g);
    }
    
    public boolean shouldDisplay()
    {
        return (System.currentTimeMillis() - createdAt) < getDisplayInterval();
    }
}