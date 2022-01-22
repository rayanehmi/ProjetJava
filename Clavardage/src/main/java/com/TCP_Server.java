package com;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Timestamp;

import db.DatabaseManager;
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
	    				String timeStamp = new Timestamp(System.currentTimeMillis()).toString();
						String jour = timeStamp.substring(0,10);
						String heure = timeStamp.substring(11,16);
						mainWindow.appendToTabbedPane(msgRecu[0], "Le "+jour+" a "+heure+", ",Color.gray);
						mainWindow.appendToTabbedPane(msgRecu[0],msgRecu[0] + " a dit : ",Color.blue); //pseudo collegue 
						mainWindow.appendToTabbedPane(msgRecu[0],msg+"\n",Color.black); //contenu
						
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

