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
package se.devnordstrom.nordstromxball.gui;

import javax.swing.JFrame;
import se.devnordstrom.nordstromxball.MainApp;
import se.devnordstrom.nordstromxball.gui.controller.GameOverScreenController;
import se.devnordstrom.nordstromxball.gui.controller.TextScreenController;
import se.devnordstrom.nordstromxball.gui.controller.MenuController;
import se.devnordstrom.nordstromxball.gui.controller.ScreenController;
import se.devnordstrom.nordstromxball.gui.controller.StandardGameController;
import se.devnordstrom.nordstromxball.highscore.HighscoreEntry;
import se.devnordstrom.nordstromxball.logic.Game;
import se.devnordstrom.nordstromxball.logic.level.CampainGame;
import se.devnordstrom.nordstromxball.util.Callable;
import se.devnordstrom.nordstromxball.util.Utils;

/**
 * 
 * @author Orville N
 */
public class GUIController 
{
    private final MainJFrame mainGui;
    
    public GUIController()
    {        
        mainGui = new MainJFrame(MainApp.APP_TITLE);
        
    }
    
    public void startGUI()
    {
        mainGui.setScreenController(getMenuController());
        mainGui.startGUI();
    }
    
    public void setScreenController(ScreenController screenController)
    {
        mainGui.setScreenController(screenController);
    }
    
    private StandardGameController getGameController()
    {
        Callable<HighscoreEntry> callable = (HighscoreEntry entry) -> {
            setGameOverScreen(entry);
        };

        Game game = CampainGame.loadGame();
        
        //Inits the game controller.
        StandardGameController gameController = new StandardGameController(game, callable);
        return gameController;
    }
    
    private void setGameOverScreen(HighscoreEntry entry)
    {
        ScreenController screenController = new GameOverScreenController(entry, 
                getGoToMenuRunnable(), 
                getExitRunnable(),
                getShowHighscoreRunnable());
        
        setScreenController(screenController);
    }
    
    private MenuController getMenuController()
    {
        Runnable startGameTask = getStartCampaignGameRunnable();
        Runnable showHelpRunnable = getShowHelpRunnable();
        Runnable showHighscoreRunnable = getShowHighscoreRunnable();
        Runnable exitRunnable = getExitRunnable();
        
        MenuController menuController = new MenuController(startGameTask, showHelpRunnable,
                showHighscoreRunnable, exitRunnable);
        
        return menuController;
    }
    
    private Runnable getGoToMenuRunnable()
    {
        return ()-> {
            setScreenController(getMenuController());
        };
    }
    
    private Runnable getStartCampaignGameRunnable()
    {
        Runnable startGameTask = () -> {
            setScreenController(getGameController());
        };
        
        return startGameTask;
    }
    
    private Runnable getShowHelpRunnable()
    {
        Runnable showHelpRunnable = () -> {
            setScreenController(getHelpScreenController());            
        };
        
        return showHelpRunnable;
    }
    
    /**
     * 
     * @return 
     */
    private ScreenController getHelpScreenController()
    {
        TextScreenController helpGuiController = new TextScreenController(MainApp.HELP_TEXT,
                getGoToMenuRunnable(),
                getExitRunnable());
        
        return helpGuiController;        
    }
    
    private Runnable getShowHighscoreRunnable()
    {
        Runnable showHighscoreRunnable = ()->{
            Utils.showMessage("Highscore Option not supported yet!.", MainApp.APP_TITLE);
        };
        
        return showHighscoreRunnable;
    }
    
    private Runnable getExitRunnable()
    {
        Runnable exitRunnable = () -> {
            MainApp.exit();
        };
        
        return exitRunnable;
    }
}