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

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import se.devnordstrom.nordstromxball.entity.MenuItemEntity;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;
import se.devnordstrom.nordstromxball.entity.TextEntity;
import static se.devnordstrom.nordstromxball.gui.controller.TextScreenController.MENU_EXIT_ITEM_X;
import se.devnordstrom.nordstromxball.highscore.HighscoreController;
import se.devnordstrom.nordstromxball.highscore.HighscoreEntry;
import se.devnordstrom.nordstromxball.util.MenuUtil;

/**
 *
 * @author Orville Nordström
 */
public class HighscoreScreenController extends ScreenController
{
    /**
     * 
     */ 
    private static final String[] HIGHSCORE_HEADER_COLUMN = {
        "MODE",
        "POINTS",
        "NAME",
        "DATE"
    };
    
    /**
     * 
     */
    private static final int ENTRY_PADDING = 145;
    
    /**
     * 
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    
    
    
    private int verticalMargin, horizontalMargin;
    
    /**
     * 
     */
    private int verticalItemMargin = TextScreenController.TEXT_VERTICAL_MARGIN;
    
    /**
     * 
     */
    private int currentStatItemY = MenuController.MARGIN_VERTICAL + (2*verticalItemMargin);
    
    private String gameMode;
    
    private final List<PaintableEntity> entities;
    
    private final Runnable goToMenuRunnable, exitRunnable;
    
    private final MouseListener mouseListener;
    
    private final MouseMotionListener mouseMotionListener;
    
    public HighscoreScreenController(String startingGameMode,
            Runnable goToMenuRunnable,
            Runnable exitRunnable)
    {
        this.gameMode = startingGameMode;
        this.entities = new LinkedList<>();
        this.goToMenuRunnable = goToMenuRunnable;
        this.exitRunnable = exitRunnable;
        this.verticalMargin = MenuController.MARGIN_VERTICAL;
        this.horizontalMargin = MenuController.MARGIN_HORIZONTAL;
        this.mouseListener = createMoucseListener();
        this.mouseMotionListener = createMouseMotionListener();
        
        setEntities();
    }
    
    private void setEntities()
    {        
        int goToMenuItemX = horizontalMargin;
        int firstRowY = verticalMargin;
        
        //Adds the return to menu button
        MenuItemEntity goToMenuItem = MenuUtil.createMenuItemEntity(goToMenuItemX, 
                firstRowY, 
                "MENU", 
                goToMenuRunnable);
        
        MenuItemEntity exitMenuItem = MenuUtil.createMenuItemEntity(MENU_EXIT_ITEM_X, 
                firstRowY, 
                "EXIT", 
                exitRunnable);
        
        
        entities.add(goToMenuItem);
        entities.add(exitMenuItem);
        
        
        //TO DO add support for switching gametypes.
        
        
        //Adds all the text items.
        entities.addAll(getHighscoreEntries());
    }
    
    private List<PaintableEntity> getHighscoreEntries()
    {   
        List<PaintableEntity> highscoreTextItems = new LinkedList<>();
        List<HighscoreEntry> highscoreEntries = loadHighscoreEntries();
        
        if(highscoreEntries == null) {
            highscoreTextItems.add(createTextItem("(Couldn't load entries)"));
        } else if(highscoreEntries.isEmpty()) {            
            highscoreTextItems.add(createTextItem("(There were no entries)"));
        } else {
            
            highscoreTextItems.addAll(createTextItemsForRow(HIGHSCORE_HEADER_COLUMN));
            
            //Adding empty row.
            highscoreTextItems.addAll(createTextItemsForRow(new String[0]));
            
            for(HighscoreEntry entry : highscoreEntries) {
                highscoreTextItems.addAll(createTextItemsForRow(entry));
            }
        }
        
        return highscoreTextItems;
    }
    
    private List<TextEntity> createTextItemsForRow(HighscoreEntry entry)
    { 
        String[] entryDetails = new String[]{
                entry.getGameMode().replace("mode", "").trim(),    //Game mode
                String.valueOf(entry.getPoints()),
                entry.getPlayerName(),
                new SimpleDateFormat(DATE_FORMAT).format(entry.getCreatedAt())};
        
        return createTextItemsForRow(entryDetails);
    }
    
    private List<TextEntity> createTextItemsForRow(String[] columnInfo)
    {   
        int x = horizontalMargin, y = currentStatItemY;
        
        List<TextEntity> textItems = new ArrayList<>();
        for(String info : columnInfo) {
            textItems.add(createTextItem(info, x, y));
            x += ENTRY_PADDING;
        }
        
        currentStatItemY += verticalItemMargin;
        
        return textItems;
    }
    
    private TextEntity createTextItem(String text)
    {
        int x = horizontalMargin, y = currentStatItemY;
        currentStatItemY += verticalItemMargin;
        return createTextItem(text, x, y);
    }
    
    private TextEntity createTextItem(String text, int x, int y)
    {
        return MenuUtil.createTextItem(x, y, text);
    }
    
    /**
     * 
     * @return 
     */
    private List<HighscoreEntry> loadHighscoreEntries()
    {        
        try {
            List<HighscoreEntry> entries = HighscoreController.fetchEntries(gameMode);
            return entries;
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @return 
     */
    private MouseListener createMoucseListener()
    {
        return MenuUtil.getMenuMouseListener(this);
    }
    
    /**
     * 
     * @return 
     */
    private MouseMotionListener createMouseMotionListener()
    {
        return MenuUtil.getMenuMouseMotionListener(this);
    }
    
    @Override
    public MouseListener getMouseListener()
    {
        return mouseListener;
    }
    
    @Override
    public MouseMotionListener getMouseMotionListener()
    {
        return mouseMotionListener;
    }
    
    @Override
    public List<PaintableEntity> getEntities() 
    {
        return entities;
    }
}