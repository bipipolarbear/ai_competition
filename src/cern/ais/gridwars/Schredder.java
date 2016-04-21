package cern.ais.gridwars;

import cern.ais.gridwars.bot.PlayerBot;
import cern.ais.gridwars.command.MovementCommand;
import cern.ais.gridwars.universe.Universe;

import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.util.*;

import static cern.ais.gridwars.Util.*;

public class Schredder implements PlayerBot {
    // holds the positions and populations
    // of respective enemy units
    private HashMap<Coordinates, Long> enemyUnits;
    private long prevSum = 100L;
    private long diff = 0L;
    private boolean panic = false;
    private MovementCommand.Direction dir = randomDirection();

    public Schredder() {
        enemyUnits = new HashMap<>();
    }

    @Override public void getNextCommands(UniverseView universeView, List<MovementCommand> movementCommands) {
        findEnemyUnits(universeView);
        SortedSet<Map.Entry<Coordinates, Long>> enemiesSorted = sortedByPopulation(enemyUnits);
        //System.out.println(enemiesSorted);
        Map.Entry<Coordinates, Long> weakest = enemiesSorted.first();
        Map.Entry<Coordinates, Long> strongest = enemiesSorted.last();

        long curSum = 0L;

        List<Coordinates> cells = universeView.getMyCells();

        for(Coordinates c : cells) {
            if (universeView.belongsToMe(c)) {

                long population = universeView.getPopulation(c);
                curSum += population;
                //GROWING
                // if the last digit is >= than 5
                ArrayList<MovementCommand.Direction> best =
                        pickBestDirection(universeView, c, 1, 1);
                Long remaining = population - 5L;
                Long batch = remaining / best.size();
                Iterator<MovementCommand.Direction> it = best.iterator();
                if (!panic && remaining > best.size() * 5L) {//% 10 >= 5) {
                    while (it.hasNext()) {
                        MovementCommand trail = new MovementCommand(
                                c,
                                it.next(),//getApproachingDirection(coord, weakest.getKey()),
                                batch
                        );
                        movementCommands.add(trail);
                        remaining -= batch;
                    }
                }

                if(panic && population >= 2) {

                    MovementCommand mc0 = new MovementCommand(
                            c,
                            dir,
                            population / 2
                    );
                    MovementCommand mc1 = new MovementCommand(
                            c,
                            opposite(dir),
                            population / 2
                    );

                    movementCommands.add(mc0);
                    movementCommands.add(mc1);


                }
            }
        }

        //PANIC MODE
        System.out.println(panic);
        if(curSum - prevSum < diff && universeView.getCurrentTurn() % 10 <= 2) {
            dir = rotate(opposite(dir));
            panic = true;
        }
        else {
            panic = false;
        }
        diff = prevSum - curSum;
        prevSum = curSum;
    }

    private static ArrayList<MovementCommand.Direction> pickBestDirection(UniverseView uv, Coordinates coord, int extension, long min) {
        ArrayList<MovementCommand.Direction> best = new ArrayList<>();

        for(MovementCommand.Direction dir : MovementCommand.Direction.values()) {
            Coordinates next = coord.getRelative(extension, dir);
            long p = uv.getPopulation(next);
            if( (uv.belongsToMe(next) && p < min) || !uv.belongsToMe(next)) best.add(dir);
        }

        if(best.isEmpty()) return pickBestDirection(uv, coord, extension + 1, min + 1L);
        else return best;
    }


    // TODO: optimize - determine starting position
    //       and update only modified units afterwards
    private void findEnemyUnits(UniverseView uv) {
        int size = GameConstants.UNIVERSE_SIZE;
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                Long population = uv.getPopulation(x, y);
                if(population > 0 && !uv.belongsToMe(x, y)) {
                    Coordinates coord = uv.getCoordinates(x, y);
                    enemyUnits.put(coord, population);
                }
            }
        }
    }
}
