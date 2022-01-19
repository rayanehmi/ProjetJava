package com;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gui.MainWindow;

public class Thread_TCP implements Runnable {
	
	public String pseudoDest;
	public InetAddress IP;
	public MainWindow mainWindow;
	
	public Thread_TCP(String pseudo,InetAddress IP, MainWindow mainWindow) {
		this.pseudoDest = pseudo;
		this.IP = IP;
		this.mainWindow=mainWindow;
	}
	
	public void run() {
		try {

        	System.out.println("Is listening on " + this.IP);
            ServerSocket mServerSocket = new ServerSocket(10000, 2, this.IP);
            
            int i = 1;
            ExecutorService pool = Executors.newCachedThreadPool();
            
            while (i < 10) {
            	
                Socket clientSocket = mServerSocket.accept();
                System.out.println("new connexion " + i);
                
                Runnable TCP_Server = new TCP_Server(this.pseudoDest, clientSocket, mainWindow);
                pool.execute(TCP_Server);
                i++;
                
            }
            
            pool.shutdown();
            mServerSocket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	

}
