package eu.the5zig.mod.server;

import com.google.common.base.Charsets;
import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.events.The5zigModUserJoinEvent;
import eu.the5zig.mod.server.api.events.The5zigModUserLoginEvent;
import eu.the5zig.mod.server.manager.ModUserImpl;
import eu.the5zig.mod.server.manager.ProtocolUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
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
		if (channel.equals(ProtocolUtils.CHANNEL)) {
			handlePluginMessage(player, new String(bytes, Charsets.UTF_8));
		}
	}

	/**
	 * Handles the custom plugin message sent by a 5zig Mod user
	 *
	 * @param player  The user of the 5zig Mod
	 * @param message The plugin message converted to a String
	 */
	private void handlePluginMessage(Player player, String message) {
		if (message.startsWith("l:")) {
			if (plugin.getUserManager().isModUser(player)) return;
			The5zigModUserLoginEvent event = new The5zigModUserLoginEvent(player);
			Bukkit.getServer().getPluginManager().callEvent(event);
			if (!event.isCancelled()) {
				try {
					ModUser modUser = new ModUserImpl(player, Integer.parseInt(message.replace("l:", "")));
					if (!modUser.isConnected()) return;
					plugin.getUserManager().addUser(modUser);
					plugin.getLogger().info("Player " + player.getName() + " connected using the 5zig Mod!");
					Bukkit.getServer().getPluginManager().callEvent(new The5zigModUserJoinEvent(modUser));
				} catch (NumberFormatException e) {
					plugin.getLogger().warning("Player " + player.getName() + " failed to connect with the 5zig Mod!");
				}
			}
		}
	}
}
