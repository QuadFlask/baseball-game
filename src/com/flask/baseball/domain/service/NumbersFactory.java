package com.flask.baseball.domain.service;

import java.util.Collections;
import java.util.List;

import com.flask.baseball.domain.model.Numbers;
import com.google.common.collect.Lists;

public class NumbersFactory {
	private static List<Integer> numbers = Collections.synchronizedList(Lists.newArrayList(14));
	static {
		numbers.clear();
		for (int i = 0; i < 10; i++)
			numbers.add(i);
	}

	private NumbersFactory() {
	}

	public static Numbers createRandom() {
		return new Numbers(generateShuffleNumbers());
	}

	private static int[] generateShuffleNumbers() {
		Collections.shuffle(numbers);
		int[] result = new int[3];
		for (int i = 0; i < 3; i++)
			result[i] = numbers.get(i);
		return result;
	}
}
