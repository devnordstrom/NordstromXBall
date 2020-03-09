package se.devnordstrom.nordstromxball;

import se.devnordstrom.nordstromxball.gui.MainJFrame;
import se.devnordstrom.nordstromxball.gui.controller.StandardGameController;
import se.devnordstrom.nordstromxball.logic.level.CampainGame;
import se.devnordstrom.nordstromxball.logic.sound.AudioController;

/**
 * 
 * @author Orville N
 */
public class MainApp 
{
    public static final String APP_TITLE = "NordstromXBall";
    
    public static void main(String[] args)
    {
        startCampaignMode();
    }
    
    private static void startCampaignMode()
    {
        //Loads the sound
        AudioController.loadSound();
        
        StandardGameController gameController = new StandardGameController(CampainGame.loadGame());
        
        //Creates the GUI and injects the controller.
        MainJFrame mainGui = new MainJFrame(APP_TITLE);
        mainGui.setScreenController(gameController);
        mainGui.startGUI();
    }
}