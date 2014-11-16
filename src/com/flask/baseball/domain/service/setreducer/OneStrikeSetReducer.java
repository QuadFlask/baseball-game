package com.flask.baseball.domain.service.setreducer;

import java.util.Iterator;
import java.util.Set;

import com.flask.baseball.domain.model.Numbers;

public class OneStrikeSetReducer implements SetReducer<Numbers> {

	@Override
	public Set<Numbers> reduce(Set<Numbers> set, Numbers numbers) {
		set.remove(numbers);
		Iterator<Numbers> iterator = set.iterator();
		while (iterator.hasNext()) {
			Numbers next = iterator.next();
			int count = 0;
			for (int i = 0; i < 3; i++)
				count += (numbers.at(i) == next.at(i)) ? 1 : 0;
			if (count != 1)
				iterator.remove();
			else {
				count = 0;
				for (int i = 0; i < 3; i++)
					count += numbers.contains(next.at(i)) ? 1 : 0;
				if (count > 1)
					iterator.remove();
			}
		}
		return set;
	}
}
