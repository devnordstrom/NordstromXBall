/*
 * Copyright (C) 2019 Orville Nordström
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

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.Collection;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;
import se.devnordstrom.nordstromxball.entity.EntityController;

/**
 *
 * @author Orville Nordström
 */
public abstract class ScreenController implements EntityController
{
    public abstract Collection<PaintableEntity> getEntities();
    
    /**
     * Used if there are entities to move such as sprites.
     * @param delta Used for calculating how far an entity should move on the screen. 
     * If the entity speed is set to 20 then the position should be incremented by speed * delta.
     */
    @Override
    public void moveEntities(double delta) 
    {
        //This should be overriden if there are entities to move.
    }
    
    /**
     * Override if the ScreenController uses a MouseListener.
     * 
     * @return 
     */
    public MouseListener getMouseListener()
    {
        return null;
    }
    
    /**
     * Override if the ScreenController uses a MouseMotionListener.
     * 
     * @return 
     */
    public MouseMotionListener getMouseMotionListener()
    {
        return null;
    }
    
    /**
     * Override if the ScreenController uses a MouseWheelListener.
     * 
     * @return 
     */
    public MouseWheelListener getMouseWheelListener()
    {
        return null;
    }
    
    /**
     * Override if the ScreenController uses a KeyListener.
     * 
     * @return 
     */
    public KeyListener getKeyListener()
    {
        return null;
    }
}