package cern.ais.gridwars;

import java.net.CookieHandler;
import java.util.*;

/**
 * Created by Sapog on 4/19/2016.
 */
public class Util {
    static int squareDistance(Coordinates from, Coordinates to) {
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
