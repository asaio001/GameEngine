package com.abraham.geng.test.snake;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.abraham.geng.GameEngine;

public class SnakeEngine extends GameEngine {
	
	public static SnakeEngine getEngine() {
		if (engine != null)
			return engine;
		
		return (engine = new SnakeEngine());
	}

	public Graphics getGraphics() {
		return gameCanvas.getGraphics();
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public Component getMainHandle() {
		return (Component) gameCanvas;
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	public void initialize() {
		gameFrame = new JFrame("SnakeEngine");
		gameCanvas = new JPanel();
		grid = new Grid(new Dimension(FRAME_WIDTH, FRAME_HEIGHT), new Dimension(TILE_WIDTH, TILE_HEIGHT));
		snake = new Snake(new Point(10, 10));
		apple = new Apple();
		playing = false;
		
		gameFrame.setResizable(false);
		gameFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameCanvas.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		gameFrame.add(gameCanvas);
		gameFrame.setVisible(true);
		
		super.setTargetGameRate(DEFAULT_GAME_RATE);
		super.addInterface(new GameInterface(0, 0, FRAME_WIDTH, FRAME_HEIGHT));
		super.addInterface(snake);
		super.addInterface(apple);
		super.run();
	}
	
	public void processGameLogic(int timeElapsed) {
		if (!isPlaying())
			return;
		
		snake.move();
		
		if (apple.equals(snake.head().tile())) {
			apple.randomize();
			
			/* Append a new node to the snake, one tile before the tail (qua direction) */
			Node tail = snake.tail();
			Point trailTile = grid.getNextTile(tail.tile(), tail.direction().opposite());
			snake.appendPart(trailTile, snake.tail().direction());
			
			speedUp();
		}
	}
							 
	public void speedUp() {
		super.setTargetGameRate(super.getTargetGameRate() + 1);
	}
	
	public void startPlaying() {
		playing = true;
	}
	
	public void stopPlaying() {
		playing = false;
	}
	
	public static void main(String[] args) {
		SnakeEngine.getEngine().initialize();
	}
	
	public static final int FRAME_WIDTH = 500, FRAME_HEIGHT = 500;
	public static final int TILE_WIDTH = 16, TILE_HEIGHT = 16;
	public static final int DEFAULT_GAME_RATE = 10;
	
	private static SnakeEngine engine;
	
	private JPanel gameCanvas;
	private JFrame gameFrame;
	
	private Grid grid;
	private Snake snake;
	private Apple apple;
		
	private boolean playing;
}