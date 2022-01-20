package com;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gui.MainWindow;

public class Thread_UDP_Listener implements Runnable {
	
	public String pseudoDest;
	public InetAddress IP;
	public MainWindow mainWindow;
	public DatagramSocket dgramSocket;
	
	public Thread_UDP_Listener(String pseudo,InetAddress IP, MainWindow mainWindow) {
		this.pseudoDest = pseudo;
		this.IP = IP;
		this.mainWindow=mainWindow;
	}
	
	public void run() {
		try {
			dgramSocket = new DatagramSocket(10001);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int i = 1;
		ExecutorService pool = Executors.newCachedThreadPool();
		
		while (i < 10) {
		    
		    Runnable UDP_Listener = new UDP_Listener(pseudoDest,IP,mainWindow, dgramSocket);
		    pool.execute(UDP_Listener);
		    i++;
		    
		}
		
		pool.shutdown();
    }
	

}