package cern.ais.gridwars;

import cern.ais.gridwars.command.MovementCommand;

import java.net.CookieHandler;
import java.util.*;

import static cern.ais.gridwars.command.MovementCommand.Direction.*;

/**
 * Created by Sapog on 4/19/2016.
 */
public final class Util {
    private static Random rng = new Random();

    private Util() {}

    public static MovementCommand.Direction randomDirection() {
        int roll = rng.nextInt(4);
        MovementCommand.Direction result = null;
        switch (roll) {
            case 0:
                result = LEFT;
                break;
            case 1:
                result = RIGHT;
                break;
            case 2:
                result = UP;
                break;
            case 3:
                result = DOWN;
                break;
        }
        return result;
    }

    public static MovementCommand.Direction getApproachingDirection(Coordinates from, Coordinates to) {
        int xDist = from.getX() - to.getX();
        int yDist = from.getY() - to.getY();

        if(yDist == 0) return xDist < 0 ? UP : DOWN;
        if(xDist == 0) return yDist < 0 ? LEFT : RIGHT;

        if(Math.abs(xDist) < Math.abs(yDist)) {
            return xDist <= 0 ? UP : DOWN;
        }
        else {
            return yDist < 0 ? LEFT : RIGHT;
        }
    }

    public static MovementCommand.Direction opposite(MovementCommand.Direction dir) {
        MovementCommand.Direction ret = null;
        switch(dir) {
            case LEFT:
                ret = RIGHT;
                break;
            case RIGHT:
                ret = LEFT;
                break;
            case UP:
                ret = DOWN;
                break;
            case DOWN:
                ret = UP;
                break;
        }
        return ret;
    }

    public static MovementCommand.Direction rotate(MovementCommand.Direction dir) {
        MovementCommand.Direction ret = null;
        switch(dir) {
            case LEFT:
                ret = DOWN;
                break;
            case RIGHT:
                ret = UP;
                break;
            case UP:
                ret = RIGHT;
                break;
            case DOWN:
                ret = LEFT;
                break;
        }
        return ret;
    }

    public static int squareDistance(Coordinates from, Coordinates to) {
        int xDist = from.getX() - to.getX();
        int yDist = from.getY() - to.getY();
        return xDist * xDist + yDist * yDist;
    }

    static SortedSet<Map.Entry<Coordinates,Long>> sortedByPopulation(Map<Coordinates,Long> map) {
        SortedSet<Map.Entry<Coordinates,Long>> sortedEntries = new TreeSet<>(
                new Comparator<Map.Entry<Coordinates,Long>>() {
                    @Override public int compare(Map.Entry<Coordinates,Long> e1, Map.Entry<Coordinates,Long> e2) {
                        int res = e1.getValue().compareTo(e2.getValue());
                        return res > 0 ? 1 : -1;
                    }
                });
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}
