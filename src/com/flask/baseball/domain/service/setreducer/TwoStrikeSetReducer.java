package com.flask.baseball.domain.service.setreducer;

import java.util.Iterator;

import com.flask.baseball.domain.model.Numbers;

public class TwoStrikeSetReducer extends SetReducerAdapter {

	protected void reduce(Iterator<Numbers> iterator, Numbers numbers) {
		Numbers next = iterator.next();

		boolean a = next.at(0) == numbers.at(0) && next.at(1) == numbers.at(1);
		boolean a1 = next.at(0) == numbers.at(0) && next.at(2) == numbers.at(2);
		boolean a2 = next.at(1) == numbers.at(1) && next.at(2) == numbers.at(2);
		a = a || a1 || a2;

		if (!a)
			iterator.remove();
	}

}
