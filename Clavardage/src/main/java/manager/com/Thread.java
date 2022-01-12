package manager.com;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import manager.gui.Messaging;

public class Thread {
	
	public String pseudo;
    ServerSocket mServerSocket = null;
    public String IP ;
    //public InetAddress IP ;
    
    public Thread(String pseudo) {
    	this.pseudo = pseudo;
    }
    
    public InetAddress getIPAddress() throws SocketException {
    	
    	 Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
                 NetworkInterface e = n.nextElement();
                 Enumeration<InetAddress> a = e.getInetAddresses();
                 InetAddress addr = a.nextElement();
                 InetAddress addr2 = a.nextElement();
                 //System.out.println("  " + addr2.getHostAddress());
                 return addr2;
    
    }

    public void start() throws Exception {
        try {
        	//this.IP = this.getIPAddress(); 
        	this.IP = "/127.0.0.1";
        	System.out.println("Is listening on " + this.IP.substring(1));
            mServerSocket = new ServerSocket(10000, 2, InetAddress.getByName(this.IP.toString().substring(1)));
            ///////////////InetAddress var = InetAddress.getByName("10.1.5.14");

            
            int i = 1;
            ExecutorService pool = Executors.newCachedThreadPool();
            while (i < 10) {
                Socket clientSocket = mServerSocket.accept();
                System.out.println("new connexion " + i);
                
                Runnable task = new Task(this.pseudo, clientSocket);
                pool.execute(task);
                i++;
            }
            pool.shutdown();
            mServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Task implements Runnable {
    private String name;
    private Socket socket;
    public BufferedReader in = null; //

    public Task(String name, Socket socket) {
    	
        this.name = name;
        this.socket = socket;
        try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    public void run() {
        try {
        	//Ouvrir interface
        	
    		Messaging messaging = new Messaging();
    		messaging.frame.setVisible(true);
    		
        	String msg="";
        	//System.out.println("debugT1");
        	DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        	//System.out.println("debugT2");
        	while(msg!="/over") 
        	{
        		msg=in.readLine();
        		while(msg!=null)  
            	{
            		//System.out.println("debugT3");
    				System.out.println("[SERVER:" + name + "] sent: " + msg);
    				// envoyer les messages vers l'interface
    				messaging.setMessage(name+" sent : "+msg);
    				// envoyer les messages vers la DataBase

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

class MultiThreadMain {
    public static void main(String[] args) throws Exception {
        Thread server = new Thread("Paul");
        server.start();
    }
}