package com.flask.baseball.domain.service.setreducer;

import java.util.Iterator;
import java.util.Set;

import com.flask.baseball.domain.model.Numbers;

public class TwoBallSetReducer implements SetReducer<Numbers> {

	@Override
	public Set<Numbers> reduce(Set<Numbers> set, Numbers numbers) {
		set.remove(numbers);
		Iterator<Numbers> iterator = set.iterator();
		while (iterator.hasNext()) {
			Numbers next = iterator.next();

			boolean a = next.containsButNotSamePosition(numbers.at(0), 0)
					&& next.containsButNotSamePosition(numbers.at(1), 1);
			boolean a1 = next.containsButNotSamePosition(numbers.at(0), 0)
					&& next.containsButNotSamePosition(numbers.at(2), 2);
			boolean a2 = next.containsButNotSamePosition(numbers.at(1), 1)
					&& next.containsButNotSamePosition(numbers.at(2), 2);

			a = a || a1 || a2;
			if (!a) {
				iterator.remove();
			} else {
				boolean isContainsEvery = true;
				for (int i = 0; i < 3; i++)
					isContainsEvery &= next.contains(numbers.at(i));
				if (isContainsEvery)
					iterator.remove();
			}
		}
		return set;
	}

}
