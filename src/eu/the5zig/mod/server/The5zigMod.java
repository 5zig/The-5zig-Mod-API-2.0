package eu.the5zig.mod.server;

import eu.the5zig.mod.server.api.UserManager;
import eu.the5zig.mod.server.manager.UserManagerImpl;
import eu.the5zig.mod.server.util.ProtocolUtils;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by 5zig.
 * All rights reserved © 2014
 */
public class The5zigMod extends JavaPlugin {

	private static The5zigMod instance;

	private UserManager userManager;

	/**
	 * Returns the current instance of the Server API.
	 *
	 * @return The Server API instance.
	 */
	public static The5zigMod getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;

		userManager = new UserManagerImpl(this);

		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents((Listener) userManager, this);

		getServer().getMessenger().registerOutgoingPluginChannel(this, ProtocolUtils.CHANNEL);
		getServer().getMessenger().registerIncomingPluginChannel(this, ProtocolUtils.CHANNEL, new ClientMessageListener(this));
		getLogger().info("Listening on channel " + ProtocolUtils.CHANNEL);


		getLogger().info("The 5zig Mod Server API v" + getDescription().getVersion() + " has been enabled!");
	}

	/**
	 * Gets the UserManager. The Manager class stores all connected 5zig Mod users in a list
	 *
	 * @return The User Manager class
	 */
	public UserManager getUserManager() {
		return userManager;
	}

}
