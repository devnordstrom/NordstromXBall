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

import java.util.List;
import se.devnordstrom.nordstromxball.entity.brick.Brick;

/**
 *
 * @author Orville Nordström
 */
public interface Level 
{
    public boolean isBonusLevel();
    
    public List<Brick> getBricks();
    
    public String getStartingMessage();
    
    public int getLevelNumber();
    
    public void setLevelNumber(int levelNumber);
            
    public String getLevelName();
    
    public boolean isCleared();
}