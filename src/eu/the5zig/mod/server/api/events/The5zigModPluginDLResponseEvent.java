package eu.the5zig.mod.server.api.events;

import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.util.protocol.Protocol;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class The5zigModPluginDLResponseEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private ModUser modUser;
	private Protocol.ModPluginResponse response;

	public The5zigModPluginDLResponseEvent(ModUser modUser, Protocol.ModPluginResponse response) {
		this.modUser = modUser;
		this.response = response;
	}

	public Player getPlayer() {
		return modUser.getPlayer();
	}

	public ModUser getModUser() {
		return modUser;
	}

	public Protocol.ModPluginResponse getResponse() {
		return response;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
