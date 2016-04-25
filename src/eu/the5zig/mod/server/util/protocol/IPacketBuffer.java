package eu.the5zig.mod.server.util.protocol;

import org.bukkit.entity.Player;

/**
 * A class representing a buffer. Basically a wrapper for the minecraft version dependent data lserializer.
 */
public interface IPacketBuffer {

	/**
	 * Writes a string to the buffer.
	 *
	 * @param string the string that should be written.
	 */
	void writeString(String string);

	/**
	 * Writes an integer to the buffer.
	 *
	 * @param i the integer that should be written.
	 */
	void writeInt(int i);

	/**
	 * Writes a long to the buffer.
	 *
	 * @param l the log that should be written.
	 */
	void writeLong(long l);

	/**
	 * Sends the buffer as custom payload to the specified player.
	 *
	 * @param player  the player that should receive the packet.
	 * @param channel the channel name.
	 */
	void send(Player player, String channel);

}