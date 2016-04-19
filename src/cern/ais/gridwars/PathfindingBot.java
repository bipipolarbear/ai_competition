package cern.ais.gridwars;

import cern.ais.gridwars.bot.PlayerBot;
import cern.ais.gridwars.command.MovementCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

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
                    Long population = universeView.getPopulation(x, y);
                    if (population > 5) {
                        MovementCommand mc = new MovementCommand(
                                universeView.getCoordinates(x, y),
                                MovementCommand.Direction.RIGHT,
                                5L
                        );
                        movementCommands.add(mc);
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
