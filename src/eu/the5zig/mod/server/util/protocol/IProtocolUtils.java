package eu.the5zig.mod.server.util.protocol;

import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.Stat;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public interface IProtocolUtils {

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
	 * Sends a login response to the User
	 *
	 * @param modUser The Mod User that should receive the response
	 */
	void sendLogin(ModUser modUser, LoginResponse response);

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
	 * Resets a image of the Mod User by sending the id of the image.
	 *
	 * @param modUser The Mod User where the image should be removed.
	 * @param id      The Id of the Image.
	 */
	void resetImage(ModUser modUser, int id);

	enum PayloadType {

		UPDATE, RESET, CLEAR, DISPLAY_NAME, LOGIN, IMAGE, IMAGE_ID, RESET_IMAGE, LARGE_TEXT, RESET_LARGE_TEXT

	}

	enum LoginResponse {

		SUCCESS, OUTDATED_SERVER, OUTDATED_CLIENT

	}

}
