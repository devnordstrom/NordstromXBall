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
package se.devnordstrom.nordstromxball.entity.brick;

/**
 *
 * @author Orville N
 */
public class InvisibleBrick extends ToughBrick
{    
    protected int startingHp = 2;
    
    @Override
    public boolean isMustBeDestroyed()
    {
        return hp < startingHp;
    }
    
    @Override
    public boolean isVisible()
    {
        return hp < startingHp;
    }
}