package com.runninglight.shared;

import java.util.Comparator;

public class PlayerCompare implements Comparator<Player>
{
    @Override
    public int compare(Player p1, Player p2)
    {
        return (int) (p1.getPoints() - p2.getPoints());
    }
}
