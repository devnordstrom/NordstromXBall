package se.devnordstrom.nordstromxball;

import se.devnordstrom.nordstromxball.gui.GUIController;
import se.devnordstrom.nordstromxball.gui.controller.StandardGameController;
import se.devnordstrom.nordstromxball.logic.level.CampainGame;
import se.devnordstrom.nordstromxball.logic.sound.AudioController;

/**
 * 
 * @author Orville N
 */
public class MainApp 
{
    /**
     * 
     */
    public static final String APP_TITLE = "NordstromXBall";
    
    /**
     * 
     * @param args 
     */
    public static void main(String[] args)
    {
        startGUI();        
    }
    
    private static void startGUI()
    {
        load();
        
        GUIController guiController = new GUIController();
        guiController.startGUI();
    }
    
    private static void load()
    {
        AudioController.loadSound();
    }
    
    private static StandardGameController getStandardGameController()
    {
        StandardGameController gameController = new StandardGameController(CampainGame.loadGame());
        return gameController;
    }
    
    public static void exit()
    {
        System.exit(0);
    }
}