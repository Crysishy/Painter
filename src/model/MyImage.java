package model;

/**
 * Class: MyImage.java
 * Purpose: This class extends PaintObject() class. It is responsible for
 *			drawing this paint type: Image.
 * Programmer: YANG HONG
 * 			   Haozhe Xu
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MyImage extends PaintObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6694970820009347890L;

	public MyImage(Color color, Point point1, Point point2) {
		super(color, point1, point2);
	}

	@Override
	public void draw(Graphics g) {
		BufferedImage myImage = null;
		try {
			myImage = ImageIO.read(new File("image.jpg"));
		} catch (IOException ioe) {
			System.out.println("Error: failed reading image.jpg");
			ioe.printStackTrace();
		}
		
		//Graphics2D g2 = (Graphics2D) g;
		
		Point p1 = this.getPoint(1);
		Point p2 = this.getPoint(2);
		
		g.drawImage(myImage, Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.abs(p1.x-p2.x), Math.abs(p1.y-p2.y), null);
	}

}
