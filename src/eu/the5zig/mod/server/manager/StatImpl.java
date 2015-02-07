package eu.the5zig.mod.server.manager;

import eu.the5zig.mod.server.api.ModUser;
import eu.the5zig.mod.server.api.Stat;
import eu.the5zig.mod.server.util.ProtocolUtils;
import org.apache.commons.lang3.Validate;

/**
 * Created by 5zig.
 * All rights reserved © 2015
 */
public class StatImpl implements Stat {

	private ModUser modUser;
	private String name;
	private String score;

	public StatImpl(String name, ModUser modUser) {
		this.name = name;
		this.modUser = modUser;
	}

	@Override
	public String getScore() {
		return score;
	}

	@Override
	public void setScore(String score) {
		Validate.notNull(score, "Score cannot be null.");
		Validate.notEmpty(score, "Score cannot be empty.");
		Validate.validState(score.length() <= 100, "Length of score cannot exceed 100 characters.");

		this.score = score;
		ProtocolUtils.sendStat(modUser, this);
	}

	@Override
	public String getName() {
		return name;
	}
}
