package com.abraham.geng.event;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.abraham.geng.GameEngine;

public class EventListener implements KeyListener, MouseListener, 
											MouseMotionListener, MouseWheelListener {

	public EventListener(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	public void keyPressed(KeyEvent e) {
		gameEngine.addEvent(e);
	}

	public void keyReleased(KeyEvent e) {
		gameEngine.addEvent(e);
	}

	public void keyTyped(KeyEvent e) {
		gameEngine.addEvent(e);
	}

	public void mouseClicked(MouseEvent e) {
		gameEngine.addEvent(e);
	}

	public void mouseEntered(MouseEvent e) {
		gameEngine.addEvent(e);
	}

	public void mouseExited(MouseEvent e) {
		gameEngine.addEvent(e);
	}

	public void mousePressed(MouseEvent e) {
		gameEngine.addEvent(e);
	}

	public void mouseReleased(MouseEvent e) {
		gameEngine.addEvent(e);
	}

	public void mouseDragged(MouseEvent e) {
		gameEngine.addEvent(e);
	}

	public void mouseMoved(MouseEvent e) {
		gameEngine.addEvent(e);
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		gameEngine.addEvent(e);
	}
	
	public void registerListeners(Component component) {
		component.addKeyListener(this);
		component.addMouseListener(this);
		component.addMouseMotionListener(this);
		component.addMouseWheelListener(this);
	}
	
	private GameEngine gameEngine;
}
