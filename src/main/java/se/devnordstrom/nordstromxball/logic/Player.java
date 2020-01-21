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
    private int points, brokenBricks, lives;
    
    private String name;
    
    public Player()
    {
        this.lives = 3;
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
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setLives(int lives)
    {
        this.lives = lives;
    }
    
    public int getLives()
    {
        return this.lives;
    }
    
    public void addLife()
    {
        this.lives++;
    }
    
    public void takeLife()
    {
        this.lives--;
    }
}