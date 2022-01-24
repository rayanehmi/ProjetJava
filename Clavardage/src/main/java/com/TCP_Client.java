package com;
import db.DatabaseManager;
import java.net.*;
import java.sql.Timestamp;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			out = new PrintWriter(ClientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		// database
		DatabaseManager dbManager = new DatabaseManager();
		dbManager.registerIfDoesntExist(pseudo);
		dbManager.registerIfDoesntExist(pseudoDest); //peut etre inutile
		mainWindow.addTabToPane(pseudoDest);
		ArrayList<String> listeMessages = dbManager.getMessagesFromDatabase(pseudo, pseudoDest);
		
		//Affichage de l'historique
		for (String message : listeMessages) {
			//System.out.println(message);
			String[] messageDecoupe = message.split("_");
			String jour = messageDecoupe[2].substring(0,10);
			String heure = messageDecoupe[2].substring(11,16);
			
			//Si l'envoyeur est nous
			if (messageDecoupe[0].equals(pseudo)) {
				mainWindow.appendToTabbedPane(pseudoDest, "Le "+jour+" a "+heure+", ",Color.gray);
				mainWindow.appendToTabbedPane(pseudoDest,pseudo + " a dit : ",Color.red);
				mainWindow.appendToTabbedPane(pseudoDest,messageDecoupe[1]+"\n",Color.black);
			}
			//Si l'envoyeur est le collegue
			else if (messageDecoupe[0].equals(pseudoDest)) {
				mainWindow.appendToTabbedPane(pseudoDest, "Le "+jour+" a "+heure+", ",Color.gray);
				mainWindow.appendToTabbedPane(pseudoDest,pseudoDest + " a dit : ",Color.blue);
				mainWindow.appendToTabbedPane(pseudoDest,messageDecoupe[1]+"\n",Color.black);
			}
		}
		
		// Partie comm
		
		String msg ="";
			while (!msg.equals("/over"))
				{
				
				try {Thread.sleep(200);} 
				catch (InterruptedException e) {e.printStackTrace();}
				msg = mainWindow.messageToSend;
				
					if (!msg.equals("")) {
						listeMessages.add(pseudo+"_"+msg);
						out.println(pseudo+"_"+msg);
						String timeStamp = new Timestamp(System.currentTimeMillis()).toString();
						String jour = timeStamp.substring(0,10);
						String heure = timeStamp.substring(11,16);
						mainWindow.appendToTabbedPane(pseudoDest, "Le "+jour+" a "+heure+", ",Color.gray);
						mainWindow.appendToTabbedPane(pseudoDest,pseudo + " a dit : ",Color.red); //pseudo collegue 
						mainWindow.appendToTabbedPane(pseudoDest,msg+"\n",Color.black); //contenu
						out.flush();
						dbManager.messageToDatabase(msg,pseudo,pseudoDest); //ajoute le message a la database
						mainWindow.messageToSend="";
					}
				
				
				}
		
		try {
			ClientSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		out.close();
		
		
	}
}