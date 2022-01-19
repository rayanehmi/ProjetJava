package com;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


class UDP_Listener implements Runnable {
	
	private InetAddress IP;
	private String pseudo;
    private DatagramSocket dgramSocket;
    public boolean newEntryFlag;

    public UDP_Listener(String pseudo, InetAddress IP, boolean newEntryFlag) {
        this.IP = IP;
        this.pseudo = pseudo;
        this.newEntryFlag=newEntryFlag;
    }


	
	public void run() {

		try {
			dgramSocket = new DatagramSocket(10001);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
    			
    			if (msgRecu[0].equals("/askingConnexion")&&(!msgRecu[1].equals(IP.toString()))) {
    				String response = "/firstConnexion_"+IP+"_"+pseudo;
    				newEntryFlag=true;
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
    		}
	
		}
		
	}
	
}