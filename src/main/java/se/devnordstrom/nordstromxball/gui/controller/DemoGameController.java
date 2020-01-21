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

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import javax.swing.SwingUtilities;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;
import se.devnordstrom.nordstromxball.entity.TextEntity;
import se.devnordstrom.nordstromxball.entity.ball.Ball;
import se.devnordstrom.nordstromxball.entity.ball.VitalBall;
import se.devnordstrom.nordstromxball.entity.brick.BallBrick;
import se.devnordstrom.nordstromxball.entity.brick.Brick;
import se.devnordstrom.nordstromxball.entity.brick.InvisibleBrick;
import se.devnordstrom.nordstromxball.entity.brick.ToughBrick;
import se.devnordstrom.nordstromxball.entity.brick.WallBrick;
import se.devnordstrom.nordstromxball.entity.pad.Pad;
import se.devnordstrom.nordstromxball.entity.powerup.Powerup;
import se.devnordstrom.nordstromxball.logic.DefaultLevel;
import se.devnordstrom.nordstromxball.logic.Player;

/**
 *
 * @author Orville Nordström
 */
public class DemoGameController extends ScreenController 
{
    private double totalDeltaForSecond;
    
    private volatile double highestDelta = 0.1;
    
    private volatile double combinedDeltas;
    
    private volatile int fps, moves, movesForSecond;
    
    private static final int DEFAULT_SCREEN_MARGIN = 30;
    
    private final int screenWidth, screenHeight;
    
    private final Rectangle screenArea;
    
    private final Collection<Pad> pads;
    
    private final Collection<Brick> bricks;    
    
    private final Collection<Ball> balls, newBalls;
    
    private final Collection<Powerup> powerups, newPowerups;
    
    private final Random random;
    
    private final Player player;
    
    
    
    public DemoGameController(int screenWidth, int screenHeight) 
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        
        this.screenArea = new Rectangle(0, 0, screenWidth, screenHeight);
        
        Pad playerPad = new Pad();
        
        playerPad.setColor(Color.GREEN);
        
        playerPad.setY(screenHeight - DEFAULT_SCREEN_MARGIN);
        playerPad.setX(100);
        
        pads = new ArrayList<>();
        pads.add(playerPad);
        
        bricks = new ArrayList<>();
        
        balls = new ArrayList<>();
        newBalls = new ArrayList<>();
        
        powerups = new ArrayList<>(); 
        newPowerups = new ArrayList<>();
        
        
        random = new Random();
        
        player = new Player();
        
        setBricks();
        
        createBall();
    }
    
    private void setBricks() 
    {
        bricks.clear();

        //Adds the wall bricks.
        WallBrick leftWallBrick = new WallBrick(-10, 0, 10, screenHeight);
        
        WallBrick rightWallBrick = new WallBrick(screenWidth, 0, 10, screenHeight);
        
        WallBrick roofWallBrick = new WallBrick(0, -10, screenWidth, 10);
        
        bricks.add(leftWallBrick);
        bricks.add(rightWallBrick);
        bricks.add(roofWallBrick);

        DefaultLevel demoLevel = getDemoLevel();
        
        
        bricks.addAll(demoLevel.getBricks());
        
        /*
        
        //screenWidth is 700, screenHeight is 500
        int brickWidth = 50;
        int brickHeight = 25;
        
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 12; x++) {
                
                int brickX = brickWidth + (x * brickWidth);
                int brickY = brickHeight + (y * brickHeight);
                
                Brick brick;
                if(y % 3 == 0) {
                    brick = new ToughBrick(brickX, brickY, brickWidth, brickHeight);
                } else {
                    brick = new Brick(brickX, brickY, brickWidth, brickHeight);
                    / *
                        brick.setColor(Color.GREEN);
                        brick.setBorderColor(Color.BLACK);
                    * /
                }            
                
                bricks.add(brick);
            }
        }
        
        */
    }
    
    private void createBall() 
    {
        Ball ball = new Ball();
        
        ball.setX(screenWidth / 2.0);
        ball.setY(screenHeight - (DEFAULT_SCREEN_MARGIN * 3));
        ball.setColor(Color.GREEN);
        ball.setDiameter(10 + random.nextInt(26));
        ball.setSpeed(55 + random.nextInt(205));
        
        balls.add(ball);
    }
    
    private void createVitalBall()
    {
        Ball ball = new VitalBall();
        
        ball.setX(screenWidth / 2.0);
        ball.setY(screenHeight - (DEFAULT_SCREEN_MARGIN * 3));
        ball.setColor(Color.GREEN);
        ball.setDiameter(10 + random.nextInt(26));
        ball.setSpeed(55 + random.nextInt(205));
        
        balls.add(ball);
    }
    
    private void movePlayerPads(int x, int y) 
    {        
        for (Pad pad : pads) {
            //Using Math.max to ensure the Pad never goes outside the screen.
            int targetX = Math.max(x, pad.getWidth() / 2);
            int targetY = Math.max(y, pad.getHeight() / 2);
            
            int maxX = this.screenWidth - (pad.getWidth() / 2);
            int maxY = this.screenHeight - (pad.getWidth() / 2);
            
            if (maxX < targetX) {
                targetX = maxX;
            }
            
            if (maxY < targetY) {
                targetY = maxY;
            }
            
            pad.move(targetX, targetY);
        }
    }
    
    @Override
    public Collection<PaintableEntity> getEntities() 
    {
        Collection<PaintableEntity> paintableEntities = new ArrayList<>();
        
        paintableEntities.addAll(pads);
        
        paintableEntities.addAll(bricks);
        
        paintableEntities.addAll(getBalls());
        
        paintableEntities.addAll(getPowerUps());
        
        paintableEntities.addAll(getTextEntities());
        
        return paintableEntities;
    }
    
    private Collection<Ball> getBalls() 
    {   
        if(!newBalls.isEmpty()) {
            balls.addAll(newBalls);
            newBalls.clear();
        }
        
        return balls;
    }
    
    private void addBall(Ball ball)
    {
        newBalls.add(ball);
    }
    
    private Collection<Powerup> getPowerUps()
    {
        if(!newPowerups.isEmpty()) {
            powerups.addAll(newPowerups);
            newPowerups.clear();
        }
        
        return powerups;
    }
            
    private void addPowerUp(Powerup powerup)
    {        
        newPowerups.add(powerup);
    }
    
    private void activatePowerup(Powerup powerup)
    {
        System.out.println("Powerup activated!(Does nothing as of now)");   
    }
    
    // activatePowerup
    
    private Collection<TextEntity> getTextEntities() 
    {
        Collection<TextEntity> textEntities = new ArrayList<>();
        
        TextEntity pointText = new TextEntity();
        pointText.setColor(Color.WHITE);
        pointText.setX(DEFAULT_SCREEN_MARGIN);
        pointText.setY(DEFAULT_SCREEN_MARGIN);
        pointText.setText("Points: " + player.getPoints());
        textEntities.add(pointText);
        
        
        
        TextEntity fpsText = new TextEntity();
        fpsText.setText("FPS: " + fps);
        fpsText.setColor(Color.WHITE);
        fpsText.setX(DEFAULT_SCREEN_MARGIN);
        fpsText.setY(DEFAULT_SCREEN_MARGIN * 2);    
        textEntities.add(fpsText);
        
        
        
        TextEntity activeBallsText = new TextEntity();
        activeBallsText.setText("Balls: " + balls.size());
        activeBallsText.setColor(Color.WHITE);
        activeBallsText.setX(DEFAULT_SCREEN_MARGIN);
        activeBallsText.setY(DEFAULT_SCREEN_MARGIN * 3);
        textEntities.add(activeBallsText);
        
        
        
        return textEntities;
    }
    
    @Override
    public MouseListener getMouseListener() 
    {
        MouseListener mouseListener = new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                    usePowerUp();
                } else {
                    releaseBalls();
                }                
            }
        };
        
        return mouseListener;
    }
    
    @Override
    public MouseMotionListener getMouseMotionListener() 
    {
        MouseMotionListener mouseListener = new MouseMotionAdapter() {
            
            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                onMouseMoved(mouseEvent);
            }
            
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                onMouseMoved(mouseEvent);
            }
            
            private void onMouseMoved(MouseEvent mouseEvent) {
                movePlayerPads(mouseEvent.getX(), mouseEvent.getY());
            }
        };
        
        return mouseListener;
    }
    
    @Override
    public KeyListener getKeyListener() 
    {
        return null;
    }
    
    private void releaseBalls() 
    {
        createVitalBall();
    }
    
    private void usePowerUp() 
    {
        System.out.println("usePowerUp() started! (Does nothing as of now)");
    }
    
    @Override
    public void moveEntities(double delta) 
    {
        moves++;
        movesForSecond++;
        combinedDeltas += delta;
        totalDeltaForSecond += delta;
        
        if (totalDeltaForSecond >= 1.0) {
            fps = (int) Math.round(((double) movesForSecond) / totalDeltaForSecond);
        }
        
        if (moves != 0 && moves % 1000 == 0) {
            double averageDelta = combinedDeltas / (moves * 1.0);
        }
        
        if (delta > highestDelta) {
            highestDelta = delta;
        }
        
        Collection<PaintableEntity> entities = getEntities();
        for (PaintableEntity entity : entities) {
            entity.move(delta);
        }
                
        Collection<Ball> balls = getBalls();
        Iterator<Ball> ballsIterator = balls.iterator();
        while (ballsIterator.hasNext()) {
            Ball ball = ballsIterator.next();
            
            if (isOutsideOfScreen(ball.getHitbox())) {
                ballsIterator.remove();
                continue;
            }
            
            checkCollissions(ball);            
        }
        
        Collection<Powerup> powerups = this.getPowerUps();
        Iterator<Powerup> powerupsIterator = powerups.iterator();
        while(powerupsIterator.hasNext()) {
            Powerup powerup = powerupsIterator.next();
            
            if (isOutsideOfScreen(powerup.getHitbox())) {
                powerupsIterator.remove();
                continue;
            }
            
            for (Pad pad : pads) {
                if(pad.getHitBox().intersects(powerup.getHitbox())) {
                    player.addPoints(powerup.getPoints());
                    activatePowerup(powerup);
                    powerupsIterator.remove();
                    continue;
                }
            }
        }
    }
    
    private void checkCollissions(Ball ball) 
    {
        for (Brick brick : bricks) {
            if (ball.collidesWith(brick)) {
                //Now we know the brick is hit by the ball.
                player.addPoints(brick.hit());
                
                
                if(brick.hasPowerUp()) {
                    addPowerUp(brick.getPowerUp());
                }
                
                if(brick.isDestroyed() && brick instanceof BallBrick) {
                    BallBrick ballBrick = (BallBrick) brick;
                    Ball extraBall = ballBrick.releaseBall();
                    addBall(extraBall);
                }
            }
        }
        
        for (Pad pad : pads) {
            ball.collidesWith(pad);
        }
    }
    
    private boolean isOutsideOfScreen(Rectangle rectangle) 
    {
        return !screenArea.intersects(rectangle);        
    }
    
    public DefaultLevel getDemoLevel()
    {
        DefaultLevel demoLevel = new DefaultLevel();
        
        demoLevel.setLevelName("DemoLevel");
        demoLevel.setLevelNumber(-1);
        
        Brick[][] bricks = new Brick[DefaultLevel.BRICK_ROWS][DefaultLevel.BRICK_COLUMNS];
        
        for(int row = 5; row < bricks.length -3; row++) {
            for(int col = 3; col < bricks[row].length - 3; col++) {
                Brick brick = null;
                if(col % 2 == 0 || row % 2 == 0) {
                    if(random.nextInt(5) == 1) {
                        brick = new BallBrick();
                    } else {
                        brick = new ToughBrick();
                    }
                } else {
                    brick = new Brick();
                }
                
                bricks[row][col] = brick;
            }
        }
        
        demoLevel.setBricks(bricks);
        
        return demoLevel;
    }
}