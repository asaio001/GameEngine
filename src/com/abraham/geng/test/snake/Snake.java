package com.abraham.geng.test.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

import com.abraham.geng.inter.Interface;

public class Snake extends Interface {
	
	public Snake(Point tile) {
		Grid grid = SnakeEngine.getEngine().getGrid();
		for (int i = 0; i < DEFAULT_SIZE; i++) {
			appendPart(grid.getNextTile(tile, DEFAULT_DIRECTION.opposite(), i), DEFAULT_DIRECTION);
		}
	}
	
	public void appendPart(Point tile, Node.Direction direction) {
		snakeComposite.add(new Node(tile, direction));
	}
	
	public void load() { }
	public void dispose() { }
	
	public Node.Direction direction() {
		return direction;
	}
	
	public Node head() {
		return snakeComposite.peek();
	}
	
	public Node tail() {
		return snakeComposite.peekLast();
	}
	
	public void move() {
		/* Caches previous node's direction and sets it for the next one */
		Node.Direction dir = direction;
		for (Node node : snakeComposite) {
			dir = node.move(dir);
		}
		
		lastDirectionMoved = direction;
	}
	
	public void render(Graphics g, int timeElapsed) {
		Grid grid = SnakeEngine.getEngine().getGrid();
		g.setColor(Color.GREEN);
		for (Node node : snakeComposite) {
			Point screenPx = grid.getScreenPxAt(node.tile());
			g.fillOval(screenPx.x, screenPx.y, Node.WIDTH, Node.HEIGHT);
		}
	}
	
	public void setDirection(Node.Direction direction) {
		if (lastDirectionMoved.opposite().equals(direction))
			return;
			
		this.direction = direction;
	}
	
	private static final int DEFAULT_SIZE = 3;
	public static final Node.Direction DEFAULT_DIRECTION = Node.Direction.DOWN;	
	private Node.Direction direction = DEFAULT_DIRECTION;
	private Node.Direction lastDirectionMoved = DEFAULT_DIRECTION;	
	private LinkedList<Node> snakeComposite = new LinkedList<Node>();
}