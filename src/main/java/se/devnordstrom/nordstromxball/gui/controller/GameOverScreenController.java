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
package se.devnordstrom.nordstromxball.gui.controller;

import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import se.devnordstrom.nordstromxball.MainApp;
import se.devnordstrom.nordstromxball.entity.AnswerTextInputEntity;
import se.devnordstrom.nordstromxball.entity.MenuItemEntity;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;
import se.devnordstrom.nordstromxball.entity.TextEntity;
import se.devnordstrom.nordstromxball.highscore.HighscoreController;
import se.devnordstrom.nordstromxball.highscore.HighscoreEntry;
import se.devnordstrom.nordstromxball.util.Callable;
import se.devnordstrom.nordstromxball.util.MenuUtil;
import se.devnordstrom.nordstromxball.util.Utils;

/**
 *
 * @author Orville N
 */
public class GameOverScreenController extends ScreenController
{    
    /**
     * 
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    
    /**
     * 
     */
    private final HighscoreEntry entry;
    
    /**
     * 
     */
    private static final int PROMPT_NAME_INPUT_WIDTH = 250;
    
    /**
     * 
     */
    private static final int PROMPT_NAME_INPUT_HEIGHT = 30;
    
    /**
     * 
     */
    private int verticalItemMargin = TextScreenController.TEXT_VERTICAL_MARGIN;
    
    /**
     * 
     */
    private int currentStatItemY = MenuController.MARGIN_VERTICAL + (2*verticalItemMargin);
    
    /**
     * 
     */
    private volatile boolean manageSaveEntryToHighscoreRunning;
    
    private final Runnable goToMenuRunnable, exitRunnable, showHighscoreRunnable;
    
    private final MouseListener mouseListener;
    
    private final MouseMotionListener mouseMotionListener;
    
    /**
     * This may be set if the user 
     * is prompted to type in their name.
     */
    private KeyListener keyListener;
    
    private final List<PaintableEntity> entities;
    
    /**
     * 
     */
    public GameOverScreenController(HighscoreEntry entry, 
            Runnable goToMenuRunnable, 
            Runnable exitRunnable, 
            Runnable showHighscoreRunnable)
    {
        System.out.println("GameOverScreenController constructor started!");
        
        this.entry = entry;
        this.goToMenuRunnable = goToMenuRunnable;
        this.exitRunnable = exitRunnable;
        this.showHighscoreRunnable = showHighscoreRunnable;
        
        this.entities = new LinkedList<>();
        
        this.mouseListener = MenuUtil.getMenuMouseListener(this);
        this.mouseMotionListener = MenuUtil.getMenuMouseMotionListener(this);
                
        setTextItems();
    }
    
    private void setTextItems()
    {
        setMenuControlls();
        
        try {
            setStatItems();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
            
    private void setMenuControlls()
    {
        int firstRowY = MenuController.MARGIN_VERTICAL;
        int goToMenuItemX = MenuController.MARGIN_HORIZONTAL;
        
        
        //Sets the return to menu button
        MenuItemEntity goToMenuItem = MenuUtil.createMenuItemEntity(goToMenuItemX, 
                firstRowY, 
                "MENU", 
                goToMenuRunnable);
        
        //Sets the exit menu items.
        int exitItemX = TextScreenController.MENU_EXIT_ITEM_X;
        MenuItemEntity exitMenuItem = MenuUtil.createMenuItemEntity(exitItemX, 
                firstRowY, 
                "EXIT", 
                exitRunnable);
        
        this.entities.add(goToMenuItem);
        this.entities.add(exitMenuItem);
    }
    
    private void setStatItems() throws Exception
    {
        //Sets the line showing the date.        
        String createdAtTime = new SimpleDateFormat(DATE_FORMAT).format(entry.getCreatedAt());
        addStatItem(createdAtTime);
        
        
        
        //Sets the line showing the game mode.
        addStatItem("Game mode: "+entry.getGameMode());
        
        
        
        //Sets the line showing the points      
        addStatItem("Points: "+entry.getPoints());
        
        
        //Sets the line showing the number of levels cleared.
        String clearedLevelsInfo = "Levels cleared: "+entry.getCompletedLevels();
        if(entry.isGameCompleted()) {
            clearedLevelsInfo += " (Finished the game mode)";
        }
        addStatItem(clearedLevelsInfo);
        
        //Adds line to clear a new row.
        addStatItem("\n");
        
        
        
        if(HighscoreController.isQualifiedForHighScore(entry)) {
            /*
            
                Congratulations! Your score qualifies you
                for a highscore entry,

                What's your name?

                __________________ OK
            
            */
            
            addStatItem("Congratulations! Your score qualifies you");
            addStatItem("for a highscore entry!");
            addStatItem("\n");
            addStatItem("What's your name?");
            
            addNameInputIItem();
        } else {
            //Adds info showing the player didn't make it to the highscore.
            int lowestPoints = HighscoreController
                    .getLowestQualifyingPointsForGameMode(entry.getGameMode());
            
            addStatItem("In order to qualify for a highscore entry");
            addStatItem("you need atleast "+lowestPoints+" point(s).");
            
            addShowHighscoreButton();          
        }
    }
    
    
    /**
     * Registers the text as an entity.
     * 
     * @param entity 
     */
    private void addStatItem(String text)
    {
        if(text != null && !text.trim().isEmpty()) {
            int statX = MenuController.MARGIN_HORIZONTAL;
            
            TextEntity entity = MenuUtil.createTextItem(statX, currentStatItemY, text.trim());
            entities.add(entity);
        }
        
        currentStatItemY += verticalItemMargin;        
    }
    
    private void addShowHighscoreButton()
    {
        int statX = MenuController.MARGIN_HORIZONTAL;
        
        
        //Adding extra vertical margin to the button.
        currentStatItemY += verticalItemMargin; 
        
        MenuItemEntity showHighscoreItem = MenuUtil.createMenuItemEntity(statX, 
                currentStatItemY, 
                "SHOW HIGHSCORE", 
                showHighscoreRunnable);
        
        entities.add(showHighscoreItem);
        
        currentStatItemY += verticalItemMargin;
    }
    
    private void addNameInputIItem()
    {
        int inputX = MenuController.MARGIN_HORIZONTAL, inputY = currentStatItemY, 
                inputWidth = PROMPT_NAME_INPUT_WIDTH, inputHeight = PROMPT_NAME_INPUT_HEIGHT;
        
        Rectangle nameTextBox = new Rectangle(inputX, inputY, inputWidth, inputHeight);
        
        Callable<String> callable = (String playerName) -> {
            if(validateName(playerName)) {
                manageSaveEntryToHighscore(playerName);
            }
        };
        
        AnswerTextInputEntity answerTextInputItem = new AnswerTextInputEntity(nameTextBox, callable);

        keyListener = answerTextInputItem.getKeyListener();

        entities.add(answerTextInputItem);
        
        currentStatItemY += verticalItemMargin;
    }
    
    /**
     * 
     * @param playerName
     * @return 
     */
    private boolean validateName(String playerName)
    {
        System.out.println("validateName(...) automatically returning true.");
        return true;
    }
    
    /**
     * 
     * @param playerName 
     */
    private void manageSaveEntryToHighscore(String playerName)
    {
        if(manageSaveEntryToHighscoreRunning) {
            System.err.println("Returning from manageSaveEntryToHighscore(...)"
                    + " because it's allready running!");
            return;
        }
        manageSaveEntryToHighscoreRunning = true;
        
        try {
            saveEntryToHighscore(playerName);
            
            Utils.showMessage("Highscore screen not supported yet!", MainApp.APP_TITLE);
            
            goToMenuRunnable.run();
        } catch(Exception ex) {
            ex.printStackTrace();
            Utils.showErrorMesage("Couldn't save the entry: " + ex.getMessage(), MainApp.APP_TITLE);
        } finally {
            manageSaveEntryToHighscoreRunning = false;
        }
    }
    
    private void saveEntryToHighscore(String playerName) throws Exception
    {
        if(playerName == null) 
            throw new NullPointerException();
                
        if(playerName.trim().isEmpty())
            throw new IllegalArgumentException("The playerName must not be empty!");
        
        System.out.println("saveEntryToHighscore(...) started!");
        
        entry.setPlayerName(playerName);
        
        HighscoreController.addHighscoreEntry(entry);
    }
    
    @Override
    public synchronized List<PaintableEntity> getEntities() 
    {
        List<PaintableEntity> returnEntities = new LinkedList<>();
 
        returnEntities.addAll(entities);
                
        return returnEntities;
    }
    
    @Override
    public MouseListener getMouseListener()
    {
        return this.mouseListener;
    }
    
    @Override
    public MouseMotionListener getMouseMotionListener()
    {
        return this.mouseMotionListener;
    }
    
    @Override
    public KeyListener getKeyListener()
    {
        if(keyListener == null) {
            System.out.println("GameOverScreenController getKeyListener() returning NULL!");
        }
        
        return this.keyListener;
    }
}