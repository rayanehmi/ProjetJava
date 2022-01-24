package db;

/* import JDBC classes */
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
	public Connection conn;
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public DatabaseManager() {
			
		/*
		 * tables : Message, 
		 * mysql -h srv-bdens.insa-toulouse.fr -P 3306 -u tp_servlet_014 -p bieNee0i
		 * (use tp_servlet_014)
		 * select count(pseudo) as total from User where pseudo="pseudo";
		 * insert into User values ("pseudo");
		 * date : varchar(23)
		 */
		
		//0. Variables
		String URL = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/";
		String dbname = "tp_servlet_014";
		String username = "tp_servlet_014";
		String access = "bieNee0i";
		
		
		//1. Declare a database connection object
		
		try {
		
			//2. Load the driver class file
			Class c = Class.forName("com.mysql.cj.jdbc.Driver") ;
			Driver pilote = (Driver)c.newInstance() ;
			DriverManager.registerDriver(pilote);
			//3. Make a database connection
			conn = DriverManager.getConnection(URL+dbname,username,access);
			System.out.println("Connected to database");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Renvoie true si le pseudo est enregistre dans la database, false sinon.
	 * @param pseudo
	 * @param conn
	 * @return
	 */
	public boolean existsInDatabase(String pseudo) {
		ResultSet rs;
		String query = "SELECT COUNT(pseudo) AS Total FROM User WHERE pseudo=\""+pseudo+"\";";
		System.out.println(query);
		try (Statement stmt = conn.createStatement()) {
		      //5. Execute the statement
		      rs = stmt.executeQuery(query);
		      rs.next();
		      return (rs.getInt("Total")>=1);
		} catch (SQLException e) {
	    	e.printStackTrace();
	    	return false;
		}
	}
	
	/**
	 * Ajoute le pseudo a la database.
	 * @param pseudo
	 * @param conn
	 */
	@SuppressWarnings("unused")
	public void addInDatabase(String pseudo) {
		String query = "INSERT INTO User VALUES (\""+pseudo+"\");";
		System.out.println(query);
		try (Statement stmt = conn.createStatement()) {
			int rs = stmt.executeUpdate(query);
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	/**
	 * Ajoute le pseudo a la database s'il n'y est pas encore.
	 * @param pseudo
	 * @param conn
	 */
	public void registerIfDoesntExist (String pseudo) {
		if (!existsInDatabase(pseudo)) {
			addInDatabase(pseudo);
		}
	}
	
	/**
	 * Ajoute le message a la database. Horodate par l'horloge locale.
	 * @param message
	 * @param pseudo
	 * @param pseudoDest
	 * @param conn
	 */
	public void messageToDatabase(String message, String pseudo, String pseudoDest) {

		
	    long millis=System.currentTimeMillis();  
	    Timestamp date=new Timestamp(millis);
	    String query = "INSERT INTO Message VALUES (\""+pseudo+"\",\""+pseudoDest+"\",\""
    			+date+"\", \""+message+"\");";
	    System.out.println(query);
	    
		try (Statement stmt = conn.createStatement()) {
			//5. Execute the statement
			int rs = stmt.executeUpdate(query);
		
		} catch (SQLException e) {
	    	e.printStackTrace();
		}
		
	}
	
	/**
	 * Renvoie la liste des messages echanges entre pseudo et pseudoDest triee par heure d'envoi.
	 * Le format d'un message est : pseudo_contenu_date.
	 * @return
	 */
	public ArrayList<String> getMessagesFromDatabase(String pseudo, String pseudoDest){
		ArrayList<String> listeMessages = new ArrayList<String>();
		String query = "SELECT msg, pseudo, date FROM Message WHERE ((pseudo=\""+pseudo+"\" AND pseudoDest=\""
				+pseudoDest+"\") OR (pseudo=\""+pseudoDest+"\" AND pseudoDest=\""+pseudo
				+"\")) ORDER BY date;";
		System.out.println(query);
		
		try (Statement stmt = conn.createStatement()) {
		      //5. Execute the statement
		      ResultSet rs = stmt.executeQuery(query);
		      while (rs.next()) {
		    	  String Message = rs.getString("msg");
		    	  String Pseudo = rs.getString("pseudo");
		    	  Timestamp Timestamp = rs.getTimestamp("date");
		    	  String messageFinal = Pseudo + "_" + Message + "_" + Timestamp;
		    	  System.out.println(messageFinal);
		    	  listeMessages.add(messageFinal);
		      }
		    } catch (SQLException e) {
		    	e.printStackTrace();
		    }
		
		return listeMessages;
	}
	

}


