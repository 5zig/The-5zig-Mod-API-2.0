package eu.the5zig.mod.server.backend;

import eu.the5zig.mod.server.The5zigMod;
import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.StatsManager;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

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
	public void sendOverlay(String message) {
		Validate.notNull(message, "Message cannot be null.");
		Validate.notEmpty(message, "Message cannot be empty.");
		Validate.validState(message.length() <= 100, "Message cannot be longer than 100 characters.");

		The5zigMod.getInstance().getProtocolUtils().sendOverlay(this, message);
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
