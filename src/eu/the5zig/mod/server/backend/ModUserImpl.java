package eu.the5zig.mod.server.backend;

import eu.the5zig.mod.server.The5zigMod;
import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.StatsManager;
import org.apache.commons.codec.Charsets;
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
	public void sendModPluginRequest(String pluginName, String sha1Hash, String downloadPath) {
		Validate.notNull(pluginName, "Plugin name cannot be null.");
		Validate.notEmpty(pluginName, "Plugin name cannot be empty.");
		Validate.isTrue(pluginName.getBytes(Charsets.UTF_8).length <= 128, "Plugin name cannot be longer than 128 bytes.");
		Validate.notNull(sha1Hash, "Sha-1 Hash cannot be null.");
		Validate.notEmpty(sha1Hash, "Sha-1 Hash cannot be empty");
		Validate.isTrue(sha1Hash.getBytes(Charsets.UTF_8).length == 40, "Sha-1 Hash must be 40 characters long.");
		Validate.notNull(downloadPath, "Download path cannot be null.");
		Validate.notEmpty(downloadPath, "Download path cannot be empty.");
		Validate.isTrue(downloadPath.getBytes(Charsets.UTF_8).length <= 256, "Download path cannot be longer than 256 bytes.");

		The5zigMod.getInstance().getProtocolUtils().sendModPluginRequest(this, pluginName, sha1Hash, downloadPath);
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
