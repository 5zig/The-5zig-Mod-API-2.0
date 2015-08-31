package eu.the5zig.mod.server.backend;

import eu.the5zig.mod.server.The5zigMod;
import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.events.The5zigModUserJoinEvent;
import eu.the5zig.mod.server.api.events.The5zigModUserLoginEvent;
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
		if (channel.equals(The5zigMod.CHANNEL)) {
			handlePluginMessage(player, bytes);
		}
	}

	/**
	 * Handles the custom plugin message sent by a 5zig Mod user
	 *
	 * @param player  The user of the 5zig Mod
	 * @param message The plugin message converted to a String
	 */
	private void handlePluginMessage(Player player, byte[] message) {
		if (message.length == 1) {
			if (plugin.getUserManager().isModUser(player))
				return;
			The5zigModUserLoginEvent event = new The5zigModUserLoginEvent(player);
			Bukkit.getServer().getPluginManager().callEvent(event);
			if (!event.isCancelled()) {
				ModUser modUser = new ModUserImpl(player, message[0]);
				((UserManagerImpl) plugin.getUserManager()).addUser(modUser);
				plugin.getLogger().info("Player " + player.getName() + " connected using the 5zig Mod!");
				Bukkit.getServer().getPluginManager().callEvent(new The5zigModUserJoinEvent(modUser));
			}
		}
	}
}
