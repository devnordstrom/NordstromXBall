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
package se.devnordstrom.nordstromxball.logic.level;

import java.util.ArrayList;
import java.util.List;
import se.devnordstrom.nordstromxball.MainApp;
import se.devnordstrom.nordstromxball.logic.DefaultGame;
import se.devnordstrom.nordstromxball.logic.Game;
import se.devnordstrom.nordstromxball.logic.animation.Animation;
import se.devnordstrom.nordstromxball.logic.animation.StandardAnimation;

/**
 * Class containing the campaign levels as well as their names.
 * 
 * @author Orville N
 */
public class CampainGame 
{   
    private static final String CAMPAIGN_NAME = "Campaign mode";
    
    private static final String CAMPAIGN_DESCRIPTION = "The campaign mode for "+ MainApp.APP_TITLE;
    
    
    
    private static final String STARTING_LEVEL_NAME = "Intro Level";
    
    private static final char[][] STARTING_LAYOUT = {
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', 'S', ' ', 'X', 'X', 'X', 'X', ' ', 'S', ' ', ' ', ' '},
            {' ', ' ', 's', 's', ' ', 'X', 'X', 'X', 'X', ' ', 's', 's', ' ', ' '},
            {' ', ' ', 't', 't', 't', 't', 't', 't', 't', 't', 't', 't', ' ', ' '},
            {' ', ' ', 'd', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'd', ' ', ' '},
            {' ', ' ', 'd', ' ', 'b', 'b', ' ', ' ', 'b', 'b', ' ', 'd', ' ', ' '},
            {' ', ' ', 'd', ' ', 'b', 'b', ' ', ' ', 'b', 'b', ' ', 'd', ' ', ' '},
            {' ', ' ', 'd', ' ', 'b', 'b', ' ', ' ', 'b', 'b', ' ', 'd', ' ', ' '},
            {' ', ' ', 'd', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'd', ' ', ' '},
            {' ', ' ', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', ' ', ' '},
            {' ', ' ', 'X', ' ', ' ', 't', 'X', 't', ' ', ' ', ' ', 'X', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
    };
    
    
    
    private static final String BRICK_FORTRESS = "Brick fortress";
    
    private static final char[][] BRICK_FORTRESS_LAYOUT = {
            {' ', 'i', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'i', ' '},
            {' ', 'i', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'i', ' '},
            {' ', 'i', 'd', ' ', 'd', 'i', 'i', 'i', 'i', 'd', ' ', 'd', 'i', ' '},
            {' ', 'i', 'd', 'd', 'd', ' ', ' ', ' ', ' ', 'd', 'd', 'd', 'i', ' '},
            {' ', 'i', 'd', 'X', 'd', ' ', ' ', ' ', ' ', 'd', 'X', 'd', 'i', ' '},
            {' ', 'i', 'd', 'X', 'd', ' ', ' ', ' ', ' ', 'd', 'X', 'd', 'i', ' '},
            {' ', 'i', 'd', 'X', 'd', ' ', ' ', ' ', ' ', 'd', 'X', 'd', 'i', ' '},
            {' ', 'i', 'd', 'X', 'd', ' ', 'X', 'X', ' ', 'd', 'X', 'd', 'i', ' '},
            {' ', 'i', 'd', 'X', 'd', 'K', 'X', 'X', 'K', 'd', 'X', 'd', 'i', ' '},
            {' ', 'i', 'd', 'X', 'd', 't', 't', 't', 't', 'd', 'X', 'd', 'i', ' '},
            {' ', 'i', 'd', 'X', 'd', 'K', 'K', 'K', 'K', 'd', 'X', 'd', 'i', ' '},
            {' ', 'i', 'K', 'X', 'K', 'b', 'b', 'b', 'b', 'K', 'X', 'K', 'i', ' '},
            {' ', 'i', 'd', 'K', 'd', 'K', 'K', 'K', 'K', 'd', 'K', 'd', 'i', ' '},
            {' ', 'i', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'i', ' '},
            {' ', 'i', 't', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 't', 'i', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
    };
    
    private static final String SMILEY_LEVEL_NAME = "Big Smiley :)";
    
    private static final char[][] SMILEY_LAYOUT = new char[][] {
            {' ', ' ', ' ', ' ', 'd', 'd', ' ', ' ', 'd', 'd', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', 'X', 'X', ' ', ' ', 'X', 'X', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', 'X', 'X', ' ', ' ', 'X', 'X', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', 'd', 'd', ' ', ' ', 'd', 'd', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', 'b', 'b', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', 'b', 'b', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', 'd', ' ', ' ', 'b', 'b', ' ', ' ', 'd', ' ', ' ', ' '},
            {' ', ' ', ' ', 'd', ' ', 'b', 'b', 'b', 'b', ' ', 'd', ' ', ' ', ' '},
            {' ', ' ', ' ', 'd', ' ', 'b', 'K', 'K', 'b', ' ', 'd', ' ', ' ', ' '},
            {' ', ' ', ' ', 'd', ' ', ' ', ' ', ' ', ' ', ' ', 'd', ' ', ' ', ' '},
            {' ', ' ', ' ', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', ' ', ' ', ' '},
            {' ', ' ', ' ', 'd', ' ', 'b', 'b', 'b', 'b', ' ', 'd', ' ', ' ', ' '},
            {' ', ' ', ' ', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
    };
    
    
    
    private static final String BUNKER_LEVEL_NAME = "Ping Pong Bunker";
    
    private static final char[][] BUNKER_LAYOUT = new char[][] {
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', ' ', ' '},
            {' ', ' ', 'i', 'K', 'K', 'K', 'K', 'K', 'K', 'K', 'K', 'i', ' ', ' '},
            {' ', ' ', 'i', 'd', ' ', ' ', ' ', ' ', ' ', ' ', 'd', 'i', ' ', ' '},
            {' ', ' ', 'i', 'd', ' ', 't', 't', 't', 't', ' ', 'd', 'i', ' ', ' '},
            {' ', ' ', 'i', 'd', ' ', 't', 'b', 'b', 't', ' ', 'd', 'i', ' ', ' '},
            {' ', ' ', 'i', 'd', ' ', 't', 'b', 'b', 't', ' ', 'd', 'i', ' ', ' '},
            {' ', ' ', 'i', 'd', ' ', 't', 't', 't', 't', ' ', 'd', 'i', ' ', ' '},
            {' ', ' ', 'i', 'd', ' ', ' ', ' ', ' ', ' ', ' ', 'd', 'i', ' ', ' '},
            {' ', ' ', 'i', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'i', ' ', ' '},
            {' ', ' ', 'i', 't', 'X', 'X', 'X', 'X', 'X', 'X', 't', 'i', ' ', ' '},
            {' ', ' ', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
    };

    
    
    private static final String SNAKE_LEVEL_NAME = "Brick Snake";
    
    private static final char[][] SNAKE_LAYOUT = new char[][] {
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'K', 'K', 'K', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', 'K', 'K', 'K', 'K', 'K', 'K', ' ', 'b', 'b', 'b', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', 'K', ' ', 'b', 'b', 'b', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', 'K', ' ', 'b', 'b', 'b', ' ', ' '},
            {' ', ' ', 'X', 'X', 'X', ' ', ' ', 'K', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', 'X', 'X', 'X', ' ', ' ', 'K', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', 'X', 'X', 'X', ' ', ' ', 'K', 'K', 'K', 'K', 'K', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'K', 'K', 'K'},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'t', 't', 't', 't', 't', 't', 't', 't', 't', 't', 't', 't', 't', 't'},
            {'t', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 't'},
            {'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i'},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
    };

    
    private static final String TWO_PILLARS_LEVEL_NAME = "2 Pillars";
    
    private static final char[][] TWO_PILLARS_LAYOUT = new char[][] {
            {' ', ' ', 'd', 'X', 'd', 'i', ' ', ' ', 'i', 'd', 'X', 'd', ' ', ' '},
            {' ', ' ', 'd', 'X', 'd', 'i', ' ', ' ', 'i', 'd', 'X', 'd', ' ', ' '},
            {' ', ' ', 'd', 'X', 'd', 'i', ' ', ' ', 'i', 'd', 'X', 'd', ' ', ' '},
            {' ', ' ', 'd', 'X', 'd', 'i', ' ', ' ', 'i', 'd', 'X', 'd', ' ', ' '},
            {' ', ' ', 'd', 'X', 'd', 'i', ' ', ' ', 'i', 'd', 'X', 'd', ' ', ' '},
            {' ', ' ', 'd', 't', 'd', 'i', ' ', ' ', 'i', 'd', 't', 'd', ' ', ' '},
            {' ', ' ', 'd', 'b', 'd', 'i', ' ', ' ', 'i', 'd', 'b', 'd', ' ', ' '},
            {' ', ' ', 'd', 't', 'd', 'i', ' ', ' ', 'i', 'd', 't', 'd', ' ', ' '},
            {' ', ' ', 'd', 'b', 'd', 'i', ' ', ' ', 'i', 'd', 'b', 'd', ' ', ' '},
            {' ', ' ', 'd', 't', 'd', 'i', ' ', ' ', 'i', 'd', 't', 'd', ' ', ' '},
            {' ', ' ', 'd', 'b', 'd', 'i', ' ', ' ', 'i', 'd', 'b', 'd', ' ', ' '},
            {' ', ' ', 'd', 't', 'd', 'i', ' ', ' ', 'i', 'd', 't', 'd', ' ', ' '},
            {' ', ' ', 'd', 'b', 'd', 'i', ' ', ' ', 'i', 'd', 'b', 'd', ' ', ' '},
            {' ', ' ', 'd', 't', 'd', 'i', ' ', ' ', 'i', 'd', 't', 'd', ' ', ' '},
            {' ', ' ', 'd', 'b', 'd', 'i', ' ', ' ', 'i', 'd', 'b', 'd', ' ', ' '},
            {' ', ' ', 'd', 'd', 'd', 'i', ' ', ' ', 'i', 'd', 'd', 'd', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
    };
        
    public static List<Level> loadLevels()
    {
        
        
        StandardLevel startingLevel = LevelReader.readDefaultLevel(STARTING_LEVEL_NAME, 
                STARTING_LAYOUT);
        StandardLevel brickFortressLevel = LevelReader.readDefaultLevel(BRICK_FORTRESS,
                BRICK_FORTRESS_LAYOUT);
        StandardLevel smileyLevel = LevelReader.readDefaultLevel(SMILEY_LEVEL_NAME, 
                SMILEY_LAYOUT);                        
        StandardLevel bunkerLevel = LevelReader.readDefaultLevel(BUNKER_LEVEL_NAME, 
                BUNKER_LAYOUT);
        StandardLevel snakeLevel = LevelReader.readDefaultLevel(SNAKE_LEVEL_NAME, 
                SNAKE_LAYOUT);
        StandardLevel twoPillarLevel = LevelReader.readDefaultLevel(TWO_PILLARS_LEVEL_NAME,
                TWO_PILLARS_LAYOUT);
        
        List<Level> levels = new ArrayList<>();        
        
        
        levels.add(startingLevel);
        levels.add(smileyLevel);        
        levels.add(bunkerLevel);
        levels.add(snakeLevel);
        levels.add(twoPillarLevel);
        levels.add(brickFortressLevel);
        
        
        return levels;        
    }
    
    public static Game loadGame()
    {
        DefaultGame game = new DefaultGame(CAMPAIGN_NAME, CAMPAIGN_DESCRIPTION, loadLevels());
        
        return game;
    }
}