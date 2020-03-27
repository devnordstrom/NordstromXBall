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
package se.devnordstrom.nordstromxball.gui;

import java.awt.AlphaComposite;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;
import se.devnordstrom.nordstromxball.gui.controller.ScreenController;
import se.devnordstrom.nordstromxball.entity.PaintableEntity;
import se.devnordstrom.nordstromxball.logic.GameLoopController;

/**
 *
 * @author Orville Nordström
 */
public class MainJFrame extends javax.swing.JFrame 
{
    /**
     * Creates new form MainJFrame
     * 
     * @param title
     */
    public MainJFrame(String title) 
    {
        super(title);
        
        initComponents();
        
        Runnable repaintRunnable = () -> {
            SwingUtilities.invokeLater(()-> {
                gameScreenJPanel.repaint();
            });
        };      
        
        gameLoopController = new GameLoopController(repaintRunnable);
        
        gameLoopThread = new Thread(gameLoopController);
        gameLoopThread.setName("gameLoopThread");
    }
    
    private void paintGameScreen(Graphics g)
    {
        if(screenController == null) {
            return;
        }
        
        for(PaintableEntity entity : screenController.getEntities()) {
            if(entity != null) entity.paint(g);
        }
    }
    
    private void setOpacity(Graphics g, float opacity)
    {
        ((Graphics2D) g).setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, opacity));  
    }
    
    public void setScreenController(ScreenController screenController) 
    {
        this.screenController = screenController;

        removeEventListeners();

        if (screenController != null) {
            registerEventListeners(screenController);
            
            if(screenController.disableCursor()) {
                setHiddenCursor();
            } else {
                setDefaultCursor();
            }
        }
        
        gameLoopController.setEntityController(screenController);
        
        requestFocus();
    }

    private void removeEventListeners() 
    {
        for(MouseListener mouseListener : getMouseListeners()) {
            removeMouseListener(mouseListener);
        }

        for(MouseMotionListener mouseMotionListener : getMouseMotionListeners()) {
            removeMouseMotionListener(mouseMotionListener);
        }

        for(MouseWheelListener mouseWheelListener : getMouseWheelListeners()) {
            removeMouseWheelListener(mouseWheelListener);
        }

        for(KeyListener keyListener : getKeyListeners()) {
            removeKeyListener(keyListener);
        }
    }

    private void registerEventListeners(ScreenController screenController) 
    {
        if(screenController.getMouseListener() != null) {
            addMouseListener(screenController.getMouseListener());
        }

        if(screenController.getMouseMotionListener() != null) {
            addMouseMotionListener(screenController.getMouseMotionListener());
        }

        if(screenController.getMouseWheelListener() != null) {
            addMouseWheelListener(screenController.getMouseWheelListener());
        }

        if(screenController.getKeyListener() != null) {
            System.out.println("MainJFrame now adding key listener...");
            addKeyListener(screenController.getKeyListener());
        }
    }
    
    public void startGUI() 
    {
        gameLoopThread.start();
        
        this.setLocationRelativeTo(null);   //Centers the frame.
        
        this.setVisible(true);
        
        gameScreenJPanel.requestFocus();
    }
    
    private void setHiddenCursor()
    {   
        Cursor hiddenCursor = Toolkit.getDefaultToolkit()
                .createCustomCursor(new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
                    new Point(), null);
        
        SwingUtilities.invokeLater(()-> {
            gameScreenJPanel.setCursor(hiddenCursor);
        });
    }
    
    private void setDefaultCursor()
    {
        Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        gameScreenJPanel.setCursor(defaultCursor);
        
        SwingUtilities.invokeLater(()-> {
            gameScreenJPanel.setCursor(defaultCursor);
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gameScreenJPanel = new javax.swing.JPanel() {

            @Override
            public void paint(Graphics g)
            {
                super.paint(g);

                Image image = createImage(getWidth(), getHeight());
                Graphics screenGraphics = image.getGraphics();
                paintComponent(screenGraphics);
                g.drawImage(image, 0, 0, this);
            }

            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                paintGameScreen(g);
                //repaint();
            }

        };

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        gameScreenJPanel.setBackground(new java.awt.Color(0, 0, 0));
        gameScreenJPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout gameScreenJPanelLayout = new javax.swing.GroupLayout(gameScreenJPanel);
        gameScreenJPanel.setLayout(gameScreenJPanelLayout);
        gameScreenJPanelLayout.setHorizontalGroup(
            gameScreenJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        gameScreenJPanelLayout.setVerticalGroup(
            gameScreenJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gameScreenJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gameScreenJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public static final int DEFAULT_WIDTH = 700;
    public static final int DEFAULT_HEIGHT = 500;
    
    private volatile ScreenController screenController;

    private final GameLoopController gameLoopController;

    private final Thread gameLoopThread;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel gameScreenJPanel;
    // End of variables declaration//GEN-END:variables
}
