package eu.the5zig.mod.server.manager;

import com.google.common.collect.Maps;
import eu.the5zig.mod.server.The5zigMod;
import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.UserManager;
import eu.the5zig.mod.server.util.ProtocolUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by 5zig.
 * All rights reserved © 2014
 */
public class UserManagerImpl implements UserManager, Listener {

	private The5zigMod plugin;
	private HashMap<UUID, ModUser> onlineModUsers = Maps.newHashMap();

	public UserManagerImpl(The5zigMod plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().sendMessage(ProtocolUtils.COLOR_CODE);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		onlineModUsers.remove(event.getPlayer().getUniqueId());
	}

	@Override
	public ModUser getUser(Player player) {
		return onlineModUsers.get(player.getUniqueId());
	}

	@Override
	public Set<ModUser> getOnlineModUsers() {
		Set<ModUser> modUsers = new HashSet<ModUser>();
		modUsers.addAll(onlineModUsers.values());
		return modUsers;
	}

	@Override
	public void addUser(ModUser modUser) {
		onlineModUsers.put(modUser.getPlayer().getUniqueId(), modUser);
	}

	@Override
	public void removeUser(ModUser modUser) {
		onlineModUsers.remove(modUser.getPlayer().getUniqueId());
	}

	@Override
	public boolean isModUser(Player player) {
		return onlineModUsers.containsKey(player.getUniqueId()) && onlineModUsers.get(player.getUniqueId()).isConnected();
	}
}
