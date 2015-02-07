package eu.the5zig.mod.server.api;

import org.bukkit.entity.Player;

import java.util.Set;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public interface UserManager {

	/**
	 * Gets The Mod User by a Player. If the Player doesn't use the 5zig Mod it returns null.
	 *
	 * @param player The Player that uses the Mod.
	 * @return The ModUser class of the Player or null if the Player doesn't play with the 5zig Mod.
	 */
	public ModUser getUser(Player player);

	/**
	 * Returns all online Mod Users
	 *
	 * @return A Set with all online Mod Users
	 */
	public Set<ModUser> getOnlineModUsers();

	/**
	 * Adds a Mod User to the online Mod User list.<br>
	 * This method should <b>only</b> be accessed from the API itself and not from any other plugin.
	 *
	 * @param modUser The Mod User that should be added.
	 */
	public void addUser(ModUser modUser);

	/**
	 * Removes a Mod User to the online Mod User list.<br>
	 * This method should <b>only</b> be accessed from the API itself and not from any other plugin.
	 *
	 * @param modUser The Mod User that should be removed.
	 */
	public void removeUser(ModUser modUser);

	/**
	 * Checks if that Player is already connected using the 5zig Mod.
	 *
	 * @param player The Player that should be checked.
	 * @return If the Player is already connected using the 5zig Mod.
	 */
	public boolean isModUser(Player player);

}
