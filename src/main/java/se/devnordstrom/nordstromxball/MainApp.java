package se.devnordstrom.nordstromxball;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import se.devnordstrom.nordstromxball.gui.GUIController;
import se.devnordstrom.nordstromxball.logic.sound.AudioController;
import se.devnordstrom.nordstromxball.util.ImageController;
import se.devnordstrom.nordstromxball.util.Utils;

/**
 * 
 * @author Orville N
 */
public class MainApp 
{
    /**
     * 
     */
    public static final boolean SET_OUTPUT_TO_FILE = false;
    
    public static final String LOG_DIR = "log";
    
    public static final String OUTPUT_FILE = LOG_DIR + File.separator + "output.txt";
    
    public static final String OUTPUT_ERROR_FILE = LOG_DIR + File.separator + "output-error.txt";
    
    /**
     * 
     */
    public static final String APP_TITLE = "NordstromXBall";
    
    public static final String AUTHOR = "Orville Nordström";
    
    public static final String HELP_TEXT = "Welcome to " + APP_TITLE + "."
            + "\n"
            + "\nIn this game you control a pad with your mouse"
            + "\nand you bounce a ball off the pad to hit"
            + "\nall the bricks in the levels."
            + "\n"
            + "\nPowerups will spawn randomly from bricks,"
            + "\nsome bricks will release balls or toxic"
            + "\nsubstances which you must avoid."
            + "\n"
            + "\nIn order to launch a ball that is attached "
            + "\nto your pad press the right mouse button."
            + "\n"
            + "\nAuthor: "+AUTHOR;
    
    public static final String EXIT_TEXT = "Thank you so much for playing."
            + "\n"
            + "\nHave a nice day and please play again :)";
    
    /**
     * 
     * @param args 
     */
    public static void main(String[] args)
    {
        if(SET_OUTPUT_TO_FILE) {
            try {
                setOutputToFile();
            } catch(Exception ex) {
                ex.printStackTrace();
                Utils.showErrorMesage("Unable to set output:"+ex.getMessage(), APP_TITLE);
            }
        }
        
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
        //Loads the sound of the game.
        AudioController.loadSound();
        
        //Loads the images.
        ImageController.load();
    }
    
    public static void exit()
    {
        Utils.showMessage(EXIT_TEXT, MainApp.APP_TITLE);
        System.exit(0);
    }
    
    private static void setOutputToFile() throws FileNotFoundException
    {
        new File(LOG_DIR).mkdir();
                
        System.setOut(new PrintStream(new File(OUTPUT_FILE)));
        System.setErr(new PrintStream(new File(OUTPUT_ERROR_FILE)));
    }
}