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
package se.devnordstrom.nordstromxball.logic;

import se.devnordstrom.nordstromxball.entity.EntityController;

/**
 *
 * @author Orville Nordström
 */
public class GameLoopController implements Runnable
{
    private long lastLoopTimeNanos, totalRepaints, totalFrames;
    
    private volatile int fps;
    
    private volatile boolean running, paused;
    
    private static final long TARGET_FPS = 60;
    private static final long OPTIMAL_TIME_NANOS = 1000_000_000 / TARGET_FPS;
    
    private volatile EntityController entityController;    
    
    private Runnable repaintRunnable;
    
    public GameLoopController(Runnable repaintRunnable)
    {
        this.repaintRunnable = repaintRunnable;
        lastLoopTimeNanos = System.nanoTime();
        running  = true;
    }
    
    @Override
    public void run() 
    {   
        while(isRunning()) {
            if(isPaused() || entityController == null) {
                lastLoopTimeNanos = System.nanoTime();
                
                sleep(1);
                continue;
            }
            
            totalFrames++;
            
            long currentNanos = System.nanoTime();
            long updateLengthNanos = currentNanos - lastLoopTimeNanos;
            
            double delta = updateLengthNanos / (double) 1000_000_000;
            
            this.entityController.moveEntities(delta);
            
            
            totalRepaints++;
            repaintRunnable.run();
            
            
            lastLoopTimeNanos = currentNanos;
            
            long delayTimeNanos = (lastLoopTimeNanos + OPTIMAL_TIME_NANOS) - currentNanos;
            sleep(delayTimeNanos/1000_000);
        }
    }
    
    private void sleep(long millis)
    {
        if(millis <= 0) millis = 1;
        
        try {
            Thread.sleep(millis);
        } catch(InterruptedException interEx) {
            //Ignore
        }
    }
    
    public void setRunning(boolean running)
    {
        this.running = running;
    }
    
    public boolean isRunning()
    {                                                                      
        return running;        
    }
    
    public void setPaused(boolean paused)
    {
        this.paused = paused;
    }
    
    public boolean isPaused()
    {
        return this.paused;
    }
    
    public EntityController getEntityController()
    {
        return entityController;
    }
    
    public void setEntityController(EntityController entityController)
    {
        this.entityController = entityController;
    }
    
    public int getFps()
    {
        return fps;
    }
}