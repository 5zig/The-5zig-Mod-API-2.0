package eu.the5zig.mod.server.util.protocol;

import eu.the5zig.mod.server.The5zigMod;
import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.Stat;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R2.PacketDataSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayOutCustomPayload;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public class ProtocolUtils_v1_8_R2 implements IProtocolUtils {

	@Override
	public void sendStat(ModUser modUser, Stat stat) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.UPDATE);
		dataSerializer.a(stat.getName());
		dataSerializer.a(stat.getScore());

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	@Override
	public void resetStat(ModUser modUser, Stat stat) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.RESET);
		dataSerializer.a(stat.getName());

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	@Override
	public void sendClearStats(ModUser modUser) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.CLEAR);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	@Override
	public void sendDisplayName(ModUser modUser, String displayName) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.DISPLAY_NAME);
		dataSerializer.a(displayName);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	@Override
	public void sendLargeText(ModUser modUser, String largeText) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.LARGE_TEXT);
		dataSerializer.a(largeText);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	@Override
	public void resetLargeText(ModUser modUser) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.RESET_LARGE_TEXT);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	@Override
	public void sendLogin(ModUser modUser, LoginResponse response) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.LOGIN);
		dataSerializer.writeInt(response.ordinal());

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	@Override
	public void sendImage(ModUser modUser, String base64, int id) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.IMAGE);
		dataSerializer.a(base64);
		dataSerializer.writeInt(id);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	@Override
	public void sendImage(ModUser modUser, int id) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.IMAGE_ID);
		dataSerializer.writeInt(id);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	@Override
	public void resetImage(ModUser modUser, int id) {
		PacketDataSerializer dataSerializer = newDataSerializer(PayloadType.RESET_IMAGE);
		dataSerializer.writeInt(id);

		sendCustomPayload(modUser.getPlayer(), dataSerializer);
	}

	private PacketDataSerializer newDataSerializer() {
		return new PacketDataSerializer(Unpooled.buffer());
	}

	private PacketDataSerializer newDataSerializer(PayloadType payloadType) {
		PacketDataSerializer packetDataSerializer = newDataSerializer();
		packetDataSerializer.writeInt(payloadType.ordinal());
		return packetDataSerializer;
	}

	private void sendCustomPayload(Player player, PacketDataSerializer dataSerializer) {
		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(The5zigMod.CHANNEL, dataSerializer);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

}
