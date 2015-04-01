package eu.the5zig.mod.server.backend;

import eu.the5zig.mod.server.The5zigMod;
import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.StatsManager;
import eu.the5zig.mod.server.util.protocol.IProtocolUtils;
import org.bukkit.entity.Player;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public class ModUserImpl implements ModUser {

	private final boolean connected;
	private final Player player;
	private StatsManager statsManager;

	ModUserImpl(Player player, int version) {
		this.player = player;
		statsManager = new StatsManagerImpl(this);
		IProtocolUtils.LoginResponse loginResponse = IProtocolUtils.LoginResponse.SUCCESS;
		if (The5zigMod.VERSION < version)
			loginResponse = IProtocolUtils.LoginResponse.OUTDATED_SERVER;
		if (The5zigMod.VERSION > version)
			loginResponse = IProtocolUtils.LoginResponse.OUTDATED_CLIENT;
		The5zigMod.getInstance().getProtocolUtils().sendLogin(this, loginResponse);
		connected = loginResponse == IProtocolUtils.LoginResponse.SUCCESS;
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
