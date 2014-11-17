package com.flask.baseball.domain.service.setreducer;

import java.util.Iterator;

import com.flask.baseball.domain.model.Numbers;

public class OneStrikeTwoBallSetReducer extends SetReducerAdapter {

	protected void reduce(Iterator<Numbers> iterator, Numbers numbers) {
		Numbers next = iterator.next();
		for (int i = 0; i < 3; i++)
			if (!next.contains(numbers.at(i))) {
				iterator.remove();
				break;
			}
	}

}
