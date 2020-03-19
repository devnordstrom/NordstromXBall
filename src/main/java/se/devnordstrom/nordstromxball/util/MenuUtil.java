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
package se.devnordstrom.nordstromxball.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.List;
import se.devnordstrom.nordstromxball.entity.EntityController;
import se.devnordstrom.nordstromxball.entity.MenuItemEntity;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;


/**
 *
 * @author Orville N
 */
public class MenuUtil {

    public static String DEFAULT_FONT_NAME = "Tahoma";
    
    public static final Color MENU_COLOR = Color.WHITE;
    
    private static final Color FOCUSED_MENU_COLOR = Color.GRAY;
    
    private static final Color ACTIVE_MENU_COLOR = Color.DARK_GRAY;
    
    private static final Color DISABLED_MENU_COLOR = new Color(50, 50, 50);
    
    public static final Font MENU_ITEM_FONT = new Font(DEFAULT_FONT_NAME, Font.PLAIN, 30);
    
    public static final int MENU_ITEM_WIDTH = 200;
    
    public static final int MENU_ITEM_HEIGHT = 50;
    
    private static final int MENU_TEXT_MARGIN_X = 0;
    
    private static final int MENU_TEXT_MARGIN_Y = 0;
    
    /**
     * 
     * @param x
     * @param y
     * @param text
     * @param action
     * @return 
     */
    public static MenuItemEntity createMenuItemEntity(int x, int y, 
            String text, Runnable action) {
        Rectangle menuBody = new Rectangle(x, y, MENU_ITEM_WIDTH, MENU_ITEM_HEIGHT);
        
        return createMenuItemEntity(menuBody, text, action);
    }  
    
    /**
     * 
     * @param rect
     * @param text
     * @param action
     * @return 
     */
    public static MenuItemEntity createMenuItemEntity(Rectangle rect, 
            String text, Runnable action) {
        
        int x = (int) rect.getX();
        int y = (int) rect.getY();
        
        MenuItemEntity menuItem = new MenuItemEntity();
        
        menuItem.setX(x);
        menuItem.setY(y);
                
        //Sets text properties.
        menuItem.setText(text);
        menuItem.setTextMarginX(MENU_TEXT_MARGIN_X);
        menuItem.setTextMarginY(MENU_TEXT_MARGIN_Y);
        menuItem.setColor(MENU_COLOR);
        menuItem.setFocusedColor(FOCUSED_MENU_COLOR);
        menuItem.setActiveColor(ACTIVE_MENU_COLOR);
        menuItem.setDisabledColor(DISABLED_MENU_COLOR);
        menuItem.setFont(MENU_ITEM_FONT);
        
        //Sets background properties.
        Rectangle background = new Rectangle(x, y, 
                (int) rect.getWidth(), (int) rect.getHeight());
        
        menuItem.setBackground(background);
        menuItem.setFillBackground(false);
        menuItem.setBackgroundColor(null);
        menuItem.setFocusedBackgroundColor(null);
        menuItem.setActiveBackgroundColor(null);
        
        menuItem.setAction(action);
        
        return menuItem;
        
    }
    
    /**
     * 
     * @param controller
     * @return 
     */
    public static MouseMotionListener getMenuMouseMotionListener(EntityController controller) {
        return new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent mouseEvent) {                                
                manageSetMenuItemFocused(mouseEvent.getX(), mouseEvent.getY(), 
                        controller.getEntities());
            }
            
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                //This implementation makes no distinction between mouseMoved or mouseDragged.
                mouseMoved(mouseEvent);
            }
        };   
    }
    
    /**
     * 
     * @param controller
     * @return 
     */
    public static MouseListener getMenuMouseListener(EntityController controller) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                System.out.println("mousePressed("+mouseEvent.getX() 
                        + "," +mouseEvent.getY()+") started!");
                
                manageSetMenuItemActive(mouseEvent.getX(), mouseEvent.getY(), 
                        true, controller.getEntities());
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                manageSetMenuItemActive(mouseEvent.getX(), mouseEvent.getY(),
                        false, controller.getEntities());
            }            
        }; 
    }
    
/**
     * 
     * @param x
     * @param y
     * @param entityList 
     */
    public static void manageSetMenuItemFocused(int x, int y, 
            List<PaintableEntity> entityList) {
        
        for(PaintableEntity entity : entityList) {

            if(!(entity instanceof MenuItemEntity)) {
                continue;
            }

            MenuItemEntity menuItemEntity = (MenuItemEntity) entity;
            
            if(menuItemEntity.isDisabled()) {
                continue;
            }
            
            boolean focused = menuItemEntity.isOverlapping(x, y);
            
            menuItemEntity.setFocused(focused);
            
        }
        
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param active 
     *      if true then the MenuItemEntities will be set to be activ
     *      if the cursor overlaps the menu item, otherwise it will set all the
     *      MenuItems provided to be inactive.
     * @param entityList 
     */
    public static void manageSetMenuItemActive(int x, int y, boolean active, 
            List<PaintableEntity> entityList) {
        for(PaintableEntity entity : entityList) {

            if(!(entity instanceof MenuItemEntity)) {
                continue;
            }

            MenuItemEntity menuItemEntity = (MenuItemEntity) entity;
            
            if(menuItemEntity.isDisabled()) {
                continue;
            }
            
            boolean menuItemActive = active ? menuItemEntity.isOverlapping(x, y) : false;
            
            if(menuItemActive) {
                System.out.println("menuItemActive is TRUE!");
            }
            
            menuItemEntity.setActive(menuItemActive);
            
            if(menuItemActive && menuItemEntity.getAction() != null) {
                System.out.println("Now running the action for menu item!");
                menuItemEntity.getAction().run();
            }
        }
    }
    
}