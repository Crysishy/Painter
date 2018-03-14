package model;

/**
 * Class: Rectangle.java
 * Purpose: This class is a super class for the other four subclasses. It is responsible for:
 *			1. storing color and two coordinates;
 *			2. return Color type;
 *			3. return Point type;
 *			4. having subclasses implementing abstract method draw(Graphics g).
 * Programmer: YANG HONG
 * 			   Haozhe Xu
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

public abstract class PaintObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5812217981301995985L;
	private Color color;
	private Point point1;
	private Point point2;
	
	public PaintObject(Color color, Point point1, Point point2) {
		this.color = color;
		this.point1 = point1;
		this.point2 = point2;
	}
	
	public Color getColor(){
		return color;
	}
	
	public Point getPoint(int ref) {
		if (ref == 1)
			return point1;
		else
			return point2;
	}
	
	public void setPoint2(Point pt){
		point2 = pt;
	}
	
	abstract public void draw(Graphics g);

}
