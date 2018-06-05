package com.abraham.geng.inter;

import java.awt.Image;

import com.abraham.geng.GameEngine;

public class Animation {

	public Animation(Frame[] states) {
		this.states = states;
	}
	
	public synchronized Image getCurrentFrame() {
		if ((GameEngine.currentTime() - timeStarted) > states[index].getDelay()) {
			timeStarted = GameEngine.currentTime();
			currentFrame = states[index++].getImage();
			if (index >= states.length) {
				index = 0;
			}
		}
		return currentFrame;
	}
	
	public void start() {
		timeStarted = GameEngine.currentTime();
	}
	
	private long timeStarted;
	private Image currentFrame;
	private Frame[] states;
	private int index;
	
	public static class Frame {
		public Frame(int delay, Image image) {
			this.delay = delay;
			this.image = image;
		}
		
		public int getDelay() {
			return delay;
		}
		
		public Image getImage() {
			return image;
		}
		
		private int delay;
		private Image image;
	}
}
