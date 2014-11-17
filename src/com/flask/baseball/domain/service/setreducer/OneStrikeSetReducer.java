package com.flask.baseball.domain.service.setreducer;

import java.util.Iterator;

import com.flask.baseball.domain.model.Numbers;

public class OneStrikeSetReducer extends SetReducerAdapter {

	protected void reduce(Iterator<Numbers> iterator, Numbers numbers) {
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
}
