package com.flask.baseball.domain.service;

import com.flask.baseball.domain.model.CheckResult;
import com.flask.baseball.domain.model.Numbers;

public class Checker {

	public CheckResult check(Numbers numbers, Numbers numbers2) {
		boolean isSame = numbers.equals(numbers2);
		int strikeCount = 0, ballCount = 0;
		int numbersLength = numbers.length();
		
		for (int i = 0; i < numbersLength; i++) {
			if (numbers.at(i) == numbers2.at(i))
				strikeCount++;
			else
				for (int j = 0; j < numbersLength; j++)
					if (i != j && numbers.at(i) == numbers2.at(j))
						ballCount++;
		}

		return new CheckResult(isSame, strikeCount, ballCount);
	}
}
