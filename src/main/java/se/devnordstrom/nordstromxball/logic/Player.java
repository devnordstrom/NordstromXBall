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
package se.devnordstrom.nordstromxball.logic;

/**
 *
 * @author Orville Nordström
 */
public class Player 
{
    public static final int DEFAULT_STARTING_LIVES = 3;
    
    private int points, brokenBricks, lifeCount;
    
    private String name;
    
    public Player()
    {
        this.lifeCount = DEFAULT_STARTING_LIVES;
    }
    
    public int getPoints()
    {
        return points;
    }
    
    public void setPoints(int points)
    {
        this.points = points;
    }
    
    public void addPoints(int points)
    {
        this.points += points;
    }
    
    public int getBrokenBricks()
    {
        return brokenBricks;
    }
    
    public void setBrokenBricks(int brokenBricks)
    {
        this.brokenBricks = brokenBricks;
    }
    
    public void addBrokenBrick()
    {
        this.brokenBricks++;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setLifeCount(int lifeCount)
    {
        this.lifeCount = lifeCount;
    }
    
    public int getLifeCount()
    {
        return this.lifeCount;
    }
    
    public void addLife()
    {
        this.lifeCount++;
    }
    
    public void takeLife()
    {
        this.lifeCount--;
    }
}