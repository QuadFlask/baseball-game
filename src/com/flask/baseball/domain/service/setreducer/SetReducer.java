package com.flask.baseball.domain.service.setreducer;

import java.util.Set;

public interface SetReducer<T> {
	public Set<T> reduce(Set<T> set, T t);
}
