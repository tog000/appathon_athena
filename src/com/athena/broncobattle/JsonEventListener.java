package com.athena.broncobattle;

public interface JsonEventListener<T> {
	public void onReadFinished(T object);
}
