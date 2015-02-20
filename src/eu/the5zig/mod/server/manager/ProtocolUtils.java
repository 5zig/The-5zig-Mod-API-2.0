package eu.the5zig.mod.server.manager;

import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.Stat;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R1.PacketDataSerializer;
import net.minecraft.server.v1_8_R1.PacketPlayOutCustomPayload;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public class ProtocolUtils {

	public static final String CHANNEL = "5zig";
	public static final int VERSION = 3;
	public static final String COLOR_CODE = "§z §i §g";

	/**
	 * Sends a stat to a mod user.
	 *
	 * @param modUser The Mod User that should receive the stat.
	 * @param stat    The Stat itself.
	 */
	static void sendStat(ModUser modUser, Stat stat) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.UPDATE);
		dataSerializer.a(stat.getName());
		dataSerializer.a(stat.getScore());

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Resets a stat of a mod user.
	 *
	 * @param modUser The Mod User whose stat should be resetted.
	 * @param stat    The Stat itself.
	 */
	static void resetStat(ModUser modUser, Stat stat) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.RESET);
		dataSerializer.a(stat.getName());

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Resets a stat of a mod user.
	 *
	 * @param modUser The Mod User whose stat should be resetted.
	 */
	static void sendClearStats(ModUser modUser) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.CLEAR);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Sends a new Display Name to a mod user.
	 *
	 * @param modUser     The Mod User that should receive the Display Name.
	 * @param displayName The new Display Name.
	 */
	static void sendDisplayName(ModUser modUser, String displayName) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.DISPLAY_NAME);
		dataSerializer.a(displayName);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Sends a large Text to a mod user.
	 *
	 * @param modUser   The Mod User that should receive the large Text.
	 * @param largeText The Text that should be send.
	 */
	static void sendLargeText(ModUser modUser, String largeText) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.LARGE_TEXT);
		dataSerializer.a(largeText);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Resets the Large Text of a mod user.
	 *
	 * @param modUser The Mod User whose Text should be removed.
	 */
	static void resetLargeText(ModUser modUser) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.RESET_LARGE_TEXT);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Sends a login response to the User
	 *
	 * @param modUser The Mod User that should receive the response
	 */
	static void sendLogin(ModUser modUser, LoginResponse response) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.LOGIN);
		dataSerializer.writeInt(response.ordinal());

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Sends an image with its server side generated id to a mod user.
	 *
	 * @param modUser The Mod User that should see the image.
	 * @param base64  The base64 String of the Image.
	 * @param id      The Server-side generated id.
	 */
	static void sendImage(ModUser modUser, String base64, int id) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.IMAGE);
		dataSerializer.a(base64);
		dataSerializer.writeInt(id);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Sends the id of the cached image to a mod user.
	 *
	 * @param modUser The Mod User that should see the image.
	 * @param id      The Id of the Image.
	 */
	static void sendImage(ModUser modUser, int id) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.IMAGE_ID);
		dataSerializer.writeInt(id);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Resets a image of the Mod User by sending the id of the image.
	 *
	 * @param modUser The Mod User where the image should be removed.
	 * @param id      The Id of the Image.
	 */
	static void resetImage(ModUser modUser, int id) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.RESET_IMAGE);
		dataSerializer.writeInt(id);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Creates a new PacketDataSerializer with an empty Buffer.
	 *
	 * @return a new PacketDataSerializer.
	 */
	private static PacketDataSerializer newDataSerializer() {
		return new PacketDataSerializer(Unpooled.buffer());
	}

	private static PacketDataSerializer newDataSerializer(PayloadType payloadType) {
		PacketDataSerializer packetDataSerializer = newDataSerializer();
		packetDataSerializer.writeInt(payloadType.ordinal());
		return packetDataSerializer;
	}

	/**
	 * Sends a Custom Plugin Message to the player
	 *
	 * @param player         The player that should receive the Plugin message.
	 * @param dataSerializer The PacketDataSerializer of the Plugin message.
	 */
	private static void sendCustomPayload(Player player, PacketDataSerializer dataSerializer) {
		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(CHANNEL, dataSerializer);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	enum PayloadType {

		UPDATE, RESET, CLEAR, DISPLAY_NAME, LOGIN, IMAGE, IMAGE_ID, RESET_IMAGE, LARGE_TEXT, RESET_LARGE_TEXT

	}

	enum LoginResponse {

		SUCCESS, OUTDATED_SERVER, OUTDATED_CLIENT

	}
}
