package com.flask.baseball.domain.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.flask.baseball.domain.model.Numbers;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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

	public static Set<Numbers> createAllPossibilities() {
		Set<Numbers> sets = Sets.newConcurrentHashSet();
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				for (int k = 0; k < 10; k++)
					if (i != j && j != k && i != k)
						sets.add(new Numbers(i, j, k));
		return sets;
	}
}
