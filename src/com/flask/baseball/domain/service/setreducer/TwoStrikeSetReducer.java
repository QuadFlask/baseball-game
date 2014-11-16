package com.flask.baseball.domain.service.setreducer;

import java.util.Iterator;
import java.util.Set;

import com.flask.baseball.domain.model.Numbers;

public class TwoStrikeSetReducer implements SetReducer<Numbers> {

	@Override
	public Set<Numbers> reduce(Set<Numbers> set, Numbers numbers) {
		set.remove(numbers);
		Iterator<Numbers> iterator = set.iterator();
		while (iterator.hasNext()) {
			Numbers next = iterator.next();

			boolean a = next.at(0) == numbers.at(0) && next.at(1) == numbers.at(1);
			boolean a1 = next.at(0) == numbers.at(0) && next.at(2) == numbers.at(2);
			boolean a2 = next.at(1) == numbers.at(1) && next.at(2) == numbers.at(2);
			a = a || a1 || a2;

			if (!a)
				iterator.remove();
		}
		return set;
	}

}
