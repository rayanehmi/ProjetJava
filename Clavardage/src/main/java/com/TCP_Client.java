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
	public ArrayList<String> listeMessages;
	public MainWindow mainWindow;

	public TCP_Client(String IPDest, String pseudoDest, MainWindow mainWindow) {
		this.IPDest = IPDest;
		this.pseudoDest = pseudoDest;
		this.mainWindow=mainWindow;
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
		
		mainWindow.erasePane();
		if (!listeMessages.isEmpty()) {
			for (String message : listeMessages) {
				String[] splitMessage = message.split("_");
				if (splitMessage[0]==pseudoDest) {
					mainWindow.appendToPane(splitMessage[0],Color.blue); //pseudo collegue 
				} else {
					mainWindow.appendToPane(splitMessage[0],Color.red); //pseudo
				}
				//mainWindow.appendToPane(", a \n"+splitMessage[2],Color.grey); //heure
				mainWindow.appendToPane(splitMessage[1]+"\n",Color.black); //message
			}
			
		}
		
		try {
			ClientSocket = new Socket(this.IPDest,port);
			System.out.println("debugCConnect");
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
				Scanner p = new Scanner(System.in);
				msg = p.nextLine();

					if (msg!="") {
						listeMessages.add(pseudoDest+"_"+msg);
						mainWindow.appendToPane(pseudoDest,Color.blue); //pseudo collegue 
						mainWindow.appendToPane(msg+"\n",Color.black); //contenu
						System.out.println("[MESSAGE ENVOYE PAR " + this.pseudoDest + "] : " + msg);
						//msg = this.pseudoDest + "_" + msg;
						//out.println(msg);
						out.flush();
					}
					
				p.close();
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