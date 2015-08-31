package eu.the5zig.mod.server.backend;

import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.StatsManager;
import org.bukkit.entity.Player;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public class ModUserImpl implements ModUser {

	private final boolean connected;
	private final Player player;
	private final byte protocolVersion;
	private StatsManager statsManager;

	ModUserImpl(Player player, byte protocolVersion) {
		this.player = player;
		this.protocolVersion = protocolVersion;
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

	@Override
	public byte getProtocolVersion() {
		return protocolVersion;
	}
}
