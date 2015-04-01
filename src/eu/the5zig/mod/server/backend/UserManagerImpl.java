package eu.the5zig.mod.server.backend;

import com.google.common.collect.Maps;
import eu.the5zig.mod.server.The5zigMod;
import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public class UserManagerImpl implements UserManager, Listener {

	private HashMap<UUID, ModUser> onlineModUsers = Maps.newHashMap();

	public UserManagerImpl() {
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().sendMessage(The5zigMod.COLOR_CODE);
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
	public Collection<ModUser> getOnlineModUsers() {
		return Collections.unmodifiableCollection(onlineModUsers.values());
	}

	public void addUser(ModUser modUser) {
		onlineModUsers.put(modUser.getPlayer().getUniqueId(), modUser);
	}

	@Override
	public boolean isModUser(Player player) {
		return onlineModUsers.containsKey(player.getUniqueId()) && onlineModUsers.get(player.getUniqueId()).isConnected();
	}
}
