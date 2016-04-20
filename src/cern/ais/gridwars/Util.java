package cern.ais.gridwars;

import cern.ais.gridwars.command.MovementCommand;

import java.net.CookieHandler;
import java.util.*;

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
                result = MovementCommand.Direction.LEFT;
                break;
            case 1:
                result = MovementCommand.Direction.RIGHT;
                break;
            case 2:
                result = MovementCommand.Direction.UP;
                break;
            case 3:
                result = MovementCommand.Direction.DOWN;
                break;
        }
        return result;
    }

    public static MovementCommand.Direction getApproachingDirection(Coordinates from, Coordinates to) {
        int xDist = from.getX() - to.getX();
        int yDist = from.getY() - to.getY();

        if(yDist == 0) return xDist < 0 ? MovementCommand.Direction.UP : MovementCommand.Direction.DOWN;
        if(xDist == 0) return yDist < 0 ? MovementCommand.Direction.LEFT : MovementCommand.Direction.RIGHT;

        if(Math.abs(xDist) < Math.abs(yDist)) {
            return xDist < 0 ? MovementCommand.Direction.UP : MovementCommand.Direction.DOWN;
        }
        else {
            return yDist < 0 ? MovementCommand.Direction.LEFT : MovementCommand.Direction.RIGHT;
        }
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
