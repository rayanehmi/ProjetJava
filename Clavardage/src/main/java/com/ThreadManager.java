package com;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;


public class ThreadManager {
	
	public String pseudo;
    public InetAddress IP;
    public ArrayList<String> listResponseThreadManager;
    
    public ThreadManager(String pseudo, InetAddress IP) {
    	this.pseudo = pseudo;
    	this.IP = IP;
    }
    
    public ThreadManager() {
    }		
	
	/**
     * Ecoute les broadcasts.
     * Stocke les reponses dans listResponseThreadManager
     */
	public void listenBroadcast() throws Exception {
		
		listResponseThreadManager = new ArrayList<String>();
		
		UDP_Response UDP_Response = new UDP_Response();
		UDP_Response.run();
		
		int i = UDP_Response.listResponse.size();
		int k = 0;
		
		System.out.println(i + " reponses recues");
		
		while ( k < i) {
			listResponseThreadManager.add(UDP_Response.listResponse.get(k));
			k++;
		}
		
	}
	
	/**
     * Ecoute les broadcasts.
     * Renvoie /firstConnexion + pseudo + IP
     */
	public void UDP_Server(String pseudo,InetAddress IP) {
		
		Runnable listen = new UDP_Listener(pseudo,IP);
		Thread thread = new Thread(listen);
		thread.start();
		
	}
	
	/**
     * Ecoute les messages en TCP.
     * Cree un nouveau Thread pour chaque nouvelle connexion (max 10 connexion en meme temps )
     */
	public void TCP_Server() {
		
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

