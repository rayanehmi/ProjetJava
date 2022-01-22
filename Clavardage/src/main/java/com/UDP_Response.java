package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;


class UDP_Response implements Runnable {
	
    private DatagramSocket dgramSocket;
    public BufferedReader in = null; //
    public ArrayList<String> listResponse = new ArrayList<String>();
    public int port;
	

	public UDP_Response(int port) {
    	this.port=port;
    }
    
	public void run() {
		
		listResponse = new ArrayList<String>();

		try {
			dgramSocket = new DatagramSocket(port);
			dgramSocket.setSoTimeout(1000);
		} catch (SocketException e1) {
			
			e1.printStackTrace();
		}

		
			
			
			
		try {
			
			while(true) {
				byte[] buffer = new byte[256];
				
				DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
				
				dgramSocket.receive(inPacket);
				
				String message = new String(inPacket.getData(), 0, inPacket.getLength());
				
				if (message != null) {
	    			String[] msgRecu = message.split("_");
	    			
	    			if (msgRecu[0].equals("/firstConnexion")) {
	    				
						listResponse.add(message);
						
						
						}
	    		}
			}
			
			
		} catch (IOException e) {
			System.out.println("Fin de l'ecoute");
			dgramSocket.close();
		}
		
	}
	
}