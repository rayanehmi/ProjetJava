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
			e.printStackTrace();
		}
    }
    
	/**
     * Affiche les messages recus
     * Se termine des que le message "/over" est recu
     */


    public void run() {
        try {

        	String msg="";
        	DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        	        	
        	while(!msg.equals("/over")) 
        	{
        		msg=in.readLine();
            	
	        		while(msg!=null)  
	            	{
	        			String[] msgRecu = msg.split("_");
	    				System.out.println("[MESSAGE RECU " + this.pseudoDest + "] : " + msg);
	    				msgRecu = msg.split("_");
	    				// envoyer les messages vers l'interface
	    				mainWindow.appendToTabbedPane(msgRecu[0],msgRecu[0] + " said : ",Color.red); //pseudo collegue 
						mainWindow.appendToTabbedPane(msgRecu[0],msgRecu[1]+"\n",Color.black); //contenu
						
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

