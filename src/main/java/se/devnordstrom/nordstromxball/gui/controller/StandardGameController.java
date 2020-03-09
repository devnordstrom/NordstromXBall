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
import se.devnordstrom.nordstromxball.gui.MainJFrame;
import se.devnordstrom.nordstromxball.logic.Game;
import se.devnordstrom.nordstromxball.logic.DefaultLevel;
import se.devnordstrom.nordstromxball.logic.Level;
import se.devnordstrom.nordstromxball.logic.Player;
import se.devnordstrom.nordstromxball.logic.animation.Animation;
import se.devnordstrom.nordstromxball.logic.animation.StandardAnimation;
import se.devnordstrom.nordstromxball.logic.sound.AudioController;

/**
 *
 * @author Orville Nordström
 */
public class StandardGameController extends ScreenController implements EntityController
{   
    private static final boolean ENABLE_MOUSE_BALL_SPAWNING = false;
    
    private static final int DEFAULT_SCREEN_MARGIN = 30;
    
    private boolean gameOver, resetLevel, setNextLevel, playerWaitingToServe;
    
    private double totalDeltaForSecond;
    
    private volatile double highestDelta = 0.1;
    
    private volatile double combinedDeltas;
    
    private volatile int fps, moves, movesForSecond;
    
    private final int screenWidth, screenHeight;

    private final Rectangle screenArea;
    
    private final Collection<Pad> pads;
        
    private final Collection<Ball> balls, newBalls, ballsToRemove;
    
    private final Collection<Powerup> powerups, newPowerups;
    
    private final Game game;
    
    private final Random random;
    
    private final Player player;
    
    private Level currentLevel;
        
    private Collection<Animation> currentAnimations = new ArrayList<>();
    
    public StandardGameController(Game game) 
    {
        this.game = game;
        this.screenWidth = MainJFrame.DEFAULT_WIDTH;
        this.screenHeight = MainJFrame.DEFAULT_HEIGHT;        
        this.screenArea = new Rectangle(0, 0, screenWidth, screenHeight);
        
        pads = new ArrayList<>();
        balls = new ArrayList<>();
        newBalls = new ArrayList<>();
        ballsToRemove = new ArrayList<>();
        powerups = new ArrayList<>(); 
        newPowerups = new ArrayList<>();
        random = new Random();
        player = new Player();
            
        initPlayerPad();
        setNextLevel();
    }
    
    private void initPlayerPad()
    {
        Pad playerPad = new Pad();
        
        playerPad.setColor(Color.GREEN);
        playerPad.setY(screenHeight - DEFAULT_SCREEN_MARGIN);
        
        pads.add(playerPad);
    }
            
    private void setLevel(Level level)
    {   
        currentLevel = level;
                
        resetLevel();
    }
    
    private void resetLevel()
    {
        clearBalls();
        
        clearPowerups();
                    
        resetPads();
    }
    
    private void resetPads()
    {
        movePlayerPads(0, 0);
        movePlayerPads(screenWidth/2, 0);
        
        for(Pad pad : this.getPads()) {    
            if(pad != null) pad.reset();
        }
        
        prepareForServ();
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
        
        playerWaitingToServe = true;
    }
    
    private boolean isPlayerWaitingToServe()
    {
        return playerWaitingToServe;
    }
    
    private void createBall() 
    {
        prepareForServ();
    }
    
    private void movePlayerPads(int x, int y) 
    {   
        if(isMovementPaused()) return;
        
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
    
    protected boolean isGameOver()
    {
        return gameOver;
    }
    
    private void setGameOver()
    {
        gameOver = true;
    }
    
    private Collection<TextEntity> getGameOverEntities()
    {
        Collection<TextEntity> textEntities = new ArrayList<>();
        
        TextEntity pointText = new TextEntity();
        pointText.setColor(Color.WHITE);
        pointText.setX(screenWidth/3);
        pointText.setY(screenHeight / 3);
        pointText.setText("GAMEOVER MAN, GAMEOVER :( Better luck next time :(");
        
        textEntities.add(pointText);
        return textEntities;
    }
    
    @Override
    public Collection<PaintableEntity> getEntities() 
    {       
        Collection<PaintableEntity> paintableEntities = new ArrayList<>();
        
        if(isGameOver()) {
            paintableEntities.addAll(getGameOverEntities());
            return paintableEntities;
        }
        
        for(Animation animation : currentAnimations) {
            if(animation.isActive()) {
                paintableEntities.addAll(animation.getEntities());
            }
        }
                
        Collection<Ball> balls = getBalls();
        
        paintableEntities.addAll(getPads());
        paintableEntities.addAll(getBricks());
        paintableEntities.addAll(balls);
        paintableEntities.addAll(getPowerUps());
        paintableEntities.addAll(getTextEntities());
        
        if(isPlayerWaitingToServe()) {
            paintableEntities.add(getHelpTextEntity("Press the right mouse button to serve."));
        }
        
        return paintableEntities;
    }
    
    
    
    private Collection<Pad> getPads()
    {
        return pads;
    }
    
    private Pad getMainPad()
    {
        Collection<Pad> pads = getPads();
        
        Pad playerPad = null;
            
        Iterator<Pad> iterator = pads.iterator();
        if(iterator.hasNext()) {
            playerPad = iterator.next();
        }
        
        return playerPad;
    }
    
    private boolean isLevelCleared()
    {
        return currentLevel != null && currentLevel.isCleared();
    }
    
    private Collection<Brick> getBricks()
    {   
        Collection<Brick> bricks = new ArrayList<>();
                
        if(currentLevel != null) {
            bricks.addAll(currentLevel.getBricks());            
        }
        
        return bricks;
    }
    
    
    
    private void addBall(Ball ball)
    {
        newBalls.add(ball);
    }
        
    private void killPlayer()
    {
        player.takeLife();
        
        if(player.getLifeCount() <= 0) {
            setGameOver();
        }
        
        setKillPlayerAnimation();
        
        resetLevel = true;
    }
    
    private void setKillPlayerAnimation()
    {
        StandardAnimation animation = new StandardAnimation();
        
        animation.setMovementPaused(true);
        
        TextEntity entity = createTextAbovePad("Life lost!");       
        
        animation.addEntity(entity);        
        animation.start();
        
        addAnimation(animation);
    }
        
    private TextEntity getHelpTextEntity(String text)
    {
        if(text == null) throw new NullPointerException("The text must not be null.");
        
        TextEntity entity = new TextEntity();
        
        entity.setBold();
        entity.setColor(Color.WHITE);
        entity.setText(text);
        
        entity.setX(screenWidth / 3);
        entity.setY(screenHeight - (DEFAULT_SCREEN_MARGIN * 3));
        
        return entity;
    }
    
    private TextEntity createTextAbovePad(String text)
    {
        if(text == null) throw new NullPointerException("The text must not be null.");
        
        TextEntity entity = new TextEntity();
        entity.setBold();
        entity.setColor(Color.WHITE);
        entity.setText(text);
        
        int textX = screenWidth / 3;
        
        Pad mainPad = getMainPad();
        if(mainPad != null) {
            textX = mainPad.getX();
        }
        
        entity.setX(textX);
        entity.setY(screenHeight - (DEFAULT_SCREEN_MARGIN * 2));
        
        return entity;
    }
    
    private void setAnimationForNextLevel()
    {
        StandardAnimation animation = new StandardAnimation();
        
        animation.setMovementPaused(true);
        
        TextEntity entity = new TextEntity();
        
        entity.setBold();
        entity.setColor(Color.WHITE);
        entity.setText("LEVEL CLEARED!");
        
        entity.setX(screenWidth/3);
        entity.setY(screenHeight/3);
        animation.addEntity(entity);        
        
        animation.start();
        
        addAnimation(animation);
    }
    
    private void addAnimation(Animation animation)
    {
        currentAnimations.add(animation);
    }
    
    private void clearBalls()
    {
        newBalls.clear();
        balls.clear();
    }
   
    private void clearPowerups()
    {
        powerups.clear();
        newPowerups.clear();
    }
    
    @Deprecated //Not thread safe.
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
    
    private void addPowerupAnimation(Powerup powerup)
    {
        System.out.println("addPowerupAnimation('"+powerup.toString()+"') started!");
        
        StandardAnimation animation = new StandardAnimation();
        
        animation.setMovementPaused(false);
        
        TextEntity textEntity = this.createTextAbovePad(powerup.toString());
        
        animation.addEntity(textEntity);
        
        animation.start();
        
        addAnimation(animation);
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
                setPadWidthMod(0.7);
                break;
            default:
                break;            
        }

        addPowerupAnimation(powerup);
    }
       
    /**
     * Hits all invisible bricks and returns the number of
     * bricks that have been hit.
     * 
     * @return 
     */
    private int hitInvisibleBricks()
    {
        Collection<Brick> bricks = this.getBricks();
        
        int hitBricks = 0;
        for(Brick brick : bricks) {
            if(!brick.isVisible()) {
                brick.hit();
                hitBricks++;
            }
        }
        
        return hitBricks;
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
        
        
        
        TextEntity lifeText = new TextEntity();
        lifeText.setColor(Color.WHITE);
        lifeText.setX(DEFAULT_SCREEN_MARGIN);
        lifeText.setY(DEFAULT_SCREEN_MARGIN);
        lifeText.setText("Lives: " + player.getLifeCount());
        textEntities.add(lifeText);
        
        
        
        TextEntity pointText = new TextEntity();
        pointText.setColor(Color.WHITE);
        pointText.setX(DEFAULT_SCREEN_MARGIN);
        pointText.setY(DEFAULT_SCREEN_MARGIN*2);
        pointText.setText("Points: " + player.getPoints());
        textEntities.add(pointText);
        
        
        
        String infoText;
        if(currentLevel == null) {
            infoText = "currentLevel is not set.";
        } else {
            infoText = "#"+currentLevel.getLevelNumber()+" "+currentLevel.getLevelName();
        }
        
        TextEntity levelInfoText = new TextEntity();
        levelInfoText.setText(infoText);
        levelInfoText.setColor(Color.WHITE);
        levelInfoText.setX(DEFAULT_SCREEN_MARGIN);
        levelInfoText.setY(DEFAULT_SCREEN_MARGIN * 3);
        textEntities.add(levelInfoText);

        
        
        return textEntities;
    }
    
    @Override
    public MouseListener getMouseListener() 
    {
        MouseListener mouseListener = new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                    releaseAttachedBalls();
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

    
    private void releaseAttachedBalls() 
    {        
        if(isMovementPaused()) return;
        
        for(Pad pad : this.getPads()) {
            pad.releaseBalls();
        }
        
        playerWaitingToServe = false;
    }
    
    private void useAction()
    {
        if(ENABLE_MOUSE_BALL_SPAWNING) {
            createBall();
        }
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
        
        if (delta > highestDelta) {
            highestDelta = delta;
        }
        
        if(isMovementPaused()) return;
        
        if(resetLevel) {
            resetLevel = false;
            resetLevel();
            return;
        }
        
        if(setNextLevel) {
            setNextLevel = false;
            setNextLevel();
            return;
        }
        
        //Moves all the entities in the game.
        Collection<PaintableEntity> entities = getEntities();
        for (PaintableEntity entity : entities) {
            entity.move(delta);
        }
                
        Collection<Ball> balls = getBalls();
        
        if(balls.isEmpty()) {
            this.killPlayer();
            return;
        }
        
        for(Ball ball : balls) {
            if(ball.isAttached()) {
                continue;
            }

            checkCollissions(ball);
            
            if (hasFallenOutsideScreen(ball.getHitbox())) {
                if(ball.isVital()) {
                    this.killPlayer();
                    return;
                }
                
                removeBall(ball);
                
                continue;
            }
        }
        
        Collection<Powerup> powerups = this.getPowerUps();
        
        Iterator<Powerup> powerupsIterator = powerups.iterator();
        while(powerupsIterator.hasNext()) {
            Powerup powerup = powerupsIterator.next();
            
            if(hasFallenOutsideScreen(powerup.getHitbox())) {
                powerupsIterator.remove();
                continue;
            }
            
            for(Pad pad : pads) {
                if(pad.getHitBox().intersects(powerup.getHitbox())) {
                    player.addPoints(powerup.getPoints());
                    activatePowerup(powerup);
                    powerupsIterator.remove();
                    continue;
                }
            }
        }
        
        if(isLevelCleared()) {
            setNextLevel = true;
            
            setAnimationForNextLevel();
        }
    }
    
    private void checkCollissions(Ball ball) 
    {   
        //Checks if the ball hits the ceiling of the level.
        if(ball.getY() <= screenArea.getY()) {
            AudioController.playBounceSound();
            ball.setY(screenArea.getY()+1);
            ball.invertyDir();
            ball.addBounce();
        }
        
        
        //Checks if the ball hits the left edge of the level.
        if(ball.getX() <= screenArea.getX()) {
            AudioController.playBounceSound();
            ball.setX(screenArea.getX()+1);
            ball.invertxDir();
            ball.addBounce();
        }
        
        
        //Checks if the ball hits the right edge of the level.
        if(ball.getX()+ball.getDiameter() >= screenArea.getX()+screenArea.getWidth()) {
            AudioController.playBounceSound();
            ball.setX((screenArea.getX()+screenArea.getWidth())-ball.getDiameter()-1);
            ball.invertxDir();
            ball.addBounce();
        }
        
        
        //Check collissions for all bricks.
        for (Brick brick : getBricks()) {
            if (ball.collidesWith(brick)) {
                player.addPoints(brick.hit());
                
                AudioController.playBounceSound();
                
                if(brick.hasPowerUp(random)) {
                    addPowerup(brick.getPowerUp());
                }
                
                if(brick.isDestroyed() && brick instanceof BallBrick) {
                    BallBrick ballBrick = (BallBrick) brick;
                    
                    /*
                        This should be refactored so that the brick will
                        release this as a powerup.
                    */
                    if(ballBrick.hasBall(random)) { 
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
    
    /**
     * Only 
     * 
     * @param rectangle
     * @return 
     */
    private boolean hasFallenOutsideScreen(Rectangle rectangle) 
    {        
        return screenArea.getHeight() < rectangle.getY();
    }
       
    /**
     * Evaluates if atleast one active animation causes the movement to be paused.
     * 
     * @return 
     */
    private boolean isMovementPaused()
    {
        for(Animation animation : currentAnimations) {
            if(animation.isActive() && animation.isMovementPaused()) {
                return true;
            }
        }
        
        return false;
    }
}