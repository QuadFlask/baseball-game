package com.flask.baseball.domain.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.flask.baseball.domain.model.CheckResult;
import com.flask.baseball.domain.model.Numbers;

public class CheckerTest {

	@Test
	public void checkSame() {
		checkNumbers(new Numbers(1, 2, 3), new Numbers(1, 2, 3), 3, 0);
	}

	@Test
	public void cehckNotSame() {
		checkNumbers(new Numbers(1, 2, 3), new Numbers(4, 5, 6), 0, 0);
	}

	@Test
	public void cehck_strikes() {
		checkNumbers(new Numbers(1, 2, 3), new Numbers(4, 5, 6), 0, 0);
		checkNumbers(new Numbers(1, 2, 3), new Numbers(1, 5, 6), 1, 0);
		checkNumbers(new Numbers(1, 2, 3), new Numbers(1, 2, 6), 2, 0);
		checkNumbers(new Numbers(1, 2, 3), new Numbers(1, 2, 3), 3, 0);
	}

	@Test
	public void checkBall() {
		checkNumbers(new Numbers(1, 2, 3), new Numbers(4, 5, 6), 0, 0);
		checkNumbers(new Numbers(1, 2, 3), new Numbers(2, 5, 6), 0, 1);

		checkNumbers(new Numbers(1, 2, 3), new Numbers(2, 1, 6), 0, 2);
		checkNumbers(new Numbers(1, 2, 3), new Numbers(2, 4, 1), 0, 2);

		checkNumbers(new Numbers(1, 2, 3), new Numbers(3, 1, 2), 0, 3);
	}

	private void checkNumbers(Numbers numbers, Numbers numbers2, int strike, int ball) {
		Checker checker = new Checker();
		CheckResult result = checker.check(numbers, numbers2);

		assertEquals(strike, result.getStrikeCount());
		assertEquals(ball, result.getBallCount());
	}
}
