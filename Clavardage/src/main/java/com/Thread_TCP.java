package com;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Thread_TCP implements Runnable {
	
	public String pseudo;
	public InetAddress IP;
	
	public Thread_TCP(String pseudo,InetAddress IP) {
		this.pseudo = pseudo;
		this.IP = IP;
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
                
                Runnable TCP_Server = new TCP_Server(this.pseudo, clientSocket);
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
