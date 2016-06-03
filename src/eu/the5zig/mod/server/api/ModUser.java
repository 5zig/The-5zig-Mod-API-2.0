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
