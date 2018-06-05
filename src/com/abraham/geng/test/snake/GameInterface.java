package com.abraham.geng.test.snake;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import com.abraham.geng.inter.Interface;
import com.abraham.geng.event.EventHandler;

public class GameInterface extends Interface implements EventHandler {
	
	public GameInterface(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		SnakeEngine.getEngine().registerEventHandler(this);
	}
	
	public long[] getEventMasks() {
		return new long[] { KeyEvent.KEY_PRESSED };
	}
	
	public Interface getParent() {
		return this;
	}
	
	public void handleEvent(InputEvent event) {
		SnakeEngine engine = SnakeEngine.getEngine();
		Snake snake = engine.getSnake();
		KeyEvent keyEvent = (KeyEvent) event;
		Node.Direction newDirection;
		
		if (!engine.isPlaying()) {
			if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
				engine.startPlaying();
			
			return;
		}
		
		switch(keyEvent.getKeyCode()) {
			case KeyEvent.VK_UP:
				newDirection = Node.Direction.UP;
				break;
			case KeyEvent.VK_DOWN:
				newDirection = Node.Direction.DOWN;
				break;
			case KeyEvent.VK_RIGHT:
				newDirection = Node.Direction.RIGHT;
				break;
			case KeyEvent.VK_LEFT:
				newDirection = Node.Direction.LEFT;
				break;
			default:
				return;
		}
		
		snake.setDirection(newDirection);
	}
	
	public void dispose() {
	}
	
	public void load() {
	}
	
	public void render(Graphics g, int timeElapsed) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
		
		if (!SnakeEngine.getEngine().isPlaying()) {
			g.setColor(Color.RED);
			g.drawString("Press Enter to begin..", 180, 250);
		}
	}
}