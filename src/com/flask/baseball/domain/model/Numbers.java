package com.flask.baseball.domain.model;

import java.util.Arrays;

public class Numbers {
	private int[] series;

	public Numbers(int i, int j, int k) {
		series = new int[3];
		series[0] = i;
		series[1] = j;
		series[2] = k;
	}

	public Numbers(int[] picked) {
		series = new int[3];
		System.arraycopy(picked, 0, series, 0, 3);
	}

	public Numbers(String str) {
		series = new int[3];
		for (int i = 0; i < 3; i++)
			series[i] = str.charAt(i) - '0';
	}

	public int at(int index) {
		return series[index];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(series);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Numbers other = (Numbers) obj;
		for (int i = 0; i < series.length; i++) {
			if (this.at(i) != other.at(i))
				return false;
		}
		return true;
	}

	public int length() {
		return series.length;
	}

	public boolean contains(int i) {
		for (int j = 0; j < series.length; j++)
			if (series[j] == i)
				return true;
		return false;
	}

	public boolean containsButNotSamePosition(int i, int position) {
		for (int j = 0; j < series.length; j++)
			if (position != j && series[j] == i)
				return true;
		return false;
	}

	@Override
	public String toString() {
		return "[" + series[0] + ", " + series[1] + ", " + series[2] + "]";
	}

}
