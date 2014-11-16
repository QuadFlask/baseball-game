package com.flask.baseball.domain.service.setreducer;

import java.util.Iterator;
import java.util.Set;

import com.flask.baseball.domain.model.Numbers;

public class OneBallSetReducer implements SetReducer<Numbers> {

	@Override
	public Set<Numbers> reduce(Set<Numbers> set, Numbers numbers) {
		set.remove(numbers);
		Iterator<Numbers> iterator = set.iterator();
		boolean contains = false;
		while (iterator.hasNext()) {
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
		return set;
	}

}
