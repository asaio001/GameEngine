package com.abraham.geng.test.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import com.abraham.geng.inter.Interface;

public class Apple extends Interface {
	
	public Apple() {
		randomize();
	}

	public void dispose() { }
	public void load() { }
	
	public boolean equals(Point tile) {
		return this.tile.x == tile.x && this.tile.y == tile.y;
	}
	
	public void render(Graphics g, int timeElapsed) {
		Grid grid = SnakeEngine.getEngine().getGrid();
		Point screenPx = grid.getScreenPxAt(tile);
		
		g.setColor(Color.RED);
		g.fillOval(screenPx.x, screenPx.y, WIDTH, HEIGHT);
	}
	
	public Point randomize() {
		Grid grid = SnakeEngine.getEngine().getGrid();
		Dimension gridDim = grid.getDimensions();
		
		int offset = 2; // Exclude tiles that are non/partially visible
		int width = gridDim.width - offset;
		int height = gridDim.height - offset;
		
		int random = (int) (Math.random() * (width * height)) + offset;
		int x = (int) Math.floor(random / height);
		int y = random - (x * height);
		
		return (tile = new Point(x, y));
	}
		
	public Point tile() {
		return tile;
	}
	
	public static final int WIDTH = SnakeEngine.TILE_WIDTH;
	public static final int HEIGHT = SnakeEngine.TILE_HEIGHT;
	private Point tile;
}