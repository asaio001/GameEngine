package com.abraham.geng.test.snake;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Point;

public class Grid {
	
	public Grid(Dimension pxDim, Dimension tileDim) {
		this.pxDim = pxDim;
		this.tileDim = tileDim;
		setup();
	}
	
	public Dimension getDimensions() {
		return new Dimension(tilesX, tilesY);
	}
	
	public Point getNextTile(Point tile, Node.Direction direction) {
		return getNextTile(tile, direction, 1);
	}
	
	public Point getNextTile(Point tile, Node.Direction direction, int offset) {
		int x = tile.x, y = tile.y;
		switch(direction) {
			case UP:
				y -= offset;
				break;
			case DOWN:
				y += offset;
				break;
			case RIGHT:
				x += offset;
				break;
			case LEFT:
				x -= offset;
				break;
		}
		return new Point(x, y);
	}
	
	public Point getScreenPxAt(Point tile) {
		return new Point(tile.x * tileDim.width, tile.y * tileDim.height);
	}
	
	public Point getTileAt(Point px) {
		int x = (int) Math.floor(px.x / tileDim.width);
		int y = (int) Math.floor(px.y / tileDim.height);
		return tiles[x][y];
	}
	
	public Dimension getTileDim() {
		return tileDim;
	}
	
	public void setup() {
		tilesX = (int) Math.ceil(pxDim.width / tileDim.width);
		tilesY = (int) Math.ceil(pxDim.height / tileDim.height);
		tiles = new Point[tilesX][tilesY];
		
		for (int x = 0; x < tilesX; x++) {
			for (int y = 0; y < tilesY; y++) {
				tiles[x][y] = new Point(x * tileDim.width, y * tileDim.height); 
			}
		}
	}
	
	public void render(Graphics g) {
		for (int x = 0; x < tilesX; x++) {
			for (int y = 0; y < tilesY; y++) {
				g.drawRect(tiles[x][y].x, tiles[x][y].y, tileDim.width - 1, tileDim.height - 1);
			}
		}
	}
	
	private Dimension pxDim;
	private Dimension tileDim;
	private Point[][] tiles;
	private int tilesX, tilesY;
}