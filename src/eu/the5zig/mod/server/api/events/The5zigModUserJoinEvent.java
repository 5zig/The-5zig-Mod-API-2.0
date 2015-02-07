package eu.the5zig.mod.server.api.events;

import eu.the5zig.mod.server.api.ModUser;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public class The5zigModUserJoinEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private ModUser modUser;

	public The5zigModUserJoinEvent(ModUser modUser) {
		this.modUser = modUser;
	}

	public Player getPlayer() {
		return modUser.getPlayer();
	}

	public ModUser getModUser() {
		return modUser;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
