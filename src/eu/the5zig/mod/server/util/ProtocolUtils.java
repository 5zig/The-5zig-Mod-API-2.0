package eu.the5zig.mod.server.util;

import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.Stat;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R1.PacketDataSerializer;
import net.minecraft.server.v1_8_R1.PacketPlayOutCustomPayload;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by 5zig.
 * All rights reserved © 2014
 */
public class ProtocolUtils {

	public static final String CHANNEL = "5zig";
	public static final int VERSION = 3;
	public static final String COLOR_CODE = "§5§z§i§g§m§o§d";

	/**
	 * Sends a stat to a mod user.
	 *
	 * @param modUser The Mod User that should receive the stat.
	 * @param stat    The Stat itself.
	 */
	public static void sendStat(ModUser modUser, Stat stat) {
		PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.buffer());
		dataSerializer.writeInt(PayloadType.UPDATE.ordinal());
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
	public static void resetStat(ModUser modUser, Stat stat) {
		PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.buffer());
		dataSerializer.writeInt(PayloadType.RESET.ordinal());
		dataSerializer.a(stat.getName());

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Resets a stat of a mod user.
	 *
	 * @param modUser The Mod User whose stat should be resetted.
	 */
	public static void sendClearStats(ModUser modUser) {
		PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.buffer());
		dataSerializer.writeInt(PayloadType.CLEAR.ordinal());

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Sends a new Display Name to a mod user.
	 *
	 * @param modUser     The Mod User that should receive the Display Name.
	 * @param displayName The new Display Name.
	 */
	public static void sendDisplayName(ModUser modUser, String displayName) {
		PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.buffer());
		dataSerializer.writeInt(PayloadType.DISPLAY_NAME.ordinal());
		dataSerializer.a(displayName);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Sends a login response to the User
	 *
	 * @param modUser The Mod User that should receive the response
	 */
	public static void sendLogin(ModUser modUser, LoginResponse response) {
		PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.buffer());
		dataSerializer.writeInt(PayloadType.LOGIN.ordinal());
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
	public static void sendImage(ModUser modUser, String base64, int id) {
		PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.buffer());
		dataSerializer.writeInt(PayloadType.IMAGE.ordinal());
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
	public static void sendImage(ModUser modUser, int id) {
		PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.buffer());
		dataSerializer.writeInt(PayloadType.IMAGE_ID.ordinal());
		dataSerializer.writeInt(id);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Resets a image of the Mod User by sending the id of the image.
	 *
	 * @param modUser The Mod User where the image should be removed.
	 * @param id      The Id of the Image.
	 */
	public static void resetImage(ModUser modUser, int id) {
		PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.buffer());
		dataSerializer.writeInt(PayloadType.RESET_IMAGE.ordinal());
		dataSerializer.writeInt(id);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	/**
	 * Sends a Custom Plugin Message to the player
	 * @param player The player that should receive the Plugin message.
	 * @param dataSerializer The PacketDataSerializer of the Plugin message.
	 */
	private static void sendCustomPayload(Player player, PacketDataSerializer dataSerializer) {
		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(CHANNEL, dataSerializer);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	public enum PayloadType {

		UPDATE, RESET, CLEAR, DISPLAY_NAME, LOGIN, IMAGE, IMAGE_ID, RESET_IMAGE

	}

	public enum LoginResponse {

		SUCCESS, OUTDATED_SERVER, OUTDATED_CLIENT

	}
}
