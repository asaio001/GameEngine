package com.abraham.geng.inter;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class Interface {
	
	public Rectangle getDimensions() {
		return new Rectangle(x, y, width, height);
	}

	public abstract void dispose();
	public abstract void load();
	
	/* The 'timeElapsed' parameter is particularly useful when a certain 'Interface' subclass
	 * wants to render animations. */
	public abstract void render(Graphics g, int timeElapsed);
	
	protected int x, y, height, width;
	protected Image img = null;
}