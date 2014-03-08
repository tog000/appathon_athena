package com.athena.broncobattle;

public interface JsonEventListener<T> {	
	public void onJsonFinished(T object, String eventType);
}
