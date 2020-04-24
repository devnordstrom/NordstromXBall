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

import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import se.devnordstrom.nordstromxball.MainApp;
import se.devnordstrom.nordstromxball.entity.MenuItemEntity;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;
import se.devnordstrom.nordstromxball.entity.TextEntity;
import se.devnordstrom.nordstromxball.util.MenuUtil;

/**
 *
 * @author Orville Nordström
 */
public class MenuController extends ScreenController
{   
    public static final int MARGIN_HORIZONTAL = 50;
    public static final int MARGIN_VERTICAL = 50;
    public static final int ITEM_VERTICAL_MARGIN = 65;
    
    private static final Color MENU_COLOR = Color.WHITE;
    
    private final List<PaintableEntity> menuItems = new ArrayList<>();
    
    private final Runnable startGameRunnable, showHelpRunnable, 
            showHighscoreRunnable, exitRunnable;
    
    private final MouseMotionListener mouseMotionListener;
    
    private final MouseListener mouseListener;
    
    private boolean hasSetMenuItemsRun;

    public MenuController(Runnable startGameRunnable, 
            Runnable showHelpRunnable, 
            Runnable showHighscoreRunnable,
            Runnable exitRunnable)
    {
        this.startGameRunnable = startGameRunnable;
        this.showHelpRunnable = showHelpRunnable;
        this.showHighscoreRunnable = showHighscoreRunnable;
        this.exitRunnable = exitRunnable;
        this.mouseMotionListener = createMouseMotionListener();
        this.mouseListener = createMouseListener();
                
        setMenuItems();
    }
    
    private void setMenuItems()
    {
        if(hasSetMenuItemsRun) {
            throw new IllegalStateException("The setMenuItems() method can only run once!");
        }
        hasSetMenuItemsRun = true;
        
        int x, y;
        
        x = MARGIN_HORIZONTAL;
        y = MARGIN_VERTICAL;
        
        //Adds header/greating text.
        TextEntity textItem = new TextEntity();
        textItem.setX(x);
        textItem.setY(y);
        textItem.setText("HELLO, WELLCOME TO "+MainApp.APP_TITLE.toUpperCase());
        textItem.setColor(MENU_COLOR);
        textItem.setFont(MenuUtil.MENU_ITEM_FONT);
        menuItems.add(textItem);
        
        
        y += ITEM_VERTICAL_MARGIN;
        
        //Adds the start game option.
        MenuItemEntity startGameItem = MenuUtil
                .createMenuItemEntity(x, y, "START GAME", startGameRunnable);
        menuItems.add(startGameItem);
        
        
        y += ITEM_VERTICAL_MARGIN;
        
        
        //Adds the Help text option.
        MenuItemEntity showHelpItem = MenuUtil
                .createMenuItemEntity(x, y, "HELP", showHelpRunnable);
        menuItems.add(showHelpItem);
        
        
        y += ITEM_VERTICAL_MARGIN;
        
        //Adds the option to display the highscore.        
        MenuItemEntity showHighscoreItem = MenuUtil
                .createMenuItemEntity(x, y, "HIGHSCORE", showHighscoreRunnable);
        menuItems.add(showHighscoreItem);
        
        
        y += ITEM_VERTICAL_MARGIN;
        
        
        //Adds the option to exit the application.
        MenuItemEntity exitItem = MenuUtil
                .createMenuItemEntity(x, y, "EXIT", exitRunnable);
        menuItems.add(exitItem);
        
        //Sets the selected colors.
        for(PaintableEntity item : menuItems) {
            if(item instanceof MenuItemEntity) {
                ((MenuItemEntity) item).setColor(MENU_COLOR);
            }
        }
    }
    
    @Override
    public List<PaintableEntity> getEntities() 
    {
        List<PaintableEntity> entities = new ArrayList<>();    
        
        //Adds menu items.
        entities.addAll(menuItems);
        
        return entities;
    }
    
    @Override
    public MouseMotionListener getMouseMotionListener()
    {
        return mouseMotionListener;
    }
    
    @Override
    public MouseListener getMouseListener()
    {
        return mouseListener;
    }
    
    private MouseMotionListener createMouseMotionListener()
    {
        return MenuUtil.getMenuMouseMotionListener(this);
    }
    
    private MouseListener createMouseListener()
    {
        return MenuUtil.getMenuMouseListener(this);
    }
}