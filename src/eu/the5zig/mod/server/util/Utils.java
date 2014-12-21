package eu.the5zig.mod.server.util;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by 5zig.
 * All rights reserved © 2014
 */
public class Utils {

	public static String getBase64(BufferedImage bufferedImage) throws IOException {
		// Converting Image byte array into Base64 String
		ByteBuf localByteBuf1 = Unpooled.buffer();
		ImageIO.write(bufferedImage, "PNG", new ByteBufOutputStream(localByteBuf1));
		ByteBuf localByteBuf2 = Base64.encode(localByteBuf1);
		return localByteBuf2.toString(Charsets.UTF_8);
	}

}
