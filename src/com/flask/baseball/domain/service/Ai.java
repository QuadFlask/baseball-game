package com.flask.baseball.domain.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.flask.baseball.domain.model.CheckResult;
import com.flask.baseball.domain.model.Numbers;
import com.flask.baseball.domain.service.setreducer.SetReducer;
import com.google.common.collect.Maps;

public class Ai {
	private int tryCount = 0;
	private Map<Numbers, CheckResult> history;
	private Set<Numbers> remainders;
	private Numbers aiNumbers;

	public Ai() {
		init();
	}

	private void init() {
		history = Maps.newConcurrentMap();
		remainders = NumbersFactory.createAllPossibilities();
	}

	public Numbers getNextGuess() {
		tryCount++;
		return chooseNextRandomCombination();
	}

	private Numbers chooseNextRandomCombination() {
		Iterator<Numbers> iterator = remainders.iterator();
		Numbers generateRandom = iterator.next();
		iterator.remove();
		return generateRandom;
	}

	public void guessResultIn(Numbers guessed, CheckResult checkResult) {
		if (!checkResult.isSame()) {
			history.put(guessed, checkResult);
			try {
				SetReducer<Numbers> setReducer = SetReducerFactory.getSetReducer(checkResult);
				setReducer.reduce(remainders, guessed);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
	}

	public int getTryCount() {
		return tryCount;
	}

	public Numbers getAiNumbers() {
		return aiNumbers;
	}

	public void setAiNumbers(Numbers aiNumbers) {
		this.aiNumbers = aiNumbers;
	}
}
