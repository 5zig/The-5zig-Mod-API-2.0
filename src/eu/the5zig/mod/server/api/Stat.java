package eu.the5zig.mod.server.api;

public interface Stat {

	/**
	 * Gets the current score of the stat.
	 *
	 * @return The score if the Stat
	 */
	String getScore();

	/**
	 * Sets the score of the stat and sends it to the client. Score length cannot exceed 100 characters.
	 *
	 * @param score The score that should be sent.
	 */
	void setScore(String score);

	/**
	 * Returns the name of the Stat.
	 *
	 * @return The name of the Stat.
	 */
	String getName();

}
