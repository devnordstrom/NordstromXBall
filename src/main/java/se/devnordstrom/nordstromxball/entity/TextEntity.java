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
package se.devnordstrom.nordstromxball.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Orville Nordström
 */
public class TextEntity implements PaintableEntity
{
    private String text;
    
    private Color color;
    
    private Font font;
    
    private int x, y;
    
    public TextEntity()
    {
        this(0, 0, null);
    }
    
    public TextEntity(int x, int y, String text)
    {
        this.x = x;
        this.y = y;
        this.text = text;
    }
    
    
    @Override
    public void paint(Graphics g) 
    {
        if(getText() == null || getText().trim().isEmpty()) {
            return;
        }
        
        Font originalFont = g.getFont();
        if(getFont() != null) g.setFont(getFont());
        
        if(getColor() != null) g.setColor(getColor());
        
        g.drawString(getText(), getX(), getY());
        
        
        
        g.setFont(originalFont);
    }

    @Override
    public void move(double delta) 
    {
        //A text entity isn't normally supposed to move.
    }
    
    /**
     * @return the text
     */
    public String getText() 
    {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) 
    {
        this.text = text;
    }

    /**
     * @return the color
     */
    public Color getColor() 
    {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) 
    {
        this.color = color;
    }

    /**
     * @return the font
     */
    public Font getFont() 
    {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(Font font) 
    {
        this.font = font;
    }

    /**
     * @return the x
     */
    public int getX() 
    {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) 
    {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() 
    {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) 
    {
        this.y = y;
    }
}