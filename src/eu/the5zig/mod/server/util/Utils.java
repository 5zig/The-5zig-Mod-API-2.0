package eu.the5zig.mod.server.util;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public class Utils {

	public static String getBase64(BufferedImage bufferedImage) throws IOException {
		// Converting Image byte array into Base64 String
		ByteBuf localByteBuf1 = Unpooled.buffer();
		ImageIO.write(bufferedImage, "PNG", new ByteBufOutputStream(localByteBuf1));
		ByteBuf localByteBuf2 = Base64.encode(localByteBuf1);
		return localByteBuf2.toString(Charsets.UTF_8);
	}

	/**
	 * Checks the size of a BufferedImage.
	 *
	 * @param image   The BufferedImage.
	 * @param maxSize The maximum allowed size of the Image.
	 */
	public static void checkImageSize(BufferedImage image, long maxSize) {
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", tmp);
			if (tmp.size() > maxSize) {
				throw new IllegalStateException("Image size may not be larger than " + maxSize + "!");
			}
		} catch (IOException ignored) {
		} finally {
			IOUtils.closeQuietly(tmp);
		}
	}

}
