package com;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import gui.MainWindow;

public class Thread_Client implements Runnable {
	
	public ArrayList<String> listePseudos = new ArrayList<String>();
	public ArrayList<InetAddress> listeIPs = new ArrayList<InetAddress>();
	public MainWindow mainWindow;
	
	public Thread_Client (ArrayList<String> listePseudos,ArrayList<InetAddress> listeIPs,MainWindow mainWindow) {
		this.listePseudos = listePseudos;
		this.listeIPs = listeIPs;
		this.mainWindow = mainWindow;
	}
	
	public void run() {
		String pseudoInitial = null;
		InetAddress IPinitial = null;
		
		while(mainWindow.pseudoChoisi ==(null)) {
			try {Thread.sleep(2000);} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
		
		while(mainWindow.pseudoChoisi.equals(pseudoInitial)) {
			try {Thread.sleep(2000);} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
		
		pseudoInitial = mainWindow.pseudoChoisi;
		for(int i = 0; i<listePseudos.size();i++) {
			if(listePseudos.get(i).equals(pseudoInitial)) {
				IPinitial = listeIPs.get(i);
			}
		}
		
		//System.out.println(IPinitial.toString());
		//System.out.println(pseudoInitial);
		TCP_Client Client = new TCP_Client(IPinitial.toString().substring(1),pseudoInitial);
		Client.conversation();
	}
	
	

}
