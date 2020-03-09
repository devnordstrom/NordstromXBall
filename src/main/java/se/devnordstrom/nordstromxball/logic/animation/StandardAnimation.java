/*
 * Copyright (C) 2020 User
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
package se.devnordstrom.nordstromxball.logic.animation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;

/**
 *
 * @author Orville N
 */
public class StandardAnimation implements Animation 
{
    private static final long DEFAULT_ANIMATION_RUN_TIME_MS = 3 * 1000;
    
    private static final double DEFAULT_OPACITY = 1.0;
    
    private double opacity;
    
    private boolean started, movementPaused;
    
    private long animationRunTimeMs, startedTimeMs;
    
    private final List<PaintableEntity> entities;
    
    public StandardAnimation()
    {   
        opacity = DEFAULT_OPACITY;
        animationRunTimeMs = DEFAULT_ANIMATION_RUN_TIME_MS;
        entities = new ArrayList<>();
    }
    
    /**
     * Starts the animation,
     * 
     * note that this animation will only start once.
     */
    public void start()
    {
        if(started) return;
        
        started = true;
        startedTimeMs = System.currentTimeMillis();
    }
    
    public boolean hasStarted()
    {
        return started;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public boolean isActive() 
    {
        return started 
                && (System.currentTimeMillis() - startedTimeMs) < animationRunTimeMs;
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean isMovementPaused() 
    {
        return this.movementPaused;
    }

    /**
     * 
     * @param movementPaused 
     */
    public void setMovementPaused(boolean movementPaused)
    {
        this.movementPaused = movementPaused;
    }
    
    /**
 * 
     * @return 
     */
    @Override
    public double getOpacity() 
    {
        return this.opacity;
    }

    /**
     * @param opacity the opacity to set
     */
    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }
    
    @Override
    public Collection<PaintableEntity> getEntities() 
    {
        return entities;
    }
    
    public void addEntity(PaintableEntity entity)
    {
        entities.add(entity);
    }
}