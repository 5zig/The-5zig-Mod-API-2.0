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
	 * Sends a Large Text to the Client. The Text will be displayed like a Minecraft 1.8 Title, but it is a lot smaller, and therefore more text can be displayed.
	 * The Text will be also splitted into multiple lines by the client itself, if it is too long. The Large Text still cannot be longer than 250 characters.
	 * <p/>
	 * Use {@code \n} to force a new line. The client can render a maximum of 10 lines!
	 * <p/>
	 * You can only have one large text at the same time. Calling this method, of there is still a large text active will simply override it.
	 * <p/>
	 * Use {@link eu.the5zig.mod.server.api.StatsManager#resetLargeText()} to reset the Large Text.
	 *
	 * @param text The text that should be displayed.
	 */
	public void sendLargeText(String text);

	/**
	 * Resets a Large Text if there is one.
	 */
	public void resetLargeText();

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
	 * Clears all stats. Does <b>not</b> reset the Large Text!
	 */
	public void clearStats();

}
