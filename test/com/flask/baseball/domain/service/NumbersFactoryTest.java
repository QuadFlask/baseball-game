package com.flask.baseball.domain.service;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import org.junit.Test;

import com.flask.baseball.domain.model.Numbers;

public class NumbersFactoryTest {

	@Test
	public void testCreateNumbers() {
		Numbers numbers = NumbersFactory.createRandom();
		assertThat(numbers, notNullValue());
		Numbers numbers2 = NumbersFactory.createRandom();
		assertThat(numbers, not(numbers2));
	}
}
