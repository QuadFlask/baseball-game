package com.flask.baseball.domain.service.setreducer;

import java.util.Iterator;

import com.flask.baseball.domain.model.Numbers;

public class OneBallSetReducer extends SetReducerAdapter {

	protected void reduce(Iterator<Numbers> iterator, Numbers numbers) {
		boolean contains;
		Numbers next = iterator.next();
		contains = false;
		for (int i = 0; i < 3; i++) {
			if (next.containsButNotSamePosition(numbers.at(i), i)) {
				contains = true;
				for (int j = 0; j < 3; j++) {
					if (i != j) {
						if (next.contains(numbers.at(j))) {
							contains = false;
							break;
						}
					}
				}
				break;
			}
		}
		if (!contains)
			iterator.remove();
	}

}
