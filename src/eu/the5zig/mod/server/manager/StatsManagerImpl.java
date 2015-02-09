package eu.the5zig.mod.server.manager;

import com.google.common.collect.Maps;
import eu.the5zig.mod.server.The5zigMod;
import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.Stat;
import eu.the5zig.mod.server.api.StatsManager;
import eu.the5zig.mod.server.util.Utils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public class StatsManagerImpl implements StatsManager {

	private ModUser modUser;
	private String displayName;
	private HashMap<String, Stat> stats;
	private HashMap<String, Integer> images;
	private int idCounter = 0;

	public StatsManagerImpl(ModUser modUser) {
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
			if (stats.size() >= 10) throw new UnsupportedOperationException("You can only create up to 10 stats per player!");
			stats.put(name, new StatImpl(name, modUser));
		}
		return stats.get(name);
	}

	@Override
	public void resetStat(String name) {
		Validate.notNull(name, "Stat name cannot be null.");
		Validate.notEmpty(name, "Stat name cannot be empty.");

		if (!hasStat(name)) return;
		Stat stat = stats.get(name);
		ProtocolUtils.resetStat(modUser, stat);
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
		ProtocolUtils.sendDisplayName(modUser, displayName);
	}

	@Override
	public void clearStats() {
		if (stats.isEmpty()) return;
		stats.clear();
		ProtocolUtils.sendClearStats(modUser);
	}

	@Override
	public void sendImage(BufferedImage image) {
		Validate.notNull(image, "Image cannot be null.");
		Validate.validState(image.getWidth() == 64, "Image width must be 64 pixels.");
		Validate.validState(image.getHeight() == 64, "Image height must be 64 pixels.");

		try {
			String base64 = Utils.getBase64(image);
			if (images.containsKey(base64)) {
				ProtocolUtils.sendImage(modUser, images.get(base64));
			} else {
				images.put(base64, idCounter);
				ProtocolUtils.sendImage(modUser, Utils.getBase64(image), idCounter);
				idCounter++;
			}
		} catch (IOException e) {
			The5zigMod.getInstance().getLogger().warning("Could not send Image to " + modUser.getPlayer().getName() + ": " + e.getMessage());
		}
	}

	@Override
	public void sendImage(String path) {
		Validate.notNull(displayName, "Path cannot be null.");
		Validate.notEmpty(displayName, "Path cannot be empty.");

		try {
			sendImage(ImageIO.read(new File(path)));
		} catch (IOException e) {
			The5zigMod.getInstance().getLogger().warning("Could not send Image to " + modUser.getPlayer().getName() + ": " + e.getMessage());
		}
	}

	@Override
	public void resetImage(BufferedImage image) {
		Validate.notNull(image, "Image cannot be null.");
		Validate.validState(image.getWidth() == 64, "Image width must be 64 pixels.");
		Validate.validState(image.getHeight() == 64, "Image height must be 64 pixels.");

		try {
			String base64 = Utils.getBase64(image);
			if (images.containsKey(base64)) {
				ProtocolUtils.resetImage(modUser, images.get(base64));
				images.remove(base64);
			}
		} catch (IOException e) {
			The5zigMod.getInstance().getLogger().warning("Could not send Image to " + modUser.getPlayer().getName() + ": " + e.getMessage());
		}
	}

	@Override
	public void resetImage(String path) {
		Validate.notNull(displayName, "Path cannot be null.");
		Validate.notEmpty(displayName, "Path cannot be empty.");

		try {
			resetImage(ImageIO.read(new File(path)));
		} catch (IOException e) {
			The5zigMod.getInstance().getLogger().warning("Could not remove Image of " + modUser.getPlayer().getName() + ": " + e.getMessage());
		}
	}
}
