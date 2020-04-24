/*
 * Copyright (C) 2020 Orville Nordström
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
import java.util.List;
import se.devnordstrom.nordstromxball.logic.DefaultGame;
import se.devnordstrom.nordstromxball.logic.Game;

/**
 *
 * @author Orville Nordström
 */
public class TestGame 
{
    private static final String GAME_NAME = "Test Game";
    
    private static final String GAME_DESC = "Just for testing purposes.";
        
    private static final String TEST_LEVEL_NAME = "Testlevel";
    
    private static final char[][] TEST_LEVEL_LAYOUT = {
            {' ', 'S', ' ', ' ', ' ', 'b', 'b', 'b', 'b', ' ', ' ', ' ', ' ', ' '},
            {' ', 'S', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', ' '},
            {' ', 'S', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'S', ' '},
            {' ', 'S', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'S', ' '},
            {' ', 'S', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'S', ' '},
            {' ', 'S', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'S', ' '},
            {' ', 'S', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'S', ' '},
            {' ', 'S', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'S', ' '},
            {' ', 'S', ' ', ' ', 'S', 'S', 'S', 'S', 'S', 'S', ' ', ' ', 'S', ' '},
            {' ', 'S', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'S', ' '},
            {' ', 'S', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'S', ' '},
            {' ', 'S', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'S', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
    };
    
    private static List<Level> loadLevels()
    {
        StandardLevel testLevel = LevelReader.readDefaultLevel(TEST_LEVEL_NAME, 
                TEST_LEVEL_LAYOUT);
        
        List<Level> levels = new ArrayList<>();
        
        levels.add(testLevel);
        
        return levels;
    }
    
    public static Game loadGame()
    {
        DefaultGame game = new DefaultGame(GAME_NAME, GAME_DESC, loadLevels());
        
        return game;
    }
    
}
