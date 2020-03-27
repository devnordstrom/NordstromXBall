/*
 * Copyright (C) 2020 Orville N
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
package se.devnordstrom.nordstromxball.highscore;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Orville Nordström
 */
public class HighscoreEntry implements Serializable, Comparable<HighscoreEntry>
{
    private Date createdAt;
    
    private String playerName, gameMode;
        
    private int points, completedLevels;
    
    private boolean gameCompleted;
    
    /**
     * @return the createdAt
     */
    public Date getCreatedAt() 
    {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    /**
     * @return the playerName
     */
    public String getPlayerName() 
    {
        return playerName;
    }

    /**
     * @param playerName the playerName to set
     */
    public void setPlayerName(String playerName) 
    {
        this.playerName = playerName;
    }

    /**
     * @return the gameMode
     */
    public String getGameMode() 
    {
        return gameMode;
    }

    /**
     * @param gameMode the gameMode to set
     */
    public void setGameMode(String gameMode) 
    {
        this.gameMode = gameMode;
    }
    
    /**
     * @return the points
     */
    public int getPoints() 
    {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(int points) 
    {
        this.points = points;
    }
    
    /**
     * @return the completedLevels
     */
    public int getCompletedLevels() 
    {
        return completedLevels;
    }

    /**
     * @param completedLevels the completedLevels to set
     */
    public void setCompletedLevels(int completedLevels) 
    {
        this.completedLevels = completedLevels;
    }
    
    /**
     * Compares the high score entries first by score, 
     * then by answers and finally by when they were created.
     * 
     * 
     * <b>NOTE</b> this will <b>NOT</b> take the gamemode into account!
     * You should only compare entries with the same gamemode!
     * 
     * @param compareHighscore
     * @return 
     */
    @Override
    public int compareTo(HighscoreEntry compareHighscore) 
    {
        if(compareHighscore == null) throw new NullPointerException();
        
        if(this.getPoints() != compareHighscore.getPoints()) {
            Integer score = getPoints();
            Integer compareScore = compareHighscore.getPoints();
            
            return score.compareTo(compareScore);
        } else if(getCreatedAt() != null && compareHighscore.getCreatedAt() != null) {
            return this.getCreatedAt().compareTo(compareHighscore.getCreatedAt());
        } else {
            return 0;       
        }
    }
    
    /**
     * @return the gameCompleted
     */
    public boolean isGameCompleted() 
    {
        return gameCompleted;
    }

    /**
     * @param gameCompleted the gameCompleted to set
     */
    public void setGameCompleted(boolean gameCompleted) 
    {
        this.gameCompleted = gameCompleted;
    }
}