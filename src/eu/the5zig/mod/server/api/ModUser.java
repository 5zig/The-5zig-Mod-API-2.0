package eu.the5zig.mod.server.api;

import org.bukkit.entity.Player;

public interface ModUser {

	/**
	 * Returns the Bukkit player of the Mod user.
	 *
	 * @return The Player
	 */
	Player getPlayer();

	/**
	 * Gets the Stats Manager of the Player. The Stats Manager stores all Stats of a player and sends them to it
	 *
	 * @return The Stats Manager.
	 */
	StatsManager getStatsManager();

	/**
	 * Sends an overlay message to the player. The message may not be longer than 100 characters and will be
	 * splitted onto the second line automatically. Use the \n character to force split the message onto a new line.
	 *
	 * @param message The message that should be displayed.
	 */
	void sendOverlay(String message);

	/**
	 * Sends a mod plugin download request to the client. This mod plugin will be installed on the client-side and can be used to display additional stats that are not possible using the
	 * server API. The mod plugin will be only downloaded from the client if the client has not downloaded the plugin before or if the hash of the previously downloaded plugin does not
	 * match the given hash. See <a href="https://github.com/5zig/The-5zig-API">https://github.com/5zig/The-5zig-API</a> for more information.
	 *
	 * @param pluginName    The name of the plugin, eg. "SuperDuperPlugin". Max length is 128 bytes/characters.
	 * @param sha1Hash      The sha1 hash of the plugin. Must be 40 characters long.
	 * @param downloadPath  The download path of the plugin that should be used by the client. HTTPS is recommended but not necessary. Max length is 256 bytes/characters.
	 * @param customMessage A custom message that will be shown to the client or {@code null} if no custom message should be displayed. Max length is 256 bytes/characters.
	 */
	void sendModPluginRequest(String pluginName, String sha1Hash, String downloadPath, String customMessage);

	/**
	 * Checks if the ModUser is currently connected to the ServerAPI.
	 *
	 * @return If the ModUser is currently connected to the ServerAPI.
	 */
	boolean isConnected();

	/**
	 * @return the API Version of the Player.
	 */
	byte getProtocolVersion();

}
