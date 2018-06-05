package com.abraham.geng.test.snake;

import java.awt.Point;

public class Node {
	
	public Node(Point tile, Direction direction) {
		this.tile = tile;
		this.direction = direction;
	}
	
	public Direction direction() {
		return direction;
	}
	
	public Direction move(Direction newDir) {
		Direction oldDir = direction;
		tile = SnakeEngine.getEngine().getGrid().getNextTile(tile, direction);
		direction = newDir;
		return oldDir;
	}
	
	public Point tile() {
		return tile;
	}
	
	public static enum Direction { 
		UP, DOWN, RIGHT, LEFT;
		
		Direction opposite() {
			switch(this) {
				case UP: return DOWN;
				case DOWN: return UP;
				case RIGHT: return LEFT;
				case LEFT: return RIGHT;
			}
			return null;
		}
	};
	
	public static final int WIDTH = SnakeEngine.TILE_WIDTH;
	public static final int HEIGHT = SnakeEngine.TILE_HEIGHT;
	
	private Direction direction;
	private Point tile;
}