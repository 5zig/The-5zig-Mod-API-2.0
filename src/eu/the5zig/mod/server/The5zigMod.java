package eu.the5zig.mod.server;

import eu.the5zig.mod.server.api.UserManager;
import eu.the5zig.mod.server.backend.ClientMessageListener;
import eu.the5zig.mod.server.backend.UserManagerImpl;
import eu.the5zig.mod.server.util.protocol.IProtocolUtils;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public class The5zigMod extends JavaPlugin {

	public static final String CHANNEL = "5zig";
	public static final int VERSION = 3;
	public static final String COLOR_CODE = "§z §i §g";
	private static The5zigMod instance;
	private UserManager userManager;
	private IProtocolUtils protocolUtils;

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

		if (!setupHooks())
			return;

		userManager = new UserManagerImpl();

		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents((Listener) userManager, this);

		getServer().getMessenger().registerOutgoingPluginChannel(this, CHANNEL);
		getServer().getMessenger().registerIncomingPluginChannel(this, CHANNEL, new ClientMessageListener(this));
		getLogger().info("Listening on channel " + CHANNEL);

		getLogger().info("The 5zig Mod Server API v" + getDescription().getVersion() + " has been enabled!");
	}

	/**
	 * Setup version independent ProtocolUtils
	 */
	private boolean setupHooks() {
		String packageName = getServer().getClass().getPackage().getName();
		// org.bukkit.craftbukkit.version
		String version = packageName.substring(packageName.lastIndexOf('.') + 1);
		getLogger().info("Trying to setup Hooks for CraftBukkit ");
		try {
			Class<?> clazz = Class.forName("eu.the5zig.mod.server.util.protocol.ProtocolUtils_" + version);
			if (IProtocolUtils.class.isAssignableFrom(clazz)) {
				this.protocolUtils = (IProtocolUtils) clazz.getConstructor().newInstance();
			} else {
				throw new ClassCastException();
			}
			getLogger().info("Successfully hooked into CraftBukkit " + version);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			getLogger().severe("Could not find support for this CraftBukkit version");
			getLogger().info("Please visit 5zig.eu for an updated Version.");
			setEnabled(false);
			return false;
		}
	}

	/**
	 * Gets the UserManager. The Manager class stores all connected 5zig Mod users in a list
	 *
	 * @return The User Manager class
	 */
	public UserManager getUserManager() {
		return userManager;
	}

	public IProtocolUtils getProtocolUtils() {
		return protocolUtils;
	}
}
