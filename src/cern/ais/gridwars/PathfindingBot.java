package cern.ais.gridwars;

import cern.ais.gridwars.bot.PlayerBot;
import cern.ais.gridwars.command.MovementCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

<<<<<<< HEAD
import static cern.ais.gridwars.Util.getApproachingDirection;
import static cern.ais.gridwars.Util.randomDirection;
=======
>>>>>>> 1d2fc79... Implement calculation of square distance between two cells.
import static cern.ais.gridwars.Util.sortedByPopulation;

/**
 * A simple driver for collaborative diffusion algorithm
 */
public class PathfindingBot implements PlayerBot {
    // holds the positions and populations
    // of respective enemy units
    private HashMap<Coordinates, Long> enemyUnits;

    public PathfindingBot() {
        enemyUnits = new HashMap<>();
    }

    @Override public void getNextCommands(UniverseView universeView, List<MovementCommand> movementCommands) {
        findEnemyUnits(universeView);
        SortedSet<Map.Entry<Coordinates, Long>> enemiesSorted = sortedByPopulation(enemyUnits);
        //System.out.println(enemiesSorted);

        int size = GameConstants.UNIVERSE_SIZE;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (universeView.belongsToMe(x, y)) {
<<<<<<< HEAD

                    Long population = universeView.getPopulation(x, y);
                    Coordinates coord = universeView.getCoordinates(x, y);

                    // try to find some trouble if we are strong enough
                    Map.Entry<Coordinates,Long> weakest = enemiesSorted.first();
                    Map.Entry<Coordinates,Long> strongest = enemiesSorted.last();

                    if (population >= 50) {//weakest.getValue()) {
                        MovementCommand trail = new MovementCommand(coord, getApproachingDirection(coord, weakest.getKey()), 9L);
                        movementCommands.add(trail);
                        enemiesSorted.remove(weakest);

                        MovementCommand missile = new MovementCommand(
                            coord,
                            getApproachingDirection(coord, strongest.getKey()),
                            30L
                        );
                        movementCommands.add(missile);
                        enemiesSorted.remove(strongest);
                    }
                    else {
                        //MovementCommand trail = new MovementCommand(coord, randomDirection(), 5L);
                        //movementCommands.add(trail);
                        if(population % 5 == 0) {
                            for(int i = 0; i < population / 5 - 2; i++) {
                                MovementCommand run = new MovementCommand(
                                        coord,
                                        randomDirection(),
                                        5L
                                );
                                movementCommands.add(run);
                            }
                        }
                        else {
                            MovementCommand run = new MovementCommand(
                                    coord,
                                    randomDirection(),
                                    population
                            );
                            movementCommands.add(run);
                        }
=======
                    Long population = universeView.getPopulation(x, y);
                    if (population > 5) {
                        MovementCommand mc = new MovementCommand(
                                universeView.getCoordinates(x, y),
                                MovementCommand.Direction.RIGHT,
                                5L
                        );
                        movementCommands.add(mc);
>>>>>>> 1d2fc79... Implement calculation of square distance between two cells.
                    }
                }
            }
        }
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
