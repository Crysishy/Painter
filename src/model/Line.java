package model;

/**
 * Class: Line.java
 * Purpose: This class extends PaintObject() class. It is responsible for
 *			drawing this paint type: Line.
 * Programmer: YANG HONG
 * 			   Haozhe Xu
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Line extends PaintObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9082971076005623150L;

	public Line(Color color, Point point1, Point point2) {
		super(color, point1, point2);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(this.getColor());
		
		Point p1 = this.getPoint(1);
		Point p2 = this.getPoint(2);
		
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
	}

}
