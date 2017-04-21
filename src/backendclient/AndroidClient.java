package backendclient;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;

import javax.ws.rs.core.NewCookie;

import workers.MapWorker;
import workers.ReduceWorker;
import master.Master;
import model.*;
// .abcdefghijklmnopqrstuvwxyz
public class AndroidClient {
	 
	public static void main(String[] args){
		Master master = new Master();
		//appendLocation(master.askGoogleDirectionsAPI("41.672690","-72.716124","41.677929","-72.853233").getDirs());
		//new ReducerClient(922, 52).start();
		MapWorker mapWorker = new MapWorker();
		//mapWorker.map();
		ReduceWorker reduceWorker = new ReduceWorker(mapWorker.map());
		System.out.println(reduceWorker.reduce(reduceWorker.getReducedDirections()).getEndlat());
		//Directions dirs= master.askGoogleDirectionsAPI("33.81","-117.91","34.13","-118.35");
		System.out.println();
		
	}
	
	
	private static void appendLocation(String response) {		
		final File currentFilePath = new File(AndroidClient.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath());
		
		String DBFOLDER = "file:///"+ currentFilePath.getParentFile().getParentFile()+ File.separator+"dbsnfiles"+File.separator+"directs.txt";
		URL myUri = null;
		try {
			myUri = new URL(DBFOLDER);
			System.out.println(myUri.toString());
			Files.write(Paths.get(new URI(myUri.getProtocol(), myUri.getHost(), myUri.getPath(), myUri.getQuery(), null)), response.getBytes(), StandardOpenOption.APPEND);
      
	    } catch (Exception e) {
	      //System.out.println(e.getMessage());
	      //if(myUri!=null)System.out.println(myUri.toString());
	    }
		
	}
	
	
	/*
	 * FIXME: Separate to 3 functions connect, insert data and query
	 */
	private static void createandaddcols(String directs){
		String hostName = "demodbs";
	      String dbName = "demojava";
	      String user = "amoraitis";
	      String password = "Tasosmor#7";
	      String url = String.format("jdbc:sqlserver://%s.database.windows.net:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
	      System.out.println(url);
	      Connection connection = null;

	      try {
	              connection = DriverManager.getConnection(url);
	              System.out.println("Query data example:");
	              System.out.println("=========================================");
	              String tableName = "dirs";
	              // Create and execute a SELECT SQL statement.
	              String newtableSql = "CREATE TABLE"
	                  + " " +tableName +" (\n"
	  	                + "	id integer PRIMARY KEY,\n"
	  	                + "	latlon text NOT NULL,\n"
	  	                + "	directions text NOT NULL\n"
	  	                + ");";
	              //
	              String insertDataSql = "INSERT INTO "+tableName+"(id,latlon,directions) VALUES(?,?,?)";
	              
	              System.out.println(insertDataSql);

	              try (/*Statement statement = connection.createStatement()*/
	            		  PreparedStatement pstmt = connection.prepareStatement(insertDataSql)) {
	            	  //statement.execute(newtableSql);
	            	  pstmt.setInt(1, 6);
	            	  pstmt.setString(2, "me");
	                  pstmt.setString(3, directs);
	                  pstmt.executeUpdate();
	              } 
	          }
	      catch (Exception e) {
	    	  e.printStackTrace();
	          System.out.println(e.getMessage());
	      }finally {
	    	  try {
	              if (connection != null) {
	                  connection.close();
	                  connection=null;
	              }
	          } catch (SQLException ex) {
	              System.out.println(ex.getMessage());
	          }
	      }
		
		
	}
	
}
