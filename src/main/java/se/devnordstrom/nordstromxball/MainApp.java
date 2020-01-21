package se.devnordstrom.nordstromxball;

import se.devnordstrom.nordstromxball.gui.MainJFrame;
import se.devnordstrom.nordstromxball.gui.controller.DemoGameController;
import se.devnordstrom.nordstromxball.gui.controller.ScreenController;
import se.devnordstrom.nordstromxball.gui.controller.StandardGameController;
import se.devnordstrom.nordstromxball.logic.Game;
import se.devnordstrom.nordstromxball.logic.LevelController;
import se.devnordstrom.nordstromxball.logic.level.LevelReader;

/**
 *
 * @author User
 */
public class MainApp 
{
    public static final String APP_TITLE = "NordstromXBall";
    
    public static void main(String[] args)
    {
        startTestGame();
    }
    
    private static void startSampleGame()
    {
        //LevelController
        Game game = LevelController.getSampleGame();
        StandardGameController gameController = new StandardGameController(game, MainJFrame.DEFAULT_WIDTH, MainJFrame.DEFAULT_HEIGHT);
        
       //Creates the GUI and injects the controller.
        MainJFrame mainGui = new MainJFrame(APP_TITLE);
        mainGui.setScreenController(gameController);
        mainGui.startGUI();
    }
    
    private static void startTestGame()
    {
        Game testGame = LevelReader.generateTestGame();
        StandardGameController gameController = new StandardGameController(testGame, MainJFrame.DEFAULT_WIDTH, MainJFrame.DEFAULT_HEIGHT);
        
       //Creates the GUI and injects the controller.
        MainJFrame mainGui = new MainJFrame(APP_TITLE);
        mainGui.setScreenController(gameController);
        mainGui.startGUI();
    }
}