package com;
import java.net.*;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import gui.MainWindow;

public class TCP_Client {
	
	public String IPDest;
	public String pseudoDest;
	public ArrayList<String> listeMessages = new ArrayList<String>();
	public MainWindow mainWindow;
	private String pseudo;

	public TCP_Client(String IPDest, String pseudoDest, MainWindow mainWindow) {
		this.IPDest = IPDest;
		this.pseudoDest = pseudoDest;
		this.mainWindow=mainWindow;
		this.pseudo = mainWindow.pseudo;
	}
	
	public String getIP() {
		return(this.IPDest);
	}
	
	/**
     * Envoie les messages a l'IP donne en parametre 
     * Affiche les messages envoyes
     * Se termine des que le message "/over" est recu
     */
	public void conversation () {
		
		int port = 10000;
		Socket ClientSocket = null;
		PrintWriter out = null;
		
		
		try {
			ClientSocket = new Socket(this.IPDest,port);
			mainWindow.feedback.setText("Connecte a "+pseudoDest);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			out = new PrintWriter(ClientSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		String msg ="";
			while (!msg.equals("/over"))
				{
				
				try {Thread.sleep(200);} 
				catch (InterruptedException e) {e.printStackTrace();}
				msg = mainWindow.messageToSend;
				
					if (!msg.equals("")) {
						listeMessages.add(pseudo+"_"+msg);
						out.println(pseudo+"_"+msg);
						mainWindow.appendToTabbedPane(pseudoDest,pseudo + " said : ",Color.blue); //pseudo collegue 
						mainWindow.appendToTabbedPane(pseudoDest,msg+"\n",Color.black); //contenu
						System.out.println("[MESSAGE ENVOYE PAR " + this.pseudo + "] : " + msg);
						out.flush();
						mainWindow.messageToSend="";
					}
				
				
				}
		
		try {
			ClientSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out.close();
		
		
	}
}