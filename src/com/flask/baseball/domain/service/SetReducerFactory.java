package com.flask.baseball.domain.service;

import com.flask.baseball.domain.model.CheckResult;
import com.flask.baseball.domain.model.Numbers;
import com.flask.baseball.domain.service.setreducer.ExcludeSetReducer;
import com.flask.baseball.domain.service.setreducer.OneBallSetReducer;
import com.flask.baseball.domain.service.setreducer.OneStrikeOneBallSetReducer;
import com.flask.baseball.domain.service.setreducer.OneStrikeSetReducer;
import com.flask.baseball.domain.service.setreducer.OneStrikeTwoBallSetReducer;
import com.flask.baseball.domain.service.setreducer.SetReducer;
import com.flask.baseball.domain.service.setreducer.ThreeBallSetReducer;
import com.flask.baseball.domain.service.setreducer.TwoBallSetReducer;
import com.flask.baseball.domain.service.setreducer.TwoStrikeSetReducer;

public class SetReducerFactory {
	private static ExcludeSetReducer exceptSetReducer = new ExcludeSetReducer();
	private static OneBallSetReducer oneBallSetReducer = new OneBallSetReducer();
	private static TwoBallSetReducer twoBallSetReducer = new TwoBallSetReducer();
	private static ThreeBallSetReducer threeBallSetReducer = new ThreeBallSetReducer();
	private static OneStrikeSetReducer oneStrikeSetReducer = new OneStrikeSetReducer();
	private static TwoStrikeSetReducer twoStrikeSetReducer = new TwoStrikeSetReducer();
	private static OneStrikeOneBallSetReducer oneStrikeOneBallSetReducer = new OneStrikeOneBallSetReducer();
	private static OneStrikeTwoBallSetReducer oneStrikeTwoBallSetReducer = new OneStrikeTwoBallSetReducer();

	private SetReducerFactory() {
	}

	public static SetReducer<Numbers> getSetReducer(CheckResult checkResult) {
		if (checkResult.isOut()) {
			return exceptSetReducer;
		} else if (checkResult.getStrikeCount() == 0) {
			if (checkResult.getBallCount() == 1)
				return oneBallSetReducer;
			else if (checkResult.getBallCount() == 2)
				return twoBallSetReducer;
			else if (checkResult.getBallCount() == 3)
				return threeBallSetReducer;
		} else if (checkResult.getBallCount() == 0) {
			if (checkResult.getStrikeCount() == 1)
				return oneStrikeSetReducer;
			else if (checkResult.getStrikeCount() == 2)
				return twoStrikeSetReducer;
		} else {
			if (checkResult.getStrikeCount() == 1 && checkResult.getBallCount() == 1) {
				return oneStrikeOneBallSetReducer;
			} else if (checkResult.getStrikeCount() == 1 && checkResult.getBallCount() == 2) {
				return oneStrikeTwoBallSetReducer;
			}
		}
		throw new RuntimeException("can not find proper SetReducer for " + checkResult);
	}
}
