

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.*; 
import javax.servlet.http.*; 

import java.sql.*; 
import java.io.*; 

public class Inventory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	    // data members 
	    protected Connection dbConnection; 
	    protected PreparedStatement displayStatement; 
	    protected PreparedStatement registerStatement; 

	    protected String dbURL = "jdbc:mysql://localhost:3306/items"; 
	    protected String userID = "root"; 
	    protected String passwd = "root"; 

	    protected String CR = "\n"; 

	    protected final int NAME_POSITION  = 1; 
	    protected final int DESCRIPTION_POSITION = 2; 
	    protected final int UNITOFMEASURE_POSITION  = 3; 
	    protected final int VOLUME_POSITION    = 4;
	    
	

	    public void init(ServletConfig config) throws ServletException 
	    { 
	        super.init(config); 

	        // use println statements to send status messages to Web server console 
	        try { 
	            System.out.println("ItemsServlet init: Start"); 

	            System.out.println("ItemsServlet init: Loading Database Driver"); 
	            Class.forName("com.mysql.jdbc.Driver"); 

	            System.out.println("ItemsServlet init: Getting a connection to - " + dbURL); 
	            dbConnection = DriverManager.getConnection(dbURL, userID, passwd); 

	            System.out.println("ItemsServlet init: Preparing display statement"); 
	            displayStatement = 
	               dbConnection.prepareStatement("select * from itemstable "); 

	  //          System.out.println("ItemsServlet init: Preparing register statement"); 
	  //          registerStatement = 
	  //             dbConnection.prepareStatement("insert into itemstable " 
//	+ "(name, description, unitofmeasure, volume)" 
	//                 + " values (?, ?, ?, ?)"); 

	            System.out.println("ItemsServlet init: End"); 
	        } 
	        catch (Exception e) 
	        { 
	            cleanUp(); 
	            e.printStackTrace(); 
	        } 
	    } 

	    public void service(HttpServletRequest request, 
	                        HttpServletResponse response) 
	           throws ServletException, IOException 
	    { 

	    	
	        String userOption = null; 

	        userOption = request.getParameter("Register"); 

	        if (userOption != null) 
	        { 
	            // hidden form field "Register" was present 
	          //  registerItemsServlet(request, response); 
	        } 
	        else 
	        { 
	        	
	            // simply display the items 
	            displayitems(request, response); 
	        } 
	    } 

	    public void displayitems(HttpServletRequest request, 
	                                HttpServletResponse response) 
	    { 
	        ItemsServlet aItemsServlet = null; 

	        try { 
	            // build the html page heading 
	            String htmlHead = "<html><head><title>List of items</title></head>" + CR; 

	            // build the html body 
	            String htmlBody = "<body><center>" + CR; 
	            htmlBody += "<h1>List of Items</h1>" + CR; 
	            

	            // build the table heading 
	            String tableHead = "<center><table border width=100% cellpadding=5>" + CR; 
	            tableHead += "<tr>" + CR; 
	            tableHead += "<th> </th>" + CR; 
	            tableHead += "<th>Name</th>" + CR; 
	            tableHead += "<th>Description</th>" + CR; 
	            tableHead += "<th>Volume</th>" + CR;
	            tableHead += "<th>UnitOfMeasure</th>" + CR;
	            tableHead += "</tr>" + CR; 

	            // execute the query to get a list of the items 
	            ResultSet dataResultSet = displayStatement.executeQuery(); 

	            // build the table body 
	            String tableBody = ""; 

	            int rowNumber = 1; 
	            while (dataResultSet.next()) 
	            { 
	                aItemsServlet = new ItemsServlet(dataResultSet); 
	                tableBody += aItemsServlet.toTableString(rowNumber); 
	                rowNumber++; 
	            } 

	            dataResultSet.close(); 

	            // build the table bottom 
	            String tableBottom = "</table></center>"; 

	            // build html page bottom 
	            String htmlBottom = "</body></html>"; 

	            // build complete html page 
	            htmlBody += tableHead + tableBody + tableBottom; 
	            htmlBody +="<br>";
	            htmlBody += "<center><a href=/Warehouse/index.html>Return to Home Page</a>"; 
	             
	            String htmlPage = htmlHead + htmlBody + htmlBottom; 

	            // now let's send this dynamic data 
	            // back to the browser 
	            PrintWriter outputToBrowser =  new PrintWriter(response.getOutputStream()); 
	            response.setContentType("text/html"); 
	            outputToBrowser.println(htmlPage); 
	            outputToBrowser.close(); 

	        } 
	        catch (Exception e) 
	        { 
	            cleanUp(); 
	            e.printStackTrace(); 
	        } 
	    } 

	  

	    public void cleanUp() 
	    { 
	        try { 
	            System.out.println("Closing database connection"); 
	            dbConnection.close(); 
	        } 
	        catch (SQLException e) 
	        { 
	            e.printStackTrace(); 
	        } 
	    } 

	    public void destroy() 
	    { 
	        System.out.println("ItemsServlet: destroy"); 
	        cleanUp(); 
	    } 

	    public String getServletInfo() 
	    { 
	        return "<i>ItemsServlet Registration Servlet, v.06</i>"; 
	    } 
	} 
