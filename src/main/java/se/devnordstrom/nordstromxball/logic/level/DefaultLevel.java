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
package se.devnordstrom.nordstromxball.logic.level;

import se.devnordstrom.nordstromxball.logic.level.Level;
import java.util.ArrayList;
import java.util.Collection;
import se.devnordstrom.nordstromxball.entity.brick.Brick;

/**
 *
 * @author Orville Nordström
 */
public class DefaultLevel implements Level
{
    public static final int BRICK_ROWS = 18;
    public static final int BRICK_COLUMNS = 14;
    public static final int BRICK_WIDTH = 50;
    public static final int BRICK_HEIGHT = 25;
    
    private int levelNumber;
    
    private boolean bonusLevel;
    
    private String levelName;
    
    private Brick[][] bricks;
    
    public DefaultLevel()
    {
        this(new Brick[BRICK_ROWS][BRICK_COLUMNS]);
    }
    
    /**
     * 
     * @param bricks 
     */
    public DefaultLevel(Brick[][] bricks)
    {
        this.bricks = bricks;
    }
       
    /**
     * 
     * @return 
     */
    public boolean isBonusLevel()
    {
        return bonusLevel;
    }
    
    public void setBonusLevel(boolean bonusLevel)
    {
        this.bonusLevel = bonusLevel;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Collection<Brick> getBricks()
    {
        Collection<Brick> brickCollection = new ArrayList<>();
        
        for(int row = 0; row < bricks.length; row++) {
            for(int col = 0; col < bricks[row].length; col++) {
                Brick brick = bricks[row][col];
                
                if(brick != null) {
                    int x = BRICK_WIDTH * col;
                    int y = BRICK_HEIGHT * row;
                    
                    brick.setX(x);
                    brick.setY(y);
                    
                    brickCollection.add(brick);
                }
            }
        }
        
        
        return brickCollection;
    }
    
    /**
     * 
     * @param bricks 
     */
    public void setBricks(Brick[][] bricks)
    {
        this.bricks = bricks;
    }
    
    /**
     * 
     * @param brick
     * @param row
     * @param column 
     */
    public void setBrick(Brick brick, int row, int column)
    {
        this.bricks[row][column] = brick;
    }
    
    /**
     * @return the levelNumber
     */
    public int getLevelNumber() 
    {
        return levelNumber;
    }

    /**
     * @param levelNumber the levelNumber to set
     */
    public void setLevelNumber(int levelNumber) 
    {
        this.levelNumber = levelNumber;
    }

    /**
     * @return the levelName
     */
    public String getLevelName() 
    {
        return levelName;
    }

    /**
     * @param levelName the levelName to set
     */
    public void setLevelName(String levelName) 
    {
        this.levelName = levelName;
    }
    
    @Override
    public boolean isCleared()
    {
        Collection<Brick> bricks = this.getBricks();
        for(Brick brick : bricks) {
            if(brick != null 
                    && !brick.isDestroyed()
                    && !brick.isIndestructable()
                    && !brick.isMustBeDestroyed()) {
                return false;
            }
        }
        
        return true;
    }
    
    public int countRemainingBricks()
    {
        int desctructibleBricks = 0;
        
        Collection<Brick> bricks = this.getBricks();
        for(Brick brick : bricks) {
            if(brick != null && !brick.isDestroyed() && !brick.isMustBeDestroyed()) {
                desctructibleBricks++;
            }
        }
        
        return desctructibleBricks;
    }
}