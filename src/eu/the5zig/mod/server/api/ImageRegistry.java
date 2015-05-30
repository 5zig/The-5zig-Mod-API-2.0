package eu.the5zig.mod.server.api;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by 5zig. All rights reserved © 2015 </p> A Class that manages all
 * images that can be send to a mod user.
 */
public interface ImageRegistry {

	/**
	 * Registers an image.
	 * Max. image size is {@link Short#MAX_VALUE}. The image must be 64x64 pixels.
	 * </p>
	 * Must be executed in the main server thread.
	 * 
	 * @param key The key under that the image should be registered. The key must be used in {@link StatsManager#sendImage(String)}.<br>
	 * Example key: {@code "testplugin.image1"}
	 * @param image The image that should be registered.
	 * @return The id of the registered image.
	 * @throws IOException if the image could not be found or the base64 String of the image could not be calculated.
	 */
	public void register(String key, BufferedImage image) throws IOException ;

	/**
	 * Registers an image.
	 * Max. image size is {@link Short#MAX_VALUE}. The image must be 64x64 pixels.
	 * </p>
	 * Must be executed in the main server thread.
	 * 
	 * @param key The key under that the image should be registered. The key must be used in {@link StatsManager#sendImage(String)}.<br>
	 * Example key: {@code "testplugin.image1"}
	 * @param path The path of the image that should be registered.
	 * @return The id of the registered image.
	 * @throws IOException if the image could not be found or the base64 String of the image could not be calculated.
	 */
	public void register(String key, String path) throws IOException ;

	/**
	 * Unregisters an image.
	 * 
	 * @param key The key of the image that should be unregistered.
	 */
	public void unregister(String key);
	
	/**
	 * Checks, if the id is already registered.
	 * @param key The key that should be checked.
	 * 
	 * @return if the key is already registered.
	 */
	public boolean isRegistered(String key);
	
	/**
	 * Returns the id of the Image.
	 * @param key The key of the Image.
	 * 
	 * @return The id of the Image or -1, if the image does not exist.
	 */
	public int getId(String key);
	
	/**
	 * Returns the base64 String of the Image.
	 * @param key The key of the Image.
	 * 
	 * @return The base64 String of the Image or null, if the image does not exist.
	 */
	public String getData(String key);

}
