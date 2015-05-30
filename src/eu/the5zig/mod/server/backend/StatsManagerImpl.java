package eu.the5zig.mod.server.backend;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.bukkit.Bukkit;
import org.spigotmc.AsyncCatcher;

import com.google.common.collect.Maps;

import eu.the5zig.mod.server.The5zigMod;
import eu.the5zig.mod.server.api.ImageRegistry;
import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.Stat;
import eu.the5zig.mod.server.api.StatsManager;
import eu.the5zig.mod.server.util.Utils;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public class StatsManagerImpl implements StatsManager {

	private ModUser modUser;
	private String displayName;

	private String largeText;

	private HashMap<String, Stat> stats;
	private HashMap<String, Integer> images;

	StatsManagerImpl(ModUser modUser) {
		this.modUser = modUser;
		this.stats = Maps.newHashMap();
		this.images = Maps.newHashMap();
		displayName = Bukkit.getServerName();
	}

	@Override
	public Stat getStat(String name) {
		Validate.notNull(name, "Stat name cannot be null.");
		Validate.notEmpty(name, "Stat name cannot be empty.");
		Validate.validState(name.length() <= 100, "Length of Stat name cannot exceed 100 characters.");

		if (!hasStat(name)) {
			if (stats.size() >= 10)
				throw new UnsupportedOperationException("You can only create up to 10 stats per player!");
			stats.put(name, new StatImpl(name, modUser));
		}
		return stats.get(name);
	}

	@Override
	public void resetStat(String name) {
		Validate.notNull(name, "Stat name cannot be null.");
		Validate.notEmpty(name, "Stat name cannot be empty.");

		if (!hasStat(name))
			return;
		Stat stat = stats.get(name);
		The5zigMod.getInstance().getProtocolUtils().resetStat(modUser, stat);
		stats.remove(name);
	}

	@Override
	public boolean hasStat(String name) {
		Validate.notNull(name, "Stat name cannot be null.");
		Validate.notEmpty(name, "Stat name cannot be empty.");

		return stats.containsKey(name);
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public void setDisplayName(String displayName) {
		Validate.notNull(displayName, "Display Name cannot be null.");
		Validate.notEmpty(displayName, "Display Name cannot be empty.");
		Validate.validState(displayName.length() <= 150, "Length of Display Name cannot exceed 150 characters.");

		this.displayName = displayName;
		The5zigMod.getInstance().getProtocolUtils().sendDisplayName(modUser, displayName);
	}

	@Override
	public void sendLargeText(String text) {
		Validate.notNull(text, "Text cannot be null.");
		Validate.notEmpty(text, "Text cannot be empty.");
		Validate.validState(text.length() <= 250, "Length of Text cannot exceed 150 characters.");

		this.largeText = text;
		The5zigMod.getInstance().getProtocolUtils().sendLargeText(modUser, largeText);
	}

	@Override
	public void resetLargeText() {
		if (largeText == null)
			return;
		largeText = null;
		The5zigMod.getInstance().getProtocolUtils().resetLargeText(modUser);
	}

	@Override
	public void clearStats() {
		if (stats.isEmpty())
			return;
		stats.clear();
		The5zigMod.getInstance().getProtocolUtils().sendClearStats(modUser);
	}

	@Override
	public void sendImage(String key) {
		ImageRegistry imageRegistry = The5zigMod.getInstance().getImageRegistry();
		
		AsyncCatcher.catchOp("image sent");
		Validate.validState(imageRegistry.isRegistered(key), "Key hasn't been registered yet.");

		if (!images.containsKey(key)) {
			images.put(key, imageRegistry.getId(key));
			The5zigMod.getInstance().getProtocolUtils().sendImage(modUser, imageRegistry.getData(key), imageRegistry.getId(key));
		} else {
			The5zigMod.getInstance().getProtocolUtils().sendImage(modUser, images.get(key));
		}
	}
	
	@Override
	public void sendImage(BufferedImage image) {
		Validate.notNull(image, "Image cannot be null.");
		Validate.validState(image.getWidth() == 64, "Image width must be 64 pixels.");
		Validate.validState(image.getHeight() == 64, "Image height must be 64 pixels.");
		Utils.checkImageSize(image, Short.MAX_VALUE);
		
		try {
			final String base64 = Utils.getBase64(image);
			The5zigMod.getInstance().getProtocolUtils().sendImage(modUser, base64, ((ImageRegistryImpl)The5zigMod.getInstance().getImageRegistry()).getNextId());
		} catch (IOException e) {
			LogManager.getLogger().warn("Could not send Image to " + modUser.getPlayer().getName(), e);
		}
	}

	@Override
	public void resetImage() {
		The5zigMod.getInstance().getProtocolUtils().resetImage(modUser);
	}
	
	@Override
	public void sendOverlay(String message) {
		Validate.notNull(message, "Message cannot be null.");
		Validate.notEmpty(message, "Message cannot be empty.");
		Validate.validState(message.length() <= 100, "Message cannot be longer than 100 characters.");
		
		The5zigMod.getInstance().getProtocolUtils().sendOverlay(modUser, message);
	}

}
