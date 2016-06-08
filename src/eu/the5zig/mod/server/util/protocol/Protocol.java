package eu.the5zig.mod.server.util.protocol;

import eu.the5zig.mod.server.The5zigMod;
import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.Stat;
import org.bukkit.entity.Player;

public class Protocol {

	private IBufferUtils bufferUtils;

	public Protocol(IBufferUtils bufferUtils) {
		this.bufferUtils = bufferUtils;
	}

	/**
	 * Sends a Register request to the client, containing the current version of the API.
	 * If the client has the 5zig mod installed (and the corrent version of it), he will
	 * send back a payload containing one byte with value 1 on Channel {@link The5zigMod#CHANNEL}
	 *
	 * @param player The player that should be requested to be registered.
	 */
	public void requestRegister(Player player) {
		IPacketBuffer buffer = bufferUtils.createBuffer();
		buffer.writeInt(The5zigMod.VERSION);

		buffer.send(player, The5zigMod.CHANNEL_REGISTER);
	}

	/**
	 * Sends a stat to a mod user.
	 *
	 * @param modUser The Mod User that should receive the stat.
	 * @param stat    The Stat itself.
	 */
	public void sendStat(ModUser modUser, Stat stat) {
		IPacketBuffer buffer = bufferUtils.createBuffer(PayloadType.UPDATE);
		buffer.writeString(stat.getName());
		buffer.writeString(stat.getScore());

		buffer.send(modUser.getPlayer(), The5zigMod.CHANNEL);
	}

	/**
	 * Resets a stat of a mod user.
	 *
	 * @param modUser The Mod User whose stat should be resetted.
	 * @param stat    The Stat itself.
	 */
	public void resetStat(ModUser modUser, Stat stat) {
		IPacketBuffer buffer = bufferUtils.createBuffer(PayloadType.RESET);
		buffer.writeString(stat.getName());

		buffer.send(modUser.getPlayer(), The5zigMod.CHANNEL);
	}

	/**
	 * Resets a stat of a mod user.
	 *
	 * @param modUser The Mod User whose stat should be resetted.
	 */
	public void sendClearStats(ModUser modUser) {
		IPacketBuffer buffer = bufferUtils.createBuffer(PayloadType.CLEAR);

		buffer.send(modUser.getPlayer(), The5zigMod.CHANNEL);
	}

	/**
	 * Sends a new Display Name to a mod user.
	 *
	 * @param modUser     The Mod User that should receive the Display Name.
	 * @param displayName The new Display Name.
	 */
	public void sendDisplayName(ModUser modUser, String displayName) {
		IPacketBuffer buffer = bufferUtils.createBuffer(PayloadType.DISPLAY_NAME);
		buffer.writeString(displayName);

		buffer.send(modUser.getPlayer(), The5zigMod.CHANNEL);
	}

	/**
	 * Sends a large Text to a mod user.
	 *
	 * @param modUser   The Mod User that should receive the large Text.
	 * @param largeText The Text that should be send.
	 */
	public void sendLargeText(ModUser modUser, String largeText) {
		IPacketBuffer buffer = bufferUtils.createBuffer(PayloadType.LARGE_TEXT);
		buffer.writeString(largeText);

		buffer.send(modUser.getPlayer(), The5zigMod.CHANNEL);
	}

	/**
	 * Resets the Large Text of a mod user.
	 *
	 * @param modUser The Mod User whose Text should be removed.
	 */
	public void resetLargeText(ModUser modUser) {
		IPacketBuffer buffer = bufferUtils.createBuffer(PayloadType.RESET_LARGE_TEXT);

		buffer.send(modUser.getPlayer(), The5zigMod.CHANNEL);
	}

	/**
	 * Sends an image with its server side generated id to a mod user.
	 *
	 * @param modUser The Mod User that should see the image.
	 * @param base64  The base64 String of the Image.
	 * @param id      The Server-side generated id.
	 */
	public void sendImage(ModUser modUser, String base64, int id) {
		IPacketBuffer buffer = bufferUtils.createBuffer(PayloadType.IMAGE);
		buffer.writeString(base64);
		buffer.writeInt(id);

		buffer.send(modUser.getPlayer(), The5zigMod.CHANNEL);
	}

	/**
	 * Sends the id of the cached image to a mod user.
	 *
	 * @param modUser The Mod User that should see the image.
	 * @param id      The Id of the Image.
	 */
	public void sendImage(ModUser modUser, int id) {
		IPacketBuffer buffer = bufferUtils.createBuffer(PayloadType.IMAGE_ID);
		buffer.writeInt(id);

		buffer.send(modUser.getPlayer(), The5zigMod.CHANNEL);
	}

	/**
	 * Resets a image of the Mod User.
	 *
	 * @param modUser The Mod User where the image should be removed.
	 */
	public void resetImage(ModUser modUser) {
		IPacketBuffer buffer = bufferUtils.createBuffer(PayloadType.RESET_IMAGE);

		buffer.send(modUser.getPlayer(), The5zigMod.CHANNEL);
	}

	/**
	 * Sends an overlay message to the Mod User. Can be split up with the \n character.
	 *
	 * @param modUser The Mod User that should receive the overlay message.
	 * @param message The overlay message.
	 */
	public void sendOverlay(ModUser modUser, String message) {
		IPacketBuffer buffer = bufferUtils.createBuffer(PayloadType.OVERLAY);
		buffer.writeString(message);

		buffer.send(modUser.getPlayer(), The5zigMod.CHANNEL);
	}

	/**
	 * Sends a countdown to the Mod User.
	 *
	 * @param modUser The Mod User that should receive the countdown.
	 * @param name    The display name of the countdown.
	 * @param time    The time of the countdown in ms.
	 */
	public void sendCountdown(ModUser modUser, String name, long time) {
		IPacketBuffer buffer = bufferUtils.createBuffer(PayloadType.COUNTDOWN);
		buffer.writeString(name);
		buffer.writeLong(time);

		buffer.send(modUser.getPlayer(), The5zigMod.CHANNEL);
	}

	/**
	 * Sends a mod plugin download request to the Mod User.
	 *
	 * @param modUser       The Mod User that should receive the request.
	 * @param pluginName    The name of the plugin.
	 * @param sha1Hash      The sha1 hash of the plugin.
	 * @param downloadPath  The download path of the plugin. The plugin will be only downloaded if the client receives this request for the first time or if the previously downloaded plugin
	 *                      does not match the sent hash.
	 * @param customMessage A custom message that will be displayed to the client.
	 */
	public void sendModPluginRequest(ModUser modUser, String pluginName, String sha1Hash, String downloadPath, String customMessage) {
		IPacketBuffer buffer = bufferUtils.createBuffer(PayloadType.MOD_PLUGIN);
		buffer.writeString(pluginName);
		buffer.writeString(sha1Hash);
		buffer.writeString(downloadPath);
		buffer.writeString(customMessage);

		buffer.send(modUser.getPlayer(), The5zigMod.CHANNEL);
	}

	public enum PayloadType {
		UPDATE, RESET, CLEAR, DISPLAY_NAME, IMAGE, IMAGE_ID, RESET_IMAGE, LARGE_TEXT, RESET_LARGE_TEXT, OVERLAY, COUNTDOWN, MOD_PLUGIN
	}

	public enum ModPluginResponse {
		DENIED, ALREADY_LOADED, DOWNLOADED, DOWNLOAD_FAILED
	}

}
