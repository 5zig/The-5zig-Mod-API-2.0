package eu.the5zig.mod.server.api;

import org.bukkit.entity.Player;

import java.util.Collection;

public interface UserManager {

	/**
	 * Gets The Mod User by a Player. If the Player doesn't use the 5zig Mod it returns null.
	 *
	 * @param player The Player that uses the Mod.
	 * @return The ModUser class of the Player or null if the Player doesn't play with the 5zig Mod.
	 */
	ModUser getUser(Player player);

	/**
	 * Returns all online Mod Users
	 *
	 * @return A Set with all online Mod Users
	 */
	Collection<ModUser> getOnlineModUsers();

	/**
	 * Checks if that Player is already connected using the 5zig Mod.
	 *
	 * @param player The Player that should be checked.
	 * @return If the Player is already connected using the 5zig Mod.
	 */
	boolean isModUser(Player player);

}
