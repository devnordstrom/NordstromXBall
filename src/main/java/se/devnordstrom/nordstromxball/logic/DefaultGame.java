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
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Orville Nordström
 */
public class DefaultGame implements Game
{
    protected List<Level> levels;
    
    protected Iterator<Level> levelIterator;
    
    protected String name, description;
    
    protected int levelNumber;
    
    public DefaultGame(String name, String description, Collection<Level> levels)
    {
        this.name = name;
        this.description = description;
        
        this.levels = new ArrayList();
        this.levels.addAll(levels);
        
        levelIterator = this.levels.iterator();
    }
    
    @Override
    public Level nextLevel()
    {
        levelNumber++;
        
        Level level = levelIterator.next();
        level.setLevelNumber(levelNumber);

        return level;
    }
    
    @Override
    public boolean hasNextLevel() 
    {
        return levelIterator.hasNext();
    }

    @Override
    public String getName() 
    {
        return name;
    }

    @Override
    public String getDescription() 
    {
        return description;
    }
}