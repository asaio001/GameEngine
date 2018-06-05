package com.abraham.geng.inter;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;

public class ProportionLayout implements LayoutManager2 {
	
	public static class Proportion {
		public Proportion(int x, int y, int width, int height, Resize resize) {
			this.x = x;
			this.y = y;	
			this.width = width;
			this.height = height;
			this.resize = resize;
		}
		
		public enum Resize { NONE, BOTH, WIDTH, HEIGHT };
		public final int x, y;
		public final int width, height;
		public final Resize resize;
	}

	public void addLayoutComponent(Component c, Object constraints) {
		if (!(constraints instanceof Proportion) && constraints != null) {
			throw new IllegalArgumentException("An instance of the Proportion class must be passed as a constraint.");
		}
		components.put(c, (Proportion) constraints);
	}

	public float getLayoutAlignmentX(Container c) {
		return 0;
	}

	public float getLayoutAlignmentY(Container c) {
		return 0;
	}

	public void invalidateLayout(Container c) {	
	}

	public Dimension maximumLayoutSize(Container c) {
		return c.getMaximumSize();
	}

	public void addLayoutComponent(String s, Component c) {	
		addLayoutComponent(c, null);
	}

	public void layoutContainer(Container c) {
		Insets insets = c.getInsets();
		for (Component comp : components.keySet()) {
			Proportion perc = components.get(comp);	
			if (perc == null) {
				continue;
			}
			
			int x, y, w = comp.getWidth(), h = comp.getHeight();
			
			x = (c.getWidth() * perc.x) / 100 + insets.left;
			y = (c.getHeight() * perc.y) / 100 + insets.top;
			
			if (perc.resize == Proportion.Resize.WIDTH || perc.resize == Proportion.Resize.BOTH) {
				w = (c.getWidth() * perc.width) / 100;
			}
			if (perc.resize == Proportion.Resize.HEIGHT || perc.resize == Proportion.Resize.BOTH) {
				h = (c.getHeight() * perc.height) / 100;
			}
			
			comp.setBounds(x, y, w, h);
		}
	}

	public Dimension minimumLayoutSize(Container c) {
		return c.getMinimumSize();
	}

	public Dimension preferredLayoutSize(Container c) {
		return c.getSize();
	}

	public void removeLayoutComponent(Component c) {	
		components.remove(c);
	}
	
	private HashMap<Component, Proportion> components = new HashMap<Component, Proportion>();
}
