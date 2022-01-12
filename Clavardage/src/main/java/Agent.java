import java.util.Scanner;

import manager.com.Client;
import manager.com.Thread;

public class Agent {

	public String numVersion; 
	public String pseudo;
	
	public Agent(String numVersion) {
		
		this.numVersion = numVersion;
		
	}
	
	public void getPseudo() {
		
		@SuppressWarnings("resource")
		Scanner p = new Scanner(System.in);
		this.pseudo = p.nextLine();
		
	}
	
	public void createClient(String IP) {
		
		Client CM = new Client(IP);
		CM.conversation();
		
	}
	
	public void createServer(String pseudo) {
		
		Thread TM = new Thread(pseudo);
		try {
			TM.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String [] argv) {
		
		Agent agt = new Agent("ok");
		agt.getPseudo();
		agt.createServer(agt.pseudo);
		// Lors du clic sur le bouton on lance la conversation
	
		
	}
	
}
