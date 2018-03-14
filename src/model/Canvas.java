package model;

/**
 * Class: Canvas.java
 * Purpose: This class is the canvas for NetPaint. It is responsible for:
 * 			1. drawing shapes on canvas;
 * 			2. read paints from server;
 * 			3. write paints to server.
 * Programmer: YANG HONG
 * 			   Haozhe Xu
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.JPanel;
import server.Server;

public class Canvas extends JPanel{

	private static final long serialVersionUID = 1L;
	private int newX, newY;
	private boolean isDrawing;
	private PaintObject ghostPaint;
	private Vector<PaintObject> paints;
	Socket socket;
	ObjectInputStream inStream;
	ObjectOutputStream outStream;
	private Color currentColor;
	private String currentPaintType;
	
	public Canvas(){
		super();
		isDrawing = false;
		paints = new Vector<PaintObject>();
		currentColor = Color.BLACK;
		currentPaintType = "Line";
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(2048,1024));
		MListener mListener = new MListener();
		this.addMouseListener(mListener);
		this.addMouseMotionListener(mListener);
		establishConnection();
		ServerHandler serverHandler = new ServerHandler();
		serverHandler.start();
	}
	
	@SuppressWarnings("unchecked")
	private void establishConnection() {
		try {
			socket = new Socket("localHost", Server.SERVER_PORT);
			outStream = new ObjectOutputStream(socket.getOutputStream());
			inStream = new ObjectInputStream(socket.getInputStream());
			try {
				paints = (Vector<PaintObject>) inStream.readObject();
			} catch (ClassNotFoundException cnfe){
				System.out.println("(GUI) Canvas - estaConnect - ClassNotFound");
			}
		} catch (UnknownHostException ukhe) {
			System.out.println("(GUI) Canvas - estaConnect - UnknownHost");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("(GUI) Canvas - estaConnect - IO");
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (PaintObject paint : paints)
			paint.draw(g);
		
		if (isDrawing && ghostPaint != null)
			ghostPaint.draw(g);
	}
	
	public void setColor(Color color){
		currentColor = color;
	}
	
	public void setPaintType(String type){
		currentPaintType = type;
	}
	
	// ---------- Class MListener() inside Canvas() ----------
	// mouse listener class
	// mouseClicked() is responsible for reading and writing paints
	// mouseMoved() is responsible for drawing ghost paint
	private class MListener implements MouseListener, MouseMotionListener{

		@Override
		public void mouseMoved(MouseEvent e) {
			newX = e.getX();
			newY = e.getY();
			if (isDrawing && ghostPaint != null){
				ghostPaint.setPoint2(new Point(newX, newY));
				repaint();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			newX = e.getX();
			newY = e.getY();
			Point pt = new Point(newX, newY);
			if (!isDrawing){
				switch (currentPaintType) {
				case ("Line"):
					ghostPaint = new Line(currentColor, pt, pt);
					System.out.println("Drawing: Line");
					break;
				case ("Rectangle"):
					ghostPaint = new Rectangle(currentColor, pt, pt);
					System.out.println("Drawing: Rectangle");
					break;
				case ("Oval"):
					ghostPaint = new Oval(currentColor, pt, pt);
					System.out.println("Drawing: Oval");
					break;
				case ("Image"):
					ghostPaint = new MyImage(currentColor, pt, pt);
					System.out.println("Drawing: Image");
					break;
				default:
					ghostPaint = null;
					System.out.println("Choose a shape");
					break;
				}
				isDrawing = true;
			}
			else {
				ghostPaint.setPoint2(new Point(e.getX(), e.getY()));
				paints.add(ghostPaint);
				try {
					outStream.writeObject(paints);
				} catch (IOException ioe){
					System.out.println("(GUI) Canvas - MListener - mouseClicked");
				}
				isDrawing = false;
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		
	}
	
	// ---------- Class ServerHandler() inside Canvas() ----------
	// handle server messages
	// read paints from server
	private class ServerHandler extends Thread {
		@SuppressWarnings("unchecked")
		@Override
		public void run(){
			while (true){
				try {
					paints = (Vector<PaintObject>) inStream.readObject();
					System.out.println("(GUI) Canvas - ServerHandler - read paints from server");
					repaint();
				} catch (ClassNotFoundException cnfe){
					System.out.println("(GUI) Canvas - ServerHandler - ClassNotFound");
				}catch (IOException ioe){
					System.out.println("(GUI) Canvas - ServerHandler - IO");
				}
			}
		}
	}
	
	
}
