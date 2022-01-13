package com;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


class UDP_Response implements Runnable {
	
    private DatagramSocket dgramSocket;
    public BufferedReader in = null; //
    public ArrayList<String> listResponse = new ArrayList<String>();

	
	public void run() {
		
		listResponse = new ArrayList<String>();

		try {
			dgramSocket = new DatagramSocket(10001);
			dgramSocket.setSoTimeout(2000);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			System.out.println("Fin de l'ecoute");
			dgramSocket.close();
		}
		
	}
	
}