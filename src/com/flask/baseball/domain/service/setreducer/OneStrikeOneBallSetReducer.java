package com.flask.baseball.domain.service.setreducer;

import java.util.Iterator;

import com.flask.baseball.domain.model.Numbers;

public class OneStrikeOneBallSetReducer extends SetReducerAdapter {

	protected void reduce(Iterator<Numbers> iterator, Numbers numbers) {
		Numbers next = iterator.next();
		int count = 0;
		for (int i = 0; i < 3; i++)
			count += next.at(i) == numbers.at(i) ? 1 : 0;
		if (count != 1)
			iterator.remove();
		else {
			count = 0;
			for (int i = 0; i < 3; i++)
				count += next.containsButNotSamePosition(numbers.at(i), i) ? 1 : 0;
			if (count != 1)
				iterator.remove();
		}
	}
}
