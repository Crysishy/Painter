package model;

/**
 * Class: Oval.java
 * Purpose: This class extends PaintObject() class. It is responsible for
 *			drawing this paint type: Oval.
 * Programmer: YANG HONG
 * 			   Haozhe Xu
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Oval extends PaintObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8460448092103932159L;

	public Oval(Color color, Point point1, Point point2) {
		super(color, point1, point2);
	}

	@Override
	public void draw(Graphics g) {
		//Graphics2D g2 = (Graphics2D) g;
		g.setColor(this.getColor());

		Point p1 = this.getPoint(1);
		Point p2 = this.getPoint(2);
		
		//g2.drawOval(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.abs(p1.x-p2.x), Math.abs(p1.y-p2.y));
		g.fillOval(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.abs(p1.x-p2.x), Math.abs(p1.y-p2.y));
	}

}
