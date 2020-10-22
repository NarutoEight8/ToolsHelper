package com.naruto.dispatcher;

public interface OEventObject {
	public abstract void receiveEvent(String eventName, Object paramObj);
}
