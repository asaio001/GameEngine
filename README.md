# Mini Java game engine

Lightweight 2D game engine written in Java with UI support, scripting, configuration loading, event handling, and game logic processing. Built on top of Java's AWT library.

The main game loop consists of (1) dispatching & handling events, (2) processing the game logic, and (3) rendering graphics. 

## UI support
All user interfaces are drawn onto a back buffer and then rendered onto the main canvas. Since rendering is built into the game loop, UI elements are rendered multiple times a second -- depending on the specific game rate (game loops a second) aimed for. The abstract Interface class that all components must inherit from defines its render method as:

```
public abstract void render(Graphics g, int timeElapsed);
```

Which passes a reference to the back buffer's graphics handle along with a measure of how much time elpased since last call to render. The latter measure is useful for animated graphics, which can sync up animations with the FPS rate of the engine.

## Scripting
Currently supports Javascript scripting with [Mozilla's Rhino](https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino).

## Event handling
In order for events to be processed in sync with all other aspects of the game loop, we have any interface that is awaiting events register their EventHandler with the GameEngine, which specifies the (1) event masks the interface is listening for and (2) the callback upon event(s).
Example (taken from the Snake game built with this engine. See the "Sample usage" section below):
```
	public long[] getEventMasks() {
		return new long[] { KeyEvent.KEY_PRESSED };
	}
	
	public Interface getParent() {
		return this;
	}
	
	public void handleEvent(InputEvent event) {
		Engine engine = Engine.getEngine();
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
```

## Game logic
Game logic processing happens after event handling and before rendering. This means that any game logic that should ne at par with rendering should happen here. The class that inherits from GameEngine has to implement:
```
public void processGameLogic(int timeElapsed);
```

As with the render function, _timeElapsed_ passes the amount of time elapsed since the run of the last game loop. This way any game logic that has to be in sync with rendering is facilitated as they are built on the same timing system.

## Sample usage
Includes an (incomplete) Snake game as a sample to the engine in the package _com.abraham.geng.test.snake_. To run it, run:
```
java com.abraham.geng.test.snake.SnakeEngine
```
