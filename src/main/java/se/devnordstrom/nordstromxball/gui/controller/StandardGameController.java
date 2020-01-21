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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import javax.swing.SwingUtilities;
import se.devnordstrom.nordstromxball.entity.EntityController;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;
import se.devnordstrom.nordstromxball.entity.TextEntity;
import se.devnordstrom.nordstromxball.entity.TimedTextEntity;
import se.devnordstrom.nordstromxball.entity.ball.Ball;
import se.devnordstrom.nordstromxball.entity.ball.VitalBall;
import se.devnordstrom.nordstromxball.entity.brick.BallBrick;
import se.devnordstrom.nordstromxball.entity.brick.Brick;
import se.devnordstrom.nordstromxball.entity.brick.ToughBrick;
import se.devnordstrom.nordstromxball.entity.brick.WallBrick;
import se.devnordstrom.nordstromxball.entity.pad.Pad;
import se.devnordstrom.nordstromxball.entity.powerup.Powerup;
import se.devnordstrom.nordstromxball.entity.powerup.PowerupKind;
import se.devnordstrom.nordstromxball.logic.Game;
import se.devnordstrom.nordstromxball.logic.DefaultLevel;
import se.devnordstrom.nordstromxball.logic.Level;
import se.devnordstrom.nordstromxball.logic.Player;

/**
 *
 * @author Orville Nordström
 */
public class StandardGameController extends ScreenController implements EntityController
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
    
    private final Collection<Ball> balls, newBalls, ballsToRemove;
    
    private final Collection<Powerup> powerups, newPowerups;
    
    private final Collection<PaintableEntity> extraEntities;
    
    private final Game game;
    
    private final Random random;
    
    private final Player player;
    
    private Level currentLevel;
    
    public StandardGameController(Game game, int screenWidth, int screenHeight) 
    {
        this.game = game;
        
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        
        this.screenArea = new Rectangle(0, 0, screenWidth, screenHeight);
        
        Pad playerPad = new Pad();
        
        playerPad.setColor(Color.GREEN);
        
        //playerPad.setSticky(true);
        
        playerPad.setY(screenHeight - DEFAULT_SCREEN_MARGIN);
        
        /*
            playerPad.setY(screenHeight - DEFAULT_SCREEN_MARGIN);
            playerPad.setX(100);
        */
 
        //playerPad.move(startingX, startingY);
        
        pads = new ArrayList<>();
        pads.add(playerPad);
        
        bricks = new ArrayList<>();
        balls = new ArrayList<>();
        newBalls = new ArrayList<>();
        ballsToRemove = new ArrayList<>();
        powerups = new ArrayList<>(); 
        newPowerups = new ArrayList<>();
        extraEntities = new ArrayList<>();
        
        random = new Random();
        player = new Player();
            
        startGame();
    }
    
    public void startGame()
    {
        if(game.hasNextLevel()) {
            setLevel(game.nextLevel());
        }
    }
    
    private void setLevel(Level level)
    {        
        currentLevel = level;
        
        balls.clear();
        newBalls.clear();
        
        powerups.clear();
        newPowerups.clear();
        
        movePlayerPads(0, 0);
        movePlayerPads(screenWidth/2, 0);
            
        resetPads();
        
        this.prepareForServ();
    }
    
    private void resetPads()
    {
        for(Pad pad : this.getPads()) {    
            if(pad != null) pad.reset();
        }
    }
    
    private void prepareForServ()
    {   
        for(Pad pad : this.getPads()) {        
            Ball ball = new Ball();
            
            int ballX = (int) Math.round(pad.getX() + (pad.getWidth() * 0.60));
            int ballY = pad.getY() - ball.getDiameter();
            
            ball.setY(ballY);
            ball.setX(ballX);
            
            pad.attachBall(ball);
            
            this.addBall(ball);
            break;
        }
    }
    
    private void createBall() 
    {
        prepareForServ();
    }
    
    private void movePlayerPads(int x, int y) 
    {   
        for (Pad pad : pads) {
            //Using Math.max to ensure the Pad never goes outside the screen to the left or up.
            int targetX = Math.max(x, pad.getWidth() / 2);
            int targetY = Math.max(y, pad.getHeight() / 2);
            
            int maxX = this.screenWidth - (pad.getWidth() / 2);
            int maxY = this.screenHeight - (pad.getWidth() / 2);
            
            
            targetX = Math.min(targetX, maxX);
            targetY = Math.min(targetY, maxY);
            
            
            pad.move(targetX, targetY);
        }
    }
    
    @Override
    public Collection<PaintableEntity> getEntities() 
    {       
        Collection<PaintableEntity> paintableEntities = new ArrayList<>();
        
        
        paintableEntities.addAll(getPads());

        paintableEntities.addAll(getBricks());

        paintableEntities.addAll(getBalls());

        paintableEntities.addAll(getPowerUps());

        paintableEntities.addAll(getTextEntities());

        
        return paintableEntities;
    }
    
    private Collection<Pad> getPads()
    {
        return pads;
    }
    
    private Collection<Brick> getBricks()
    {        
        Collection<Brick> bricks = new ArrayList<>();
        
        bricks.addAll(getWallBricks());
        
        if(currentLevel != null) {
            bricks.addAll(currentLevel.getBricks());            
        }
        
        return bricks;
    }
    
    private Collection<Brick> getWallBricks()
    {
        Collection<Brick> wallBricks = new ArrayList<>();
        
        //Adds the wall bricks.
        WallBrick leftWallBrick = new WallBrick(-10, 0, 10, screenHeight);
        
        WallBrick rightWallBrick = new WallBrick(screenWidth, 0, 10, screenHeight);
        
        WallBrick roofWallBrick = new WallBrick(0, -10, screenWidth, 10);
        
        wallBricks.add(leftWallBrick);
        wallBricks.add(rightWallBrick);
        wallBricks.add(roofWallBrick);
        
        return wallBricks;
    }
    
    private void addBall(Ball ball)
    {
        newBalls.add(ball);
    }
    
    private Collection<Ball> getBalls() 
    {   
        if(!newBalls.isEmpty()) {
            balls.addAll(newBalls);
            newBalls.clear();
        }
        
        if(!ballsToRemove.isEmpty()) {
            balls.removeAll(ballsToRemove);
            ballsToRemove.clear();
        }
        
        return new ArrayList<>(balls);
    }
    
    private void removeBall(Ball ball)
    {        
        ballsToRemove.add(ball);
    }
        
    private Collection<Powerup> getPowerUps()
    {
        if(!newPowerups.isEmpty()) {
            powerups.addAll(newPowerups);
            newPowerups.clear();
        }
        
        return powerups;
    }
            
    private void addPowerup(Powerup powerup)
    {            
        if(powerup.getPowerUpKind() == PowerupKind.RANDOM) {
            PowerupKind[] powerUpKinds = PowerupKind.values();
            int kindIndex = random.nextInt(powerUpKinds.length);
            
            powerup.setPowerUpKind(powerUpKinds[kindIndex]);
        }
        
        newPowerups.add(powerup);
    }
    
    private void activatePowerup(Powerup powerup)
    {
        switch(powerup.getPowerUpKind()) {
            case REVEAL_INVISIBLE:
                hitInvisibleBricks();
                break;
            case SPLIT_BALLS:
                splitBalls();
                break;
            case STICKY_PAD:
                setStickyPads();
                break;
            case DOUBLE_SPEED:
                modifyBallSpeed(2.0);
                break;
            case HALF_SPEED:
                modifyBallSpeed(0.5);
                break;
            case EXTRA_LIFE:
                addExtraLife();
                break;
            case BIGGER_PAD:
                setPadWidthMod(1.2);
                break;
            case SMALLER_PAD:
                setPadWidthMod(0.5);
                break;
            default:
                break;            
        }        
    }
       
    private void hitInvisibleBricks()
    {
        Collection<Brick> bricks = this.getBricks();
        
        int hitBricks = 0;
        for(Brick brick : bricks) {
            if(!brick.isVisible()) {
                brick.hit();
                hitBricks++;
            }
        }   
    }    
    
    private void splitBalls()
    {        
        Collection<Ball> existingBalls = this.getBalls();
        
        Collection<Ball> newBalls = new ArrayList<>();    
               
        for(Ball existingBall : existingBalls) {
            if(existingBall.isAttached()) {
                continue;
            }
            
            Ball extraBall = new Ball();
            extraBall.setSpeed(existingBall.getSpeed());
            extraBall.setDiameter(existingBall.getDiameter());
            extraBall.setLethal(existingBall.isLethal());
            extraBall.setVital(existingBall.isVital());
            extraBall.setColor(existingBall.getColor());
            extraBall.setX(existingBall.getX());
            extraBall.setY(existingBall.getY());
            extraBall.setYspeedMod(existingBall.getYspeedMod());
            extraBall.setXspeedMod(existingBall.getXspeedMod());
            extraBall.setyDir(existingBall.getyDir());
            extraBall.setxDir(existingBall.getxDir());
            
            extraBall.invertxDir();
              
            newBalls.add(extraBall);            
        }
                
        for(Ball newBall : newBalls) {
            addBall(newBall);
        }
    }
    
    private void setStickyPads()
    {
        Collection<Pad> pads = this.getPads();
        
        for(Pad pad : pads) {
            if(pad != null) pad.setSticky(true);
        }
    }
    
    private void modifyBallSpeed(double speedModifyer)
    {
        Collection<Ball> balls = this.getBalls();
        
        for(Ball ball : balls) {
            double newSpeed = ball.getSpeed() * speedModifyer;
            
            ball.setSpeed(newSpeed);
        }
    }
    
    private void addExtraLife()
    {
        this.player.addLife();
    }
    
    private void killPlayer()
    {
        this.player.takeLife();
    }
    
    private void setPadWidthMod(double widthMod)
    {
        Collection<Pad> pads = this.getPads();
        
        for(Pad pad : pads) {            
            int newWidth = (int) Math.round(pad.getWidth()*widthMod);
            
            pad.setWidth(newWidth);
        }
    }
    
    private Collection<TextEntity> getTextEntities() 
    {
        Collection<TextEntity> textEntities = new ArrayList<>();
        
        TextEntity pointText = new TextEntity();
        pointText.setColor(Color.WHITE);
        pointText.setX(DEFAULT_SCREEN_MARGIN);
        pointText.setY(DEFAULT_SCREEN_MARGIN);
        pointText.setText("Points: " + player.getPoints());
        textEntities.add(pointText);
        
        
        TextEntity lifeText = new TextEntity();
        lifeText.setColor(Color.WHITE);
        lifeText.setX(DEFAULT_SCREEN_MARGIN * 4);
        lifeText.setY(DEFAULT_SCREEN_MARGIN);
        lifeText.setText("Life: " + player.getLives());
        textEntities.add(lifeText);
        
        
        
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
        
        
        
        String infoText;
        if(currentLevel == null) {
            infoText = "currentLevel is Null.";
        } else {
            infoText = "#"+currentLevel.getLevelNumber()+" "+currentLevel.getLevelName();
        }
        
                
        
        TextEntity levelInfoText = new TextEntity();
        levelInfoText.setText(infoText);
        levelInfoText.setColor(Color.WHITE);
        levelInfoText.setX(DEFAULT_SCREEN_MARGIN);
        levelInfoText.setY(DEFAULT_SCREEN_MARGIN * 4);
        textEntities.add(levelInfoText);

        
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
                    useAction();
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

    
    private void usePowerUp() 
    {        
        for(Pad pad : this.getPads()) {
            pad.releaseBalls();
        }
    }
    
    private void useAction()
    {
        this.createBall();
    }
    
    private void setNextLevel()
    {
        if(game.hasNextLevel()) {
            this.setLevel(game.nextLevel());
        } else {
            this.setLevel(null);
        }
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
        
        /*
        if(!balls.isEmpty()) {
            this.killPlayer();
            return;
        }
        */
        
        for(Ball ball : balls) {
            if (isOutsideOfScreen(ball.getHitbox())) {
                removeBall(ball);
                continue;
            }
            
            if(ball.isAttached()) {
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
        
        if(currentLevel != null && currentLevel.isCleared()) {
            setNextLevel();
        }
    }
    
    private void checkCollissions(Ball ball) 
    {
        Collection<Brick> bricks = this.getBricks();
        
        for (Brick brick : bricks) {
            if (ball.collidesWith(brick)) {
                //Now we know the brick is hit by the ball.
                player.addPoints(brick.hit());
                if(brick.hasPowerUp()) {
                    addPowerup(brick.getPowerUp());
                }
                
                if(brick.isDestroyed() && brick instanceof BallBrick) {
                    if(random.nextInt(3) == 1) {
                        BallBrick ballBrick = (BallBrick) brick;
                        Ball extraBall = ballBrick.releaseBall();
                        addBall(extraBall);
                    }
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
}