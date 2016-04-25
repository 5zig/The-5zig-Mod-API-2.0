package eu.the5zig.mod.server.util.protocol;

/**
 * A class that manages the creation of Packet Buffers, depending on the current bukkit/spigot version.
 */
public interface IBufferUtils {

	/**
	 * Creates a Packet Buffer that stores all data that should be send by the custom payload.
	 *
	 * @return a newly created Buffer.
	 */
	IPacketBuffer createBuffer();

	/**
	 * Creates a Packet Buffer that stores all data that should be send by the custom payload.
	 *
	 * @param payloadType the type of the custom payload. Will be written as integer to the buffer after its creation.
	 * @return a newly created Buffer.
	 */
	IPacketBuffer createBuffer(Protocol.PayloadType payloadType);

}
