package com.flask.baseball.domain.service.setreducer;

import java.util.Iterator;
import java.util.Set;

import com.flask.baseball.domain.model.Numbers;

public class ExcludeSetReducer implements SetReducer<Numbers> {
	@Override
	public Set<Numbers> reduce(Set<Numbers> set, Numbers numbers) {
		set.remove(numbers);
		Iterator<Numbers> iterator = set.iterator();
		while (iterator.hasNext()) {
			Numbers next = iterator.next();
			for (int i = 0; i < 3; i++) {
				if (next.contains(numbers.at(i))) {
					iterator.remove();
					break;
				}
			}
		}
		return set;
	}
}
