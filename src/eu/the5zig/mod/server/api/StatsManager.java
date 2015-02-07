package eu.the5zig.mod.server.api;

import java.awt.image.BufferedImage;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public interface StatsManager {

	/**
	 * Gets a Stat specified by its name.
	 *
	 * @param name The name of the stat.
	 * @return The Stat.
	 * @throws RuntimeException if you want to add more then 10 stat items.
	 */
	public Stat getStat(String name);

	/**
	 * Resets a Stat of the Player specified by its name
	 *
	 * @param name The name of the stat.
	 */
	public void resetStat(String name);

	/**
	 * Checks if the Stat already exists.
	 *
	 * @param name The name of the stat.
	 * @return If the Stat exists.
	 */
	public boolean hasStat(String name);

	/**
	 * Gets the Display Name of the Stat. The Display Name is shown as a 'Label' or Title for all following stats.
	 *
	 * @return The Display Name of the Stat.
	 */
	public String getDisplayName();

	/**
	 * Sets the Display Name of the Stat. The Display Name is shown as a 'Label' or Title for all following stats.<br>
	 * Maximum length for the Display Name is 32 characters.
	 *
	 * @param displayName The new Display Name.
	 */
	public void setDisplayName(String displayName);

	/**
	 * Sends an Image to the Mod User. The Image will be sent as Base64 encoded String.
	 *
	 * @param image The BufferedImage that should be sent.
	 */
	public void sendImage(BufferedImage image);

	/**
	 * Sends an Image to the Mod User. The Image will be sent as Base64 encoded String.
	 *
	 * @param path The Path where the image is located (in your bukkit server directory).
	 */
	public void sendImage(String path);

	/**
	 * Removes the Image from the Mod User. The Image itself should have been sent already.
	 *
	 * @param image The BufferedImage that should be removed.
	 */
	public void resetImage(BufferedImage image);

	/**
	 * Removes the Image from the Mod User. The Image itself should have been sent already.
	 *
	 * @param path The Path where the image is located (in your bukkit server directory).
	 */
	public void resetImage(String path);

	/**
	 * Clears all stats.
	 */
	public void clearStats();

}
