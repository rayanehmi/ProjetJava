package com;

import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import gui.MainWindow;


class UDP_Listener implements Runnable {
	
	private InetAddress IP;
	private String pseudo;
    private DatagramSocket dgramSocket;
    public MainWindow mainWindow;

    public UDP_Listener(String pseudo, InetAddress IP, MainWindow mainWindow, DatagramSocket dgramSocket) {
        this.IP = IP;
        this.pseudo = pseudo;
        this.mainWindow = mainWindow;
        this.dgramSocket = dgramSocket;
    }


	
	public void run() {
		
		while(true) {
		
			
			
			byte[] buffer = new byte[256];
			
			DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
			
			try {
				dgramSocket.receive(inPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			InetAddress clientAddress = inPacket.getAddress();
			int clientPort = inPacket.getPort();
	
			String message = new String(inPacket.getData(), 0, inPacket.getLength());
			
			if (message != null) {
    			String[] msgRecu = message.split("_");
    			
    			if (msgRecu[0].equals("/askingConnexion")) {
    				String response = "/firstConnexion_"+IP+"_"+pseudo;
    				
    				DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(),
    				clientAddress, 10001);
    		
    				try {
    					try {Thread.sleep(200);}
    					catch(InterruptedException e) {e.printStackTrace();}
						dgramSocket.send(outPacket);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    				
				}
    			
    			else if (msgRecu[0].equals("/refresh")) {
    				String response = "/firstConnexion_"+IP+"_"+pseudo;
    				
    				DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(),
    				clientAddress, 10002);
    		
    				try {
    					try {Thread.sleep(200);}
    					catch(InterruptedException e) {e.printStackTrace();}
						dgramSocket.send(outPacket);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    				
				}
    			
    			else if (msgRecu[0].equals("/over")) {
    				
    				mainWindow.appendToTabbedPane(msgRecu[1], msgRecu[1]+" s'est deconnecte.", Color.gray);
    				
				}
    		}
	
		}
		
	}
	
}