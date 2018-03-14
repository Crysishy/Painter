package server;

/**
 * Class: Server.java
 * Purpose: This class provides the server for NetPaint. It is responsible for
 * 			establishing connections between/among several clients(NetPaintGUI).
 * Programmer: YANG HONG
 * 			   Haozhe Xu
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import model.PaintObject;

public class Server implements Runnable {

	public static int SERVER_PORT = 9001;
	private static ServerSocket socket = null;
	private static List<ObjectOutputStream> clients = Collections.synchronizedList(new ArrayList<ObjectOutputStream>());
	private Vector<PaintObject> paints;

	public static void main(String[] args) {
		new Thread(new Server()).start();
	}

	@Override
	public void run(){
		try {
			socket = new ServerSocket(SERVER_PORT);
		} catch (IOException ioe){
			System.out.println("(SERVER) Error: ServerSocket starting failed");
			ioe.printStackTrace();
		}
		paints = new Vector<PaintObject>();
		System.out.println("(SERVER) Server started at PORT " + SERVER_PORT);
		while(true){
			Socket s= null;
			ObjectInputStream input = null;
			try{
			s = socket.accept();
			} catch (IOException ioe){
				return;
			}
			try{
			input = new ObjectInputStream(s.getInputStream());
			ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
			clients.add(output);
			} catch (IOException ioe){
				System.out.println("(SERVER) Error: ServerSocket accepting failed");
				ioe.printStackTrace();
			}
			System.out.println("(SERVER) A client has successfully connected to Server");
			new Thread(new ClientHandler(input, clients)).start();
		}
	}
	
	// ---------- Class ClientHandler() ----------
	// This inner class handles several clients within the server
	// It has two functionalities
	// 1. reading paints from server
	// 2. writing paints to server
	private class ClientHandler implements Runnable {
		
		private ObjectInputStream input;
		private List<ObjectOutputStream> clients;
		
		ClientHandler(ObjectInputStream input, List<ObjectOutputStream> clients) {
			this.input = input;
			this.clients = clients;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			try {
				writePaintToClient();
				while (true) {
					try {
						paints = (Vector<PaintObject>) input.readObject();
						System.out.println("(SERVER) Add paints to Server");
					} catch (ClassNotFoundException cnfe){
						cnfe.printStackTrace();
					}
					writePaintToClient();
				}
			} catch (IOException ioe){
				ioe.printStackTrace();
			}
		}
		
		private void writePaintToClient() {
			synchronized (clients) {
				for (ObjectOutputStream client : clients){
					try {					
						client.writeObject(paints);
						client.reset();
						System.out.println("(SERVER) Paint written to clients");
					} catch (IOException ioe){
						clients.remove(client);
						ioe.printStackTrace();
					}
				}
			}
		}
	}
}