package com.flask.baseball.domain.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import com.flask.baseball.domain.model.Numbers;
import com.flask.baseball.domain.service.setreducer.OneBallSetReducer;
import com.flask.baseball.domain.service.setreducer.OneStrikeSetReducer;
import com.flask.baseball.domain.service.setreducer.SetReducer;
import com.flask.baseball.domain.service.setreducer.ThreeBallSetReducer;
import com.flask.baseball.domain.service.setreducer.TwoBallSetReducer;
import com.flask.baseball.domain.service.setreducer.TwoStrikeSetReducer;
import com.google.common.collect.Sets;

public class SetReducerTest {

	@Test
	public void testOneBallSetReducer() {
		SetReducer<Numbers> reducer = new OneBallSetReducer();
		Set<Numbers> testSample = Sets.newConcurrentHashSet();
		testSample.add(new Numbers("123"));// x
		testSample.add(new Numbers("132"));// x
		testSample.add(new Numbers("456"));// x
		testSample.add(new Numbers("789"));// x
		testSample.add(new Numbers("178"));// x
		testSample.add(new Numbers("819"));// o
		testSample.add(new Numbers("829"));// x
		testSample.add(new Numbers("823"));// x
		testSample.add(new Numbers("832"));// x
		testSample.add(new Numbers("831"));// x
		testSample.add(new Numbers("836"));// o

		reducer.reduce(testSample, new Numbers("123"));
		assertThat(testSample.size(), equalTo(2));
	}

	@Test
	public void testTwoBallSetReduceer() {
		SetReducer<Numbers> reducer = new TwoBallSetReducer();
		Set<Numbers> testSample = Sets.newConcurrentHashSet();
		testSample.add(new Numbers("123"));// x
		testSample.add(new Numbers("103"));// x
		testSample.add(new Numbers("120"));// x
		testSample.add(new Numbers("023"));// x
		testSample.add(new Numbers("230"));// o
		testSample.add(new Numbers("312"));// x
		testSample.add(new Numbers("231"));// x
		testSample.add(new Numbers("310"));// o
		testSample.add(new Numbers("456"));// x
		testSample.add(new Numbers("413"));// x
		testSample.add(new Numbers("431"));// o
		testSample.add(new Numbers("319"));// o

		reducer.reduce(testSample, new Numbers("123"));
		assertThat(testSample.size(), equalTo(4));
	}

	@Test
	public void testThreeBallSetReducer() {
		SetReducer<Numbers> reducer = new ThreeBallSetReducer();
		Set<Numbers> testSample = Sets.newConcurrentHashSet();
		testSample.add(new Numbers("123"));// x
		testSample.add(new Numbers("132"));// x
		testSample.add(new Numbers("321"));// x
		testSample.add(new Numbers("312"));// o
		testSample.add(new Numbers("321"));// x
		testSample.add(new Numbers("231"));// o
		testSample.add(new Numbers("213"));// x
		testSample.add(new Numbers("456"));// x
		testSample.add(new Numbers("324"));// x

		reducer.reduce(testSample, new Numbers("123"));
		assertThat(testSample.size(), equalTo(2));
	}

	@Test
	public void testOneStrikeSetReducer() {
		SetReducer<Numbers> reducer = new OneStrikeSetReducer();
		Set<Numbers> testSample = Sets.newConcurrentHashSet();
		testSample.add(new Numbers("123"));// x
		testSample.add(new Numbers("324"));// x
		testSample.add(new Numbers("120"));// x
		testSample.add(new Numbers("321"));// x
		testSample.add(new Numbers("103"));// x
		testSample.add(new Numbers("983"));// o
		testSample.add(new Numbers("123"));// x
		testSample.add(new Numbers("320"));// x
		testSample.add(new Numbers("321"));// x
		testSample.add(new Numbers("145"));// o
		testSample.add(new Numbers("425"));// o

		reducer.reduce(testSample, new Numbers("123"));
		assertThat(testSample.size(), equalTo(3));
	}

	@Test
	public void testTwoStrikeSetReducer() {
		SetReducer<Numbers> reducer = new TwoStrikeSetReducer();
		Set<Numbers> testSample = Sets.newConcurrentHashSet();
		testSample.add(new Numbers("123"));// x
		testSample.add(new Numbers("120"));// o
		testSample.add(new Numbers("023"));// o
		testSample.add(new Numbers("103"));// o
		testSample.add(new Numbers("132"));// x
		testSample.add(new Numbers("231"));// x
		testSample.add(new Numbers("456"));// x

		reducer.reduce(testSample, new Numbers("123"));
		assertThat(testSample.size(), equalTo(3));
	}
}
