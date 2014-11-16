package com.flask.baseball.domain.model;

public class CheckResult {
	private boolean isSame;
	private int strikeCount;
	private int ballCount;

	public CheckResult(boolean isSame, int strikeCount, int ballCount) {
		this.isSame = isSame;
		this.strikeCount = strikeCount;
		this.ballCount = ballCount;
	}

	public boolean isSame() {
		return isSame;
	}

	public int getStrikeCount() {
		return strikeCount;
	}

	public int getBallCount() {
		return ballCount;
	}

	public boolean isOut() {
		return strikeCount == 0 && ballCount == 0;
	}

	@Override
	public String toString() {
		String string = "";
		if (isOut())
			string = "OUT";
		if (getStrikeCount() > 0)
			string = getStrikeCount() + "S ";
		if (getBallCount() > 0)
			string += getBallCount() + "B";
		return string;
	}
}
