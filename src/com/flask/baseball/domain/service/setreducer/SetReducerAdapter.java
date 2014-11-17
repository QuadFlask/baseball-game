package com.flask.baseball.domain.service.setreducer;

import java.util.Iterator;
import java.util.Set;

import com.flask.baseball.domain.model.Numbers;

public abstract class SetReducerAdapter implements SetReducer<Numbers> {

	@Override
	public Set<Numbers> reduce(Set<Numbers> set, Numbers numbers) {
		set.remove(numbers);
		Iterator<Numbers> iterator = set.iterator();
		while (iterator.hasNext()) {
			reduce(iterator, numbers);
		}
		return set;
	}

	protected abstract void reduce(Iterator<Numbers> iterator, Numbers numbers);
}
