package com;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import gui.MainWindow;

public class TCP_Server implements Runnable {

	public MainWindow mainWindow;
	private String pseudoDest;
    private Socket socket;
    public BufferedReader in = null; //

    public TCP_Server(String pseudo, Socket socket, MainWindow mainWindow) {
    	
        this.pseudoDest = pseudo;
        this.socket = socket;
        this.mainWindow = mainWindow;
        try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	/**
     * Affiche les messages recus
     * Se termine des que le message "/over" est recu
     */


    public void run() {
        try {
        	//Ouvrir interface
        	
    		//Messaging messaging = new Messaging();
    		//messaging.frame.setVisible(true);
    		
        	String msg="";
        	//System.out.println("debugT1");
        	DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        	System.out.println("debugT2");
        	        	
        	while(!msg.equals("/over")) 
        	{
        		msg=in.readLine();
        		System.out.println(msg);
        		
            	String[] msgRecu = msg.split("_");
            	System.out.println(pseudoDest);
            	
        		
	        		while(msg!=null)  
	            	{
	    				System.out.println("[MESSAGE RECU " + this.pseudoDest + "] : " + msg);
	    				msgRecu = msg.split("_");
	    				// envoyer les messages vers l'interface
	    				mainWindow.appendToPane(msgRecu[0] + " said : ",Color.red); //pseudo collegue 
						mainWindow.appendToPane(msgRecu[1]+"\n",Color.black); //contenu
						
	    				// envoyer les messages vers la DataBase
						
	    		        outputStream.writeUTF(msg);
	    		        outputStream.flush();
	    		        msg=in.readLine();
	    			}
	        		
            }
        	
        	
        	
        	outputStream.close();
            socket.close();
            in.close();
            
        } catch (IOException e) {
        }
    }
	
}

