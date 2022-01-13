package com;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCP_Client {
	
	public String IPDest;
	public String pseudoUser;

	public TCP_Client(String newIP, String pseudoUser) {
		this.IPDest = newIP;
		this.pseudoUser = pseudoUser;
	}
	
	public String getIP() {
		return(this.IPDest);
	}
	
	/**
     * Envoie les messages a l'IP donne en parametre 
     * Affiche les messages envoyes
     * Se termine des que le message "/over" est recu
     */
	
	@SuppressWarnings("resource")
	public void conversation () {
		
		int port = 10000;
		Socket ClientSocket = null;
		PrintWriter out = null;
		
		
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
		
		//Ouvrir interface
		//System.out.println("debug0");
		//Messaging messaging = new Messaging();
		//messaging.frame.setVisible(true);
		
		String msg ="";
		System.out.println("debugC1");

			while (msg!="/over")
				{
				System.out.println("debugC2");
				Scanner p = new Scanner(System.in);
				msg = p.nextLine();

					if (msg!="") {
						
						//System.out.println("debugC3");
						System.out.println("[MESSAGE ENVOYE PAR " + this.pseudoUser + "] : " + msg);
						msg = this.pseudoUser + "_" + msg;
						out.println(msg);
						// envoyer les messages vers l'interface
						//messaging.setMessage("g envoye le msg : "+msg);
						//messaging.messageSent="";
						// envoyer les messages vers la DataBase
						out.flush();
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