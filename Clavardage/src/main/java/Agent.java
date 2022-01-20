import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JList;

//Fichiers du projet
import com.*;
import gui.*;

public class Agent {
	
	static Welcome welcomePage;
	public static String pseudonyme;
	public static InetAddress IP;
	public static ArrayList<String> listePseudos = new ArrayList<String>();
	public static ArrayList<InetAddress> listeIPs = new ArrayList<InetAddress>();
	public static MainWindow mainWindow;
	public static String pseudoSelectionne = "";
	public static boolean newEntryFlag;
	
	/**
	 * Renvoie une adresse IP locale.
	 * Doit commencer par 192.168 ou 10.1 (INSA).
	 */
	public static InetAddress getIPAddress() throws SocketException {
		InetAddress addr;
		InetAddress addrFinale=null;
		Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
        for (; n.hasMoreElements();)
        {
        	NetworkInterface e = n.nextElement();
        	Enumeration<InetAddress> a = e.getInetAddresses();
        	for (; a.hasMoreElements();)
        	{
        		addr = a.nextElement();
        		if (addr.toString().substring(1, 8).contentEquals("192.168") 
        		 || addr.toString().substring(1, 4).contentEquals("10.")) 
        		{addrFinale=addr;}
        	}
        }
        return addrFinale;
   }
	
	/**
	 * Envoie un paquet UDP en broadcast sur le reseau local.
	 * "address" doit etre une adresse de broadcast.
	 * @throws IOException 
	 */
	public static void broadcast(String broadcastMessage, InetAddress address) throws IOException{
		DatagramSocket datagramSocket = new DatagramSocket();
		datagramSocket.setBroadcast(true);
        byte[] buffer = broadcastMessage.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 10001);
        datagramSocket.send(packet);
        datagramSocket.close();
    }
	
	/**
	 * Met a jour la table des pseudos & la table des IPs.
	 * pseudo[i] correspond a IP[i].
	 * @throws UnknownHostException 
	 */
	private static void updateConnectedList(ArrayList<String> messageList) throws UnknownHostException {
		//Pour chaque reponse recue
		for(int i = 0 ; i < messageList.size(); i++) {
			if (messageList.get(i)!=null) {
				//On decoupe le message au niveau des "/"
				String[] splitMessage = messageList.get(i).split("_");
				
				if(splitMessage[0].equals("/firstConnexion")) {
					listePseudos.add(splitMessage[2]);
					listeIPs.add(InetAddress.getByName(splitMessage[1].substring(1)));
				}
			}
		}
		
	}
	
	/**
	 * Met a jour la table des pseudos & la table des IPs.
	 * pseudo[i] correspond a IP[i].
	 * @throws Exception 
	 */
	private static void refreshConnectedList() throws Exception {
		
		listePseudos.clear();
		listeIPs.clear();
		//Creation d'un thread manager en charge de recevoir les reponses TCP
		ThreadManager TM = new ThreadManager();
		ArrayList<String> listAnswers = new ArrayList<String>();
		
		//Lancement du broadcast sur le reseau local
		String[] temp= IP.toString().substring(1).split("\\.");
		InetAddress broadcastAddress = InetAddress.getByName(temp[0]+"."+temp[1]+".255.255");
		broadcast("/refresh",broadcastAddress);
		System.out.println("Broadcast lance");
		
		//Appel du server sur Ecoute
		TM.listenRefresh();
		
		listAnswers = TM.listResponseThreadManager;
		String[] tableauPseudos = new String[listAnswers.size()];
		
		if (!listAnswers.isEmpty()) {
			//Pour chaque reponse recue
			for(int i = 0 ; i < listAnswers.size(); i++) {
				if (listAnswers.get(i)!=null) {
					//On decoupe le message au niveau des "/"
					String[] splitMessage = listAnswers.get(i).split("_");
					
					if(splitMessage[0].equals("/firstConnexion")) {
						if (!splitMessage[2].equals(pseudonyme)) {
							listePseudos.add(splitMessage[2]);
							tableauPseudos[i] = splitMessage[2]; 
							listeIPs.add(InetAddress.getByName(splitMessage[1].substring(1)));
						}
						
					}
				}
			}
		} 
		

		mainWindow.arrayConnectes = listePseudos;
		mainWindow.listeConnectes.setListData(tableauPseudos);
		mainWindow.feedback.setText("Liste pseudo rafraichie");
		
		
	}
	
	/**
	 * Verifie si le pseudonyme est disponible.
	 * Envoie un broadcast sur le reseau local.
	 * Appelle une mise a jour des pseudos en ligne.
	 * @throws Exception 
	 */
	private static boolean checkPseudo(String pseudo) throws Exception {
		boolean available = true;
		
		//Creation d'un thread manager en charge de recevoir les reponses TCP
		ThreadManager TM = new ThreadManager();
		ArrayList<String> listAnswers = new ArrayList<String>();
		
		//Lancement du broadcast sur le reseau local
		String[] temp= IP.toString().substring(1).split("\\.");
		InetAddress broadcastAdress = InetAddress.getByName(temp[0]+"."+temp[1]+".255.255");
		broadcast("/askingConnexion",broadcastAdress);
		System.out.println("Broadcast lance");
		
		//Appel du server sur Ecoute
		TM.listenBroadcast();
		
		
		listAnswers = TM.listResponseThreadManager;
		if (!listAnswers.isEmpty()) {
			updateConnectedList(listAnswers);	
		}
		
		if (!listePseudos.isEmpty()) {
			for (String pseudoTeste : listePseudos) {
				
	            if (pseudo.equals(pseudoTeste)) {
	                available = false;
	                break;
	            }
	        }
		}
	
		return available;
	}
	
	/**
	 * Affiche la page de connexion.
	 * Modifie la variable "pseudonyme".
	 * Envoie un broadcast pour verifier la disponibilite du pseudonyme.
	 * Termine lorsque l'on a trouve un pseudo disponible.
	 * @throws Exception 
	 */
	public static void welcomePage() throws Exception{
		
		//Creation de l'interface de bienvenue
		welcomePage = new Welcome();
		welcomePage.setVisible(true);	
		
		//Toutes les 200ms, on regarde si l'utilisateur a entre un nouveau pseudo
		String currentPseudo = null;
		while (welcomePage.pseudoChoisi == currentPseudo) {
			try {Thread.sleep(200);} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
		currentPseudo=welcomePage.pseudoChoisi;
		welcomePage.feedbackText.setForeground(Color.GREEN);
		welcomePage.feedbackText.setText("Connexion en cours...");
		
		//Si le premier pseudo n'est pas disponible, on boucle jusqu'a en trouver un valide
		while (!checkPseudo(currentPseudo)) {
			welcomePage.feedbackText.setForeground(Color.RED);
			welcomePage.feedbackText.setText("Le pseudo "+currentPseudo+" n'est pas disponible.");
			while (welcomePage.pseudoChoisi == currentPseudo) {
				try {Thread.sleep(200);} 
				catch (InterruptedException e) {e.printStackTrace();}
			}
			currentPseudo = welcomePage.pseudoChoisi;
			welcomePage.feedbackText.setForeground(Color.GRAY);
			welcomePage.feedbackText.setText("Test du pseudo "+currentPseudo);
		};
		
		//Une fois le pseudo valide, on l'inscrit et on ferme la page de connexion
		pseudonyme = currentPseudo;
		welcomePage.setVisible(false);
	}
	
	/**
	 * Fonction qui lance une conversation avec un pseudo choisi
	 */
	
	public static void startConv(){
		
		Runnable threadClient = new Thread_Client(listePseudos,listeIPs,mainWindow);
		Thread thread = new Thread(threadClient);
		thread.start();
		
	}
	
	/**
	 * main.
	 * Fonction lancee a l'execution du programme
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		//Obtention de l'adresse IP
		IP = getIPAddress();
		
		//Affichage de la page de bienvenue
		//Obtention d'un pseudonyme
		welcomePage();
		
		//Lancement de l'ecoute des broadcasts
		System.out.println("Lancement du server UDP sur ecoute");
		ThreadManager UDP_Listener = new ThreadManager(mainWindow);
		UDP_Listener.UDP_Server(pseudonyme, IP);
		
		
		//Connexion a la database
		
		
		//Temporaire : ajoute des faux pseudos connectes
		listePseudos.add("PseudoTest1");
		listePseudos.add("PseudoTest2");
		
		//Lancement de l'interface principale
		mainWindow = new MainWindow(pseudonyme,listePseudos);
		mainWindow.setVisible(true);
		
		//Lancement de l'ecoute des messages entrants (TCP)

		ThreadManager TCP_TM = new ThreadManager(pseudonyme, IP);
		TCP_TM.TCP_Server(mainWindow);	
		
		while(true) {
			try {Thread.sleep(200);} 
			catch (InterruptedException e) {e.printStackTrace();}
			//System.out.println(UDP_Listener.newEntryFlag);
			if (mainWindow.refreshFlag) {
				System.out.println("Demande de rafraichissement...");
				mainWindow.refreshFlag=false;
				refreshConnectedList();
			}
			if (mainWindow.pseudoChoisi != pseudoSelectionne ) {
				pseudoSelectionne = mainWindow.pseudoChoisi ;
				startConv();
			}
			if (mainWindow.exitFlag) {
				System.out.println("Leaving...");
				String[] temp= IP.toString().substring(1).split("\\.");
				InetAddress broadcastAddress = InetAddress.getByName(temp[0]+"."+temp[1]+".255.255");
				broadcast("/over_"+pseudonyme, broadcastAddress);
				System.exit(0);
			}
		}
	}

}
