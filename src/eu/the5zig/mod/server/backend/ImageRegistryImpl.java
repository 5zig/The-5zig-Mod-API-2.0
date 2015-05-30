package eu.the5zig.mod.server.backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.Validate;
import org.spigotmc.AsyncCatcher;

import com.google.common.collect.Maps;

import eu.the5zig.mod.server.api.ImageRegistry;
import eu.the5zig.mod.server.util.Utils;

public class ImageRegistryImpl implements ImageRegistry {

	/**
	 * A HashMap, containing all image-keys, their unique id and their base64 Data.
	 */
	private HashMap<String, RegisteredImage> images = Maps.newHashMap();
	private AtomicInteger currentId = new AtomicInteger(0);
	
	@Override
	public void register(String key, BufferedImage image) throws IOException {
		AsyncCatcher.catchOp("image registered");
		
		Validate.validState(!isRegistered(key), "Image is already registered.");
		Validate.notNull(image, "Image cannot be null.");
		Validate.validState(image.getWidth() == 64, "Image width must be 64 pixels.");
		Validate.validState(image.getHeight() == 64, "Image height must be 64 pixels.");
		Utils.checkImageSize(image, Short.MAX_VALUE);
		
		String value = Utils.getBase64(image);
		images.put(key, new RegisteredImage(getNextId(), value));
	}

	@Override
	public void register(String key, String path) throws IOException {
		register(key, ImageIO.read(new File(path)));
	}

	@Override
	public void unregister(String key) {
		AsyncCatcher.catchOp("image unregistered");
		
		images.remove(key);
	}
	
	@Override
	public boolean isRegistered(String key) {
		AsyncCatcher.catchOp("image unregistered");
		
		return images.containsKey(key);
	}
	
	@Override
	public int getId(String key) {
		AsyncCatcher.catchOp("image unregistered");
		
		return isRegistered(key) ? images.get(key).id : -1;
	}

	@Override
	public String getData(String key) {
		AsyncCatcher.catchOp("image unregistered");
		
		return isRegistered(key) ? images.get(key).data : null;
	}
	
	public int getNextId() {
		return currentId.getAndIncrement();
	}
	
	private class RegisteredImage {
		
		private int id;
		private String data;
		
		public RegisteredImage(int id, String data) {
			this.id = id;
			this.data = data;
		}
		
	}
	
}
