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

import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.ArrayList;
import se.devnordstrom.nordstromxball.entity.MenuItemEntity;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;
import se.devnordstrom.nordstromxball.entity.TextEntity;
import se.devnordstrom.nordstromxball.util.MenuUtil;

/**
 *
 * @author Orville N
 */
public class TextScreenController extends ScreenController
{
    public static final int MENU_EXIT_ITEM_X = 600;
    
    public static final int TEXT_VERTICAL_MARGIN = 24;    
    
    private final String text;
    
    private final MouseListener mouseListener;
    
    private final MouseMotionListener mouseMotionListener;
    
    private final List<PaintableEntity> entities;
    
    private final Runnable goToMenuRunnable, exitRunnable;
    
    private int horizontalMargin, verticalMargin;
    
    /**
     * 
     * @param text
     * @param goToMenuRunnable
     * @param exitRunnable
     */
    public TextScreenController(String text,
            Runnable goToMenuRunnable,
            Runnable exitRunnable)
    {
        if(null == text)
            throw new NullPointerException();
        
        if(text.trim().isEmpty())
            throw new IllegalArgumentException("The helpText must not be empty!");
        
        this.verticalMargin = MenuController.MARGIN_VERTICAL;
        this.horizontalMargin = MenuController.MARGIN_HORIZONTAL;
        this.text = text;
        this.mouseListener = createMouseListener();
        this.mouseMotionListener = createMouseMotionListener();
        this.entities = new ArrayList<>();
        this.goToMenuRunnable = goToMenuRunnable;
        this.exitRunnable = exitRunnable;
        
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
        
        
        //Adds all the text items.
        entities.addAll(getTextItems());
    }
    
    protected List<TextEntity> getTextItems()
    {   
        List<TextEntity> entities = new ArrayList<>();
        
        //Sets the help text to different rows.
        String[] textRows = text.split("\n");
        
        int itemMargin = TEXT_VERTICAL_MARGIN;
        int y = MenuController.MARGIN_VERTICAL + (itemMargin*2);
        int x = MenuController.MARGIN_HORIZONTAL;
        
        for(String text : textRows) {
            if(!text.trim().isEmpty()) {
                TextEntity textItem = createTextItem(x, y, text);
                entities.add(textItem);
            }
            
            y += itemMargin;
        }
        
        return entities;
    }
    
    protected TextEntity createTextItem(int x, int y, String text)
    {
        return MenuUtil.createTextItem(x, y, text);
    }
    
    @Override
    public List<PaintableEntity> getEntities() 
    {
        return entities;
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
    
    public MouseMotionListener createMouseMotionListener()
    {
        return MenuUtil.getMenuMouseMotionListener(this);
    }
    
    public MouseListener createMouseListener()
    {
        return MenuUtil.getMenuMouseListener(this);
    }
}