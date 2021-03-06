package com.humaorie.wr.dict;

public class FailingDict implements Dict {

	private final RuntimeException exception;

	public FailingDict(RuntimeException exception) {
		this.exception = exception;
	}

	@Override
	public DictEntry lookup(String dict, String word) {
		throw this.exception;
	}
}
