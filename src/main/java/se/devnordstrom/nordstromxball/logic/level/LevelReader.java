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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import se.devnordstrom.nordstromxball.entity.brick.BallBrick;
import se.devnordstrom.nordstromxball.entity.brick.Brick;
import se.devnordstrom.nordstromxball.entity.brick.ExplosiveBrick;
import se.devnordstrom.nordstromxball.entity.brick.InvisibleBrick;
import se.devnordstrom.nordstromxball.entity.brick.SteelBrick;
import se.devnordstrom.nordstromxball.entity.brick.StickyBallBrick;
import se.devnordstrom.nordstromxball.entity.brick.ToughBrick;
import se.devnordstrom.nordstromxball.entity.brick.ToxicBrick;
import se.devnordstrom.nordstromxball.logic.DefaultGame;
import se.devnordstrom.nordstromxball.logic.Game;

/**
 *
 * @author Orville Nordström
 */
public class LevelReader 
{
    public static final char EMPTY = ' ';
    private static final char DEFAULT_BRICK = 'd';
    private static final char BALL_BRICK = 'b';
    private static final char TOUGH_BRICK = 't';
    private static final char INVISIBLE_BRICK = 'i';
    
    public static final char[] BRICK_CODES = new char[]{EMPTY, DEFAULT_BRICK, 
        BALL_BRICK, TOUGH_BRICK, INVISIBLE_BRICK};
    
    private static final char[][] EMPTY_LEVEL = {
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
    };
    
    public static DefaultLevel generateRandomLevel(String name)
    {
        Random rand = new Random();
        
        char[][] levelLayout = EMPTY_LEVEL;
        
        int rowMargin = rand.nextInt(6);
        int columnMargin = rand.nextInt(10);
        int invisibleBricks = 0;
                
        for(int row = rowMargin; row < (levelLayout.length - rowMargin); row++) {
            for(int column = columnMargin; column < (levelLayout[row].length - columnMargin); column++) {
                                
                char brickSymbol;
                if(rand.nextBoolean()) {
                    brickSymbol = EMPTY;
                } else {
                    //BALL_BRICK, TOUGH_BRICK, INVISIBLE_BRICK                       
                    int index = rand.nextInt(BRICK_CODES.length - 1) + 1;   //So this will not be empty
                    brickSymbol = BRICK_CODES[index];

                    if(INVISIBLE_BRICK == brickSymbol) {
                        invisibleBricks++;
                    }
                }
                
                levelLayout[row][column] = brickSymbol;
            }
        }        
        
        DefaultLevel generatedLevel = readDefaultLevel(name, levelLayout);
        return generatedLevel;
    }
    
    public static DefaultLevel readDefaultLevel(String levelName, char[][] levelLayout)
    {
        if(levelLayout == null) throw new NullPointerException();
        
        if(levelLayout.length > DefaultLevel.BRICK_ROWS) {        
            throw new IndexOutOfBoundsException("levelLayout must not contain "
                    + "more than "+DefaultLevel.BRICK_ROWS+" rows("+levelLayout.length+").");
        }
        
        if(levelLayout.length > 0 && levelLayout[0].length > DefaultLevel.BRICK_COLUMNS) {
            throw new IndexOutOfBoundsException("levelLayout must not contain "
                    + "more than "+DefaultLevel.BRICK_COLUMNS+" columns("+levelLayout[0].length+").");
        }
        
        
        Brick[][] bricks = new Brick[DefaultLevel.BRICK_ROWS][DefaultLevel.BRICK_COLUMNS];
        
        for(int row = 0; row < levelLayout.length; row++) {
            char[] brickColumns = levelLayout[row];
            
            for(int column = 0; column < brickColumns.length; column++) {
                bricks[row][column] = readBrick(brickColumns[column]);
            }
        }
        
        DefaultLevel level = new DefaultLevel(bricks);
        
        level.setLevelName(levelName);
        
        return level;
    }    
    
    public static Brick readBrick(char brickInfo)
    {
        switch(brickInfo) {
            case ' ':
                return null;
            case 'd':
                return new Brick();
            case 'b':
                return new BallBrick();
            case 't':
                return new ToughBrick();
            case 'i':
                return new InvisibleBrick();
            case 's':
                return new StickyBallBrick();
            case 'S':
                return new SteelBrick();
            case 'K':
                return new ToxicBrick();
            case 'X':
                return new ExplosiveBrick();
            default:
                throw new IllegalArgumentException("char '"+brickInfo+"' is not supported.");
        }
    }
    
}