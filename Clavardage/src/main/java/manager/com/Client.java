package manager.com;
import manager.gui.Messaging;

import java.net.*;
import java.io.*;
import java.util.Scanner;


public class Client {
	
	public String IP;
	
	public Client(String newIP) {
		this.IP = newIP;
	}
	
	public String getIP() {
		return(this.IP);
	}
	
	@SuppressWarnings("resource")
	public void conversation () {
		
		int port = 10000;
		Socket ClientSocket = null;
		PrintWriter out = null;
		
		
		try {
			ClientSocket = new Socket(this.IP,port);
			//System.out.println("debugCConnect");
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
		Messaging messaging = new Messaging();
		messaging.frame.setVisible(true);
		
		String msg ="";
		//System.out.println("debugC1");

			while (msg!="/over")
				{
				//System.out.println("debugC2");
				msg = messaging.getMessage();
					if (msg!="") {
						
						//System.out.println("debugC3");
						System.out.println(msg);
						out.println(msg);
						// envoyer les messages vers l'interface
						messaging.setMessage("g envoye le msg : "+msg);
						messaging.messageSent="";
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

class MultiClientMain {
    public static void main(String[] args) throws Exception {
        //Client client = new Client("10.1.5.14");
    	Client client = new Client("127.0.0.1");
        client.conversation();
    }
}
