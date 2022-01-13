package com;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCP_Server implements Runnable {

	private String pseudo;
    private Socket socket;
    public BufferedReader in = null; //

    public TCP_Server(String pseudo, Socket socket) {
    	
        this.pseudo = pseudo;
        this.socket = socket;
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
        	//System.out.println("debugT2");
        	        	
        	while(msg!="/over") 
        	{
        		msg=in.readLine();
        		
            	String[] msgRecu = msg.split("_");
            	if(msgRecu[0].equals(this.pseudo)) {
        		
	        		while(msg!=null)  
	            	{
	            		//System.out.println("debugT3");
	    				System.out.println("[MESSAGE RECU " + this.pseudo + "] : " + msg);
	    				// envoyer les messages vers l'interface
	    				//messaging.setMessage(name+" sent : "+msg);
	    				// envoyer les messages vers la DataBase
	
	    		        outputStream.writeUTF(msg);
	    		        outputStream.flush();
	    		        msg=in.readLine();
	    			}
            	}
            	else {System.out.println("Erreur Destinataire");
        	}
        	}
        	
        	outputStream.close();
            socket.close();
            in.close();
            
        } catch (IOException e) {
        }
    }
	
}

