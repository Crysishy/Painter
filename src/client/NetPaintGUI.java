package client;

/**
 * Class: NetPaintGUI.java
 * Purpose: This class is the GUI for NetPaint program. It is responsible for:
 * 			1. creating an interface for users;
 * 			2. send color messages to canvas;
 * 			3. send paintType messages to canvas.
 * Programmer: YANG HONG
 * 			   Haozhe Xu
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Canvas;

public class NetPaintGUI extends JFrame{

	private static final long serialVersionUID = 8575591141756075510L;
	private Canvas canvas;
	private JScrollPane canvasScrollPane;
	private JRadioButton lineButton;
	private JRadioButton rectButton;
	private JRadioButton ovalButton;
	private JRadioButton imagButton;
	private ButtonListener buttonListener;
	private ButtonGroup buttonGroup;
	private JPanel buttonPanel;
	private JColorChooser colorChooser;
	private ColorListener colorListener;
	
	public NetPaintGUI() {
		this.setTitle("Collaborative Painting");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		canvas = new Canvas();
		canvasScrollPane = new JScrollPane(canvas);
		
		colorChooser = new JColorChooser();
		colorListener = new ColorListener();
		colorChooser.getSelectionModel().addChangeListener(colorListener);
		
		setupButtons();
		
		this.add(canvasScrollPane, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.PAGE_START);
		this.add(colorChooser, BorderLayout.PAGE_END);
		this.setVisible(true);
	}

	private void setupButtons() {
		buttonListener = new ButtonListener();
		lineButton = new JRadioButton("Line");
		lineButton.setActionCommand("Line");
		lineButton.addActionListener(buttonListener);
		rectButton = new JRadioButton("Rectangle");
		rectButton.setActionCommand("Rectangle");
		rectButton.addActionListener(buttonListener);
		ovalButton = new JRadioButton("Oval");
		ovalButton.setActionCommand("Oval");
		ovalButton.addActionListener(buttonListener);
		imagButton = new JRadioButton("Image");
		imagButton.setActionCommand("Image");
		imagButton.addActionListener(buttonListener);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(lineButton);
		buttonGroup.add(rectButton);
		buttonGroup.add(ovalButton);
		buttonGroup.add(imagButton);
		
		buttonPanel = new JPanel(new GridLayout(1, 4));
		buttonPanel.add(lineButton);
		buttonPanel.add(rectButton);
		buttonPanel.add(ovalButton);
		buttonPanel.add(imagButton);
	}

	public static void main(String[] args) {
		new NetPaintGUI();
	}
	
	// ---------- Class ColorListener() ----------
	// send chosen color message to Canvas()
	private class ColorListener implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			canvas.setColor(colorChooser.getColor());
			System.out.println("Color: " + colorChooser.getColor().toString());
		}	
	}
	
	// ---------- Class ButtonListener() ----------
	// send chosen paintType message to Canvas()
	private class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.setPaintType(e.getActionCommand());
			System.out.println("Shape: " + e.getActionCommand());
		}	
	}
}
