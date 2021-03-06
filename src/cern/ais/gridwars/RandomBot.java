package cern.ais.gridwars;

import cern.ais.gridwars.bot.PlayerBot;
import cern.ais.gridwars.command.MovementCommand;

import java.util.List;

public class RandomBot implements PlayerBot {
	public RandomBot() {}


	@Override public void getNextCommands(UniverseView universeView, List<MovementCommand> movementCommands) {
		for (int my = 0; my < GameConstants.UNIVERSE_SIZE; my++)
			for (int mx = 0; mx < GameConstants.UNIVERSE_SIZE; mx++) {
				if (universeView.belongsToMe(mx, my)) {
					Long population = universeView.getPopulation(mx, my);
					if (population > 1) {
						movementCommands.add(new MovementCommand(universeView.getCoordinates(mx, my), Util.randomDirection(), population / 2));
					}
				}
			}
	}
}
