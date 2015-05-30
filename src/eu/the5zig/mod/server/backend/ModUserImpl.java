package eu.the5zig.mod.server.backend;

import org.bukkit.entity.Player;

import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.StatsManager;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public class ModUserImpl implements ModUser {

	private final boolean connected;
	private final Player player;
	private StatsManager statsManager;

	ModUserImpl(Player player) {
		this.player = player;
		statsManager = new StatsManagerImpl(this);
		connected = true;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public StatsManager getStatsManager() {
		return statsManager;
	}

	@Override
	public boolean isConnected() {
		return connected && player.isOnline();
	}
}
