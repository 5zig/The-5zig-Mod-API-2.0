package eu.the5zig.mod.server.util;

import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.Stat;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R1.PacketDataSerializer;
import net.minecraft.server.v1_8_R1.PacketPlayOutCustomPayload;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;

/**
 * Created by 5zig.
 * All rights reserved © 2014
 */
public class ProtocolUtils {

	public static final String CHANNEL = "5zig";
	public static final int VERSION = 3;

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

		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(CHANNEL, dataSerializer);
		((CraftPlayer) modUser.getPlayer()).getHandle().playerConnection.sendPacket(packet);
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

		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(CHANNEL, dataSerializer);
		((CraftPlayer) modUser.getPlayer()).getHandle().playerConnection.sendPacket(packet);
	}

	/**
	 * Resets a stat of a mod user.
	 *
	 * @param modUser The Mod User whose stat should be resetted.
	 */
	public static void sendClearStats(ModUser modUser) {
		PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.buffer());
		dataSerializer.writeInt(PayloadType.CLEAR.ordinal());

		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(CHANNEL, dataSerializer);
		((CraftPlayer) modUser.getPlayer()).getHandle().playerConnection.sendPacket(packet);
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

		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(CHANNEL, dataSerializer);
		((CraftPlayer) modUser.getPlayer()).getHandle().playerConnection.sendPacket(packet);
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

		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(CHANNEL, dataSerializer);
		((CraftPlayer) modUser.getPlayer()).getHandle().playerConnection.sendPacket(packet);
	}

	public static void sendImage(ModUser modUser, String base64) {
		PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.buffer());
		dataSerializer.writeInt(PayloadType.IMAGE.ordinal());
		dataSerializer.a(base64);

		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(CHANNEL, dataSerializer);
		((CraftPlayer) modUser.getPlayer()).getHandle().playerConnection.sendPacket(packet);
	}

	public static void sendImage(ModUser modUser, int id) {
		PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.buffer());
		dataSerializer.writeInt(PayloadType.IMAGE.ordinal());
		dataSerializer.writeInt(id);

		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(CHANNEL, dataSerializer);
		((CraftPlayer) modUser.getPlayer()).getHandle().playerConnection.sendPacket(packet);
	}

	public enum PayloadType {

		UPDATE, RESET, CLEAR, DISPLAY_NAME, LOGIN, IMAGE

	}

	public enum LoginResponse {

		SUCCESS, OUTDATED_SERVER, OUTDATED_CLIENT

	}
}
