package com.abraham.geng.event;

import java.awt.event.InputEvent;

import com.abraham.geng.inter.Interface;

public interface EventHandler {
	
	public long[] getEventMasks();
	public Interface getParent();
	public void handleEvent(InputEvent e);
}
