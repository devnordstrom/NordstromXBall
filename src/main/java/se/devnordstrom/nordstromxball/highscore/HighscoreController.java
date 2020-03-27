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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import se.devnordstrom.nordstromxball.MainApp;
import se.devnordstrom.nordstromxball.util.Utils;

/**
 *
 * @author Orville N
 */
public class HighscoreController 
{   
    private static final String HIGHSCORE_FILE_NAME = "highscore.ser";
    
    private static final int DISPLAY_HIGHSCORE_MAXIMUM = 10;
    
    /**
     * Reads and sorts the Highscore entries for the gameMode provided.
     * 
     * @param gameMode
     * @return 
     */
    public static List<HighscoreEntry> fetchEntries(String gameMode) 
            throws IOException, ClassNotFoundException
    {
        List<HighscoreEntry> highscoreEntries = new ArrayList<>();
        List<HighscoreEntry> oldHighscoreEntries = fetchAllEntries();
        
        for(HighscoreEntry highscoreEntry : oldHighscoreEntries) {
            if(gameMode == null 
                    || gameMode.equals(highscoreEntry.getGameMode())) {
                highscoreEntries.add(highscoreEntry);
            }
        }
        
        Collections.sort(highscoreEntries);
        Collections.reverse(highscoreEntries);
        
        return highscoreEntries;
    }
    
    /**
     * 
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    private static List<HighscoreEntry> fetchAllEntries() throws IOException, 
            ClassNotFoundException 
    {   
        File highscoreFile = new File(HIGHSCORE_FILE_NAME);
        
        //Returns empty list if no entries exists
        if(!highscoreFile.exists()) {
            return new ArrayList<>();
        }

        HighscoreEntry[] entries = (HighscoreEntry[]) 
                Utils.unserialize(highscoreFile);

        List<HighscoreEntry> returnEntries = new ArrayList<>();
        for(HighscoreEntry entry : entries) {
            returnEntries.add(entry);
        }
        return returnEntries;
    }
    
    /**
     * 
     * @param entry
     * @return 
     */
    public static boolean isQualifiedForHighScore(HighscoreEntry entry) 
            throws IOException, ClassNotFoundException
    {    
        if(entry == null) {
            throw new IllegalArgumentException("The highscore entry must be set!");
        }
        
        if(entry.getGameMode() == null 
                || entry.getGameMode().trim().isEmpty()) {
            throw new IllegalArgumentException("The difficulty must be set!");
        }
        
        if(entry.getPoints() <= 0) {
            return false;
        }
        
        List<HighscoreEntry> entries = fetchEntries(entry.getGameMode());
        
        entries.add(entry);
        
        Collections.sort(entries);
        
        /*
            Since only a certain number of entries are saved/shown.
        
            So if only 10 entries are to be shown and this is the
            entry with the 11th highest number of points then this
            entry will not qualify.
        */
        return entries.indexOf(entry) <= DISPLAY_HIGHSCORE_MAXIMUM;
    }
    
    /**
     * Returns the lowest points needed to qualify to the highscore
     * and be above currently lowest qualifiying score.
     * 
     * If no entries are saved then the number 1 will be returned.
     * 
     * @param gameMode
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static int getLowestQualifyingPointsForGameMode(String gameMode) throws IOException, ClassNotFoundException
    {

        HighscoreEntry entry = getLowestQualifyingHighscoreForGameMode(gameMode);

        if(entry == null) {
            return 1;
        } else {
            return entry.getPoints()+1;
        }
        
    }
    
    /**
     * 
     * @param difficulty
     * @return 
     */
    public static HighscoreEntry getLowestQualifyingHighscoreForGameMode(String gameMode)
            throws IOException, ClassNotFoundException
    {   
        if(gameMode == null) 
            throw new NullPointerException("The gameMode must not be null!");
        
        if(gameMode.trim().isEmpty())
            throw new IllegalArgumentException("The gameMode must not be empty!");
        
        
        List<HighscoreEntry> entryList = fetchEntries(gameMode);
        
        if(entryList.isEmpty()) {
            return null;
        }
        
        return entryList.get(entryList.size() - 1);
    }
    
    /**
     * 
     * @param entry 
     */
    public static void addHighscoreEntry(HighscoreEntry entry) throws IOException, ClassNotFoundException 
    {
        if(entry == null) {
            throw new IllegalArgumentException("Entry must not be null!");
        }
        
        if(entry.getGameMode() == null) {
            throw new IllegalArgumentException("The game mode must be set!");
        }

        List<HighscoreEntry> highscoreEntryList = fetchAllEntries();
        
        highscoreEntryList.add(entry);
        
        saveHighscoreList(highscoreEntryList); 
    }
    
    private static void saveHighscoreList(List<HighscoreEntry> highscoreEntryList) 
    {
        try {
            HighscoreEntry[] entryArray = highscoreEntryList.toArray(new HighscoreEntry[0]);
            
            File highscoreFile = new File(HIGHSCORE_FILE_NAME);
            
            Utils.serialize(highscoreFile, entryArray);
        } catch(Exception ex) {
            ex.printStackTrace();
            
            Utils.showErrorMesage("Could not save highscore: " + ex, MainApp.APP_TITLE);
        }
    }
}