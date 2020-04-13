/*
 * Copyright (C) 2020 Orville Nordström
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
    public static final boolean DEBUG_FPS = false;
    
    private static final long MIN_SLEEP_MILLIS = 1;
    
    private long lastLoopTimeNanos, totalRepaints,
            totalFrames, lastFpsTimeMs, lastFpsCount;
    
    private volatile int fps;
    
    private volatile boolean running, paused;
    
    private volatile EntityController entityController;    
    
    private final Runnable repaintRunnable;
    
    public GameLoopController(Runnable repaintRunnable)
    {
        this.repaintRunnable = repaintRunnable;
        lastLoopTimeNanos = System.nanoTime();
        running  = true;
    }
    
    @Override
    public void run() 
    {   
        lastFpsTimeMs = System.nanoTime();
        
        while(isRunning()) {
            if(isPaused() || entityController == null) {
                lastLoopTimeNanos = System.nanoTime();
                
                sleep(1);
                continue;
            }
            
            totalFrames++;
            lastFpsCount++;
            
            if(DEBUG_FPS && (System.nanoTime() - lastFpsTimeMs) >= (1000_000_000)) {
                fps = (int) lastFpsCount;
                lastFpsCount = 0;
                lastFpsTimeMs = System.nanoTime();
                System.out.println("FPS: "+fps);
            }
            
            long currentNanos = System.nanoTime();
            long updateLengthNanos = currentNanos - lastLoopTimeNanos;
            
            double delta = (updateLengthNanos * 1.0) / (1000_000_000 * 1.0);
            
            entityController.moveEntities(delta);
            
            totalRepaints++;
            repaintRunnable.run();
            
            lastLoopTimeNanos = currentNanos;
            
            
            //Added to prevent the computer from freezing.
            sleep(MIN_SLEEP_MILLIS);
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