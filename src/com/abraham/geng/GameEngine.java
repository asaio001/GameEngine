package com.abraham.geng;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import com.abraham.geng.event.EventHandler;
import com.abraham.geng.event.EventListener;
import com.abraham.geng.inter.Interface;

/* The core of the entire engine. Responsible for rendering, dispatching events to their respective
 * handlers, and game updating. This class should be inherited by the implementors of this engine.*/

public abstract class GameEngine implements Runnable {
	
	public GameEngine() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				atExit();
			}
		}));
	}
	
	/* Get the graphics handle to draw on. */
	public abstract Graphics getGraphics();
	
	/* This is the component on which the engine will be listening for events. It should also be
	 * the component from which the graphics returned by getGraphics render upon. */
	public abstract Component getMainHandle();
	
	/* Perform any game logic that should be at par with rendering.*/
	public abstract void processGameLogic(int timeElapsed);
	
	public void addEvent(InputEvent event) {
		synchronized(events) {
			events.add(event);
		}
	}
	
	public void addInterface(Interface i) {
		synchronized(interfaces) {
			interfaces.add(i);
			i.load();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void atExit() {
		for (Interface i : (Vector<Interface>) interfaces.clone()) {
			i.dispose();
		}
	}
	
	/* Better to use than System.currentTimeMillis for timing due to granularity issues. */
	public static long currentTime() {
		return (System.nanoTime() / 1000000);
	}
	
	private void createBackBuffer() {
		backBuffer = getMainHandle().createImage(getMainHandle().getWidth(), getMainHandle().getHeight());
	}
	
	private void createEventListener() {
		while(!getMainHandle().isVisible());
		getMainHandle().requestFocusInWindow();
		
		new EventListener(this).registerListeners(getMainHandle());
	}
	
	protected void dispatchEvents() {
		EventHandler[] handlers;
		synchronized(eventHandlers) {
			handlers = eventHandlers.toArray(new EventHandler[] { });
		}
		
		synchronized(events) {
			for (InputEvent event : events) {
				for (EventHandler handler : handlers) {
					/* TODO: handler.includes(mask)*/
					for (long mask : handler.getEventMasks()) {
						if (event.getID() == mask) {
							if (event instanceof MouseEvent) {
								MouseEvent mouseEvent = (MouseEvent) event;
								if (!handler.getParent().getDimensions().intersects(new Rectangle(mouseEvent.getX(), mouseEvent.getY(), 1, 1))) {
									continue;
								}
							}
							handler.handleEvent(event);
						}
					}
				}
			}
			events.clear();
		}
	}
	
	public int getFPS() {
		return fps;
	}
	
	public int getTargetGameRate() {
		return targetGameRate;
	}
	
	public boolean isRunning() {
		return run;
	}
	
	protected void processRender(Graphics gfx, int timeElapsed) {
		synchronized(interfaces) {
			/* Render to the back buffer*/
			Graphics g = backBuffer.getGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, backBuffer.getWidth(null), backBuffer.getHeight(null));
			getMainHandle().paint(g);
			for (Interface i : interfaces) {
				i.render(g, timeElapsed);
			}
			
			/* Render to the main graphics handle */
			gfx.drawImage(backBuffer, 0, 0, null);
		}
	}
	
	public void registerEventHandler(EventHandler eventHandler) {
		synchronized(eventHandlers) {
			eventHandlers.add(eventHandler);
		}
	}
	
	public void removeEventHandler(EventHandler eventHandler) {
		synchronized(eventHandlers) {
			eventHandlers.remove(eventHandler);
		}
	}
	
	public void removeInterface(Interface i) {
		i.dispose();
		synchronized(interfaces) {
			interfaces.remove(i);
		}
	}

	/* This function contains the "game loop". Responsible for all core engine handling.
	 * It will block the underlying thread until the application exits. */
	public void run() {
		setupGameRate();
		createBackBuffer();
		createEventListener();
		
		run = true;
		
		while(run) {
			timeStarted = currentTime();
			iterTime = timePerIter;
			frames = 0;
			fps = 0;
			
			while((((currentTime = currentTime()) - timeStarted) < 1000) &&  frames < targetGameRate) {
				int timeElapsed = (int) (currentTime() - currentTime);
				
				dispatchEvents();
				processGameLogic(timeElapsed);
				processRender(getGraphics(), timeElapsed);
				
				if (iterTime > timeElapsed) {
					try {
						Thread.sleep(iterTime - timeElapsed);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
				iterTime = timePerIter - timeElapsed; 
				if (timeElapsed > iterTime) {
					if (iterTime < 0) {
						frames += (iterTime * -1) / timePerIter;
						iterTime = (iterTime * -1) % timePerIter;
					}
				}
				fps++;
				frames++;
			}
			lastLoop = timeStarted;
		}
	}
	
	private void setupGameRate() {
		timePerIter = (int) Math.floor(1000 / targetGameRate);
	}
	
	public final void setTargetGameRate(int targetGameRate) {
		this.targetGameRate = targetGameRate;
		setupGameRate();
	}
	
	public final void stopRunning() {
		run = false;
	}
	
	private int frames, fps, timePerIter, iterTime;
	private long lastLoop, timeStarted, currentTime;
	private int targetGameRate = 30; // default
	private boolean run = false;
	
	private Image backBuffer;
	private Vector<Interface> interfaces = new Vector<Interface>();
	private Vector<EventHandler> eventHandlers = new Vector<EventHandler>();
	private Vector<InputEvent> events = new Vector<InputEvent>();
}
