package com.flask.baseball.domain.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.flask.baseball.domain.model.CheckResult;
import com.flask.baseball.domain.model.Numbers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class AiTest {
	private Checker checker;

	@Before
	public void setup() {
		checker = new Checker();
	}

	@Test
	public void randomTest() {
		int tryCount = 1000000;
		int sum = 0;
		int max = 0;
		int min = Integer.MAX_VALUE;
		List<Integer> result = Lists.newArrayList();

		for (int i = 0; i < tryCount; i++) {
			int test = test(NumbersFactory.createRandom());
			max = Math.max(max, test);
			min = Math.min(min, test);
			sum += test;
			result.add(test);
		}

		printStatistic(tryCount, sum, max, min, result);
	}

	private void printStatistic(int tryCount, int sum, int max, int min, List<Integer> result) {
		Collections.sort(result);
		System.out.println("average: " + sum / tryCount);
		System.out.println("min: " + min + ",  max: " + max);
		System.out.println("mid: " + result.get(tryCount / 2));

		Map<Integer, Integer> graph = Maps.newConcurrentMap();
		for (Integer value : result) {
			Integer sum1 = graph.get(value);
			if (sum1 == null)
				graph.put(value, 1);
			else
				graph.put(value, sum1 + 1);
		}
		Set<Entry<Integer, Integer>> entrySet = graph.entrySet();
		Integer[] arr = new Integer[max + 1];
		int total = 0;
		for (Entry<Integer, Integer> entry : entrySet) {
			arr[entry.getKey()] = entry.getValue();
			total += (entry.getValue() == null ? 0 : entry.getValue());
		}

		int sum2 = 0;
		for (int i = 1; i < arr.length; i++) {
			sum2 += (arr[i] == null ? 0 : arr[i]);
			System.out.println(i + "\t" + arr[i] + "(" + ((double) sum2 / total) + ")");
		}
	}

	private int test(Numbers userNumber) {
		Ai ai = new Ai();
		CheckResult checkResult;
		do {
			Numbers nextGuess = ai.getNextGuess();
			checkResult = checker.check(userNumber, nextGuess);
			ai.guessResultIn(nextGuess, checkResult);
		} while (!checkResult.isSame());
		return ai.getTryCount();
	}

}
