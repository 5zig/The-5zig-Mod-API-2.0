package eu.the5zig.mod.server.util.protocol;

import eu.the5zig.mod.server.The5zigMod;
import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.Stat;
import org.bukkit.entity.Player;

public interface IProtocolUtils {

	/**
	 * Sends a Register request to the client, containing the current version of the API.
	 * If the client has the 5zig mod installed (and the corrent version of it), he will
	 * send back a payload containing one byte with value 1 on Channel {@link The5zigMod#CHANNEL}
	 *
	 * @param player The player that should be requested to be registered.
	 */
	void requestRegister(Player player);

	/**
	 * Sends a stat to a mod user.
	 *
	 * @param modUser The Mod User that should receive the stat.
	 * @param stat    The Stat itself.
	 */
	void sendStat(ModUser modUser, Stat stat);

	/**
	 * Resets a stat of a mod user.
	 *
	 * @param modUser The Mod User whose stat should be resetted.
	 * @param stat    The Stat itself.
	 */
	void resetStat(ModUser modUser, Stat stat);

	/**
	 * Resets a stat of a mod user.
	 *
	 * @param modUser The Mod User whose stat should be resetted.
	 */
	void sendClearStats(ModUser modUser);

	/**
	 * Sends a new Display Name to a mod user.
	 *
	 * @param modUser     The Mod User that should receive the Display Name.
	 * @param displayName The new Display Name.
	 */
	void sendDisplayName(ModUser modUser, String displayName);

	/**
	 * Sends a large Text to a mod user.
	 *
	 * @param modUser   The Mod User that should receive the large Text.
	 * @param largeText The Text that should be send.
	 */
	void sendLargeText(ModUser modUser, String largeText);

	/**
	 * Resets the Large Text of a mod user.
	 *
	 * @param modUser The Mod User whose Text should be removed.
	 */
	void resetLargeText(ModUser modUser);

	/**
	 * Sends an image with its server side generated id to a mod user.
	 *
	 * @param modUser The Mod User that should see the image.
	 * @param base64  The base64 String of the Image.
	 * @param id      The Server-side generated id.
	 */
	void sendImage(ModUser modUser, String base64, int id);

	/**
	 * Sends the id of the cached image to a mod user.
	 *
	 * @param modUser The Mod User that should see the image.
	 * @param id      The Id of the Image.
	 */
	void sendImage(ModUser modUser, int id);

	/**
	 * Resets a image of the Mod User.
	 *
	 * @param modUser The Mod User where the image should be removed.
	 */
	void resetImage(ModUser modUser);

	/**
	 * Sends an overlay message to the Mod User. Can be split up with the \n character.
	 *
	 * @param modUser The Mod User that should receive the overlay message.
	 * @param message The overlay message.
	 */
	void sendOverlay(ModUser modUser, String message);

	/**
	 * Sends a countdown to the Mod User.
	 *
	 * @param modUser The Mod User that should receive the countdown.
	 * @param name    The display name of the countdown.
	 * @param time    The time of the countdown in ms.
	 */
	void sendCountdown(ModUser modUser, String name, long time);

	enum PayloadType {

		UPDATE, RESET, CLEAR, DISPLAY_NAME, IMAGE, IMAGE_ID, RESET_IMAGE, LARGE_TEXT, RESET_LARGE_TEXT, OVERLAY, COUNTDOWN

	}

}
