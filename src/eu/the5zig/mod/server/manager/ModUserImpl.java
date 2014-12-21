package eu.the5zig.mod.server.manager;

import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.StatsManager;
import eu.the5zig.mod.server.util.ProtocolUtils;
import org.bukkit.entity.Player;

/**
 * Created by 5zig.
 * All rights reserved © 2014
 */
public class ModUserImpl implements ModUser {

	private final boolean connected;
	private final Player player;
	private StatsManager statsManager;

	public ModUserImpl(Player player, int version) {
		this.player = player;
		statsManager = new StatsManagerImpl(this);
		ProtocolUtils.LoginResponse loginResponse = ProtocolUtils.LoginResponse.SUCCESS;
		if (ProtocolUtils.VERSION < version) loginResponse = ProtocolUtils.LoginResponse.OUTDATED_SERVER;
		if (ProtocolUtils.VERSION > version) loginResponse = ProtocolUtils.LoginResponse.OUTDATED_CLIENT;
		ProtocolUtils.sendLogin(this, loginResponse);
		connected = loginResponse == ProtocolUtils.LoginResponse.SUCCESS;
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
