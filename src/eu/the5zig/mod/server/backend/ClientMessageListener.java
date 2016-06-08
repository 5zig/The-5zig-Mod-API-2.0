package eu.the5zig.mod.server.backend;

import eu.the5zig.mod.server.The5zigMod;
import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.events.The5zigModPluginDLResponseEvent;
import eu.the5zig.mod.server.api.events.The5zigModUserJoinEvent;
import eu.the5zig.mod.server.api.events.The5zigModUserLoginEvent;
import eu.the5zig.mod.server.util.protocol.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ClientMessageListener implements PluginMessageListener {

	private The5zigMod plugin;

	public ClientMessageListener(The5zigMod plugin) {
		this.plugin = plugin;
	}

	/**
	 * Fired when the Server receives a custom plugin message.
	 *
	 * @param channel The channel name of the plugin message. In my case '5zig'
	 * @param player  The player who sent the plugin message.
	 * @param bytes   The byte data of the plugin message.
	 */
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
		// Check if the channel is equal to '5zig'
		if (channel.equals(The5zigMod.CHANNEL)) {
			ByteBuf byteBuf = Unpooled.buffer().writeBytes(bytes).resetReaderIndex();
			handlePluginMessage(player, byteBuf);
		}
	}

	/**
	 * Handles the custom plugin message sent by a 5zig Mod user
	 *
	 * @param player  The user of the 5zig Mod
	 * @param message The plugin message converted to a String
	 */
	private void handlePluginMessage(Player player, ByteBuf message) {
		if (!plugin.getUserManager().isModUser(player)) {
			if (message.readableBytes() == 1) {
				The5zigModUserLoginEvent event = new The5zigModUserLoginEvent(player);
				Bukkit.getServer().getPluginManager().callEvent(event);
				if (!event.isCancelled()) {
					ModUser modUser = new ModUserImpl(player, message.readByte());
					((UserManagerImpl) plugin.getUserManager()).addUser(modUser);
					plugin.getLogger().info("Player " + player.getName() + " connected using the 5zig Mod!");
					Bukkit.getServer().getPluginManager().callEvent(new The5zigModUserJoinEvent(modUser));
				}
			}
		} else {
			int packetOrdinal = message.readInt();
			Protocol.PayloadType payloadType = Protocol.PayloadType.values()[packetOrdinal];
			switch (payloadType) {
				case MOD_PLUGIN:
					int responseOrdinal = message.readInt();
					Protocol.ModPluginResponse response = Protocol.ModPluginResponse.values()[responseOrdinal];
					Bukkit.getPluginManager().callEvent(new The5zigModPluginDLResponseEvent(plugin.getUserManager().getUser(player), response));
					break;
			}
		}
	}
}
