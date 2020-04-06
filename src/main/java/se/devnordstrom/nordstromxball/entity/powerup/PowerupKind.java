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
package se.devnordstrom.nordstromxball.entity.powerup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Orville N
 */
public enum PowerupKind 
{   
    REVEAL_INVISIBLE, SPLIT_BALLS, INCREASED_SPEED, 
    DECREASED_SPEED, STICKY_PAD, EXTRA_LIFE, 
    BIGGER_PAD, SMALLER_PAD, EXTRA_BALL, 
    EXTRA_STICKY_BALL, KILL_PLAYER, RANDOM, EXPLOSION;
    
    /**
     * 
     * @return 
     */
    public static List<PowerupKind> getDefaultKinds()
    {
        List<PowerupKind> powerupKinds = new ArrayList<>();
        
        powerupKinds.addAll(getPossitiveDefaultKinds());
        
        powerupKinds.addAll(getNegativeDefaultKinds());
        
        return powerupKinds;
    }
    
    public static List<PowerupKind> getNegativeDefaultKinds()
    {
        List<PowerupKind> powerupKinds = new ArrayList<>();
        
        powerupKinds.add(INCREASED_SPEED);
        powerupKinds.add(SMALLER_PAD);
        
        return powerupKinds;
    }
    
    public static List<PowerupKind> getPossitiveDefaultKinds()
    {
        List<PowerupKind> possitiveKinds = new ArrayList<>();
        
        possitiveKinds.add(REVEAL_INVISIBLE);
        possitiveKinds.add(SPLIT_BALLS);
        possitiveKinds.add(DECREASED_SPEED);
        possitiveKinds.add(STICKY_PAD);
        possitiveKinds.add(EXTRA_LIFE);
        possitiveKinds.add(BIGGER_PAD);
        
        return possitiveKinds;
    }
}