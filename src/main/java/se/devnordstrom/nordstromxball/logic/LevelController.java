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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import se.devnordstrom.nordstromxball.entity.brick.BallBrick;
import se.devnordstrom.nordstromxball.entity.brick.Brick;
import se.devnordstrom.nordstromxball.entity.brick.InvisibleBrick;
import se.devnordstrom.nordstromxball.entity.brick.ToughBrick;

/**
 *
 * @author Orville Nordström
 */
public class LevelController 
{
    public static Game getSampleGame()
    {
        Collection<Level> levels = new ArrayList<>();
        
        for(int i = 0; i < 5; i++) {
            levels.add(generateLevel());
        }
                
        
        String name = "Sample Game";
        String description = "Sample description";
        
        
        Game game = new DefaultGame(name, description, levels);
        return game;
    }
        
    public static Level generateLevel()
    {
        Random random = new Random();
        
        DefaultLevel demoLevel = new DefaultLevel();
        
        demoLevel.setLevelName("DemoLevel");
        
        Brick[][] bricks = new Brick[DefaultLevel.BRICK_ROWS][DefaultLevel.BRICK_COLUMNS];
        
        for(int row = 5; row < bricks.length -3; row++) {
            for(int col = 3; col < bricks[row].length - 3; col++) {
                Brick brick = null;
                if(col % 2 == 0 || row % 2 == 0) {                    
                    brick = new ToughBrick();
                } else {
                    brick = new Brick();
                }
                
                bricks[row][col] = brick;
            }
        }
                
        demoLevel.setBricks(bricks);
        
        return demoLevel;
    }
}