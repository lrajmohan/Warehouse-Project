

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.*; 
import javax.servlet.http.*; 

import java.sql.*; 
import java.io.*; 


public class Bill extends HttpServlet {
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
	    protected final int DATE_POSITION = 2; 
	    protected final int TIME_POSITION      = 3; 
	    protected final int PRODUCT_POSITION    = 4; 
	    protected final int QUANTITY_POSITION = 5; 
	    protected final int EMAIL_POSITION = 6; 

	    public void init(ServletConfig config) throws ServletException 
	    { 
	        super.init(config); 

	        // use println statements to send status messages to Web server console 
	        try { 
	            System.out.println("Bill init: Start"); 

		   	     
	            System.out.println("Bill init: Loading Database Driver"); 
	            Class.forName("com.mysql.jdbc.Driver"); 

	            System.out.println("Bill init: Getting a connection to - " + dbURL); 
	            dbConnection = DriverManager.getConnection(dbURL, userID, passwd); 

	            System.out.println("Bill init: Preparing display statement"); 
	            displayStatement = 
	               dbConnection.prepareStatement("select * from lading "); 

	            System.out.println("Bill init: Preparing register statement"); 
	            registerStatement = 
	               dbConnection.prepareStatement("insert into lading " 
	+ "(name, date, time, product, quantity, email)" 
	                 + " values (?, ?, ?, ?, ?, ?)"); 
	            
	            System.out.println("insert query"+ registerStatement);

	            System.out.println("Bill init: End"); 
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

	        userOption = request.getParameter("submit"); 

	        if (userOption != null) 
	        { 
	            // hidden form field "Register" was present 
	            registerBillServlet(request, response); 
	        } 
	        else 
	        { 
	            // simply display the BillServlets 
	            displayBillServlets(request, response); 
	        } 
	    } 

	    public void displayBillServlets(HttpServletRequest request, 
	                                HttpServletResponse response) 
	    { 
	        BillServlet aBillServlet = null; 

	        try { 
	            // build the html page heading 
	            String htmlHead = "<html><head><title>List of bill</title></head>" + CR; 

	            // build the html body 
	            String htmlBody = "<body><center>" + CR; 
	            htmlBody += "<h1>Bill List</h1>" + CR; 
	            

	            // build the table heading 
	            String tableHead = "<center><table border width=100% cellpadding=5>" + CR; 
	            tableHead += "<tr>" + CR; 
	            tableHead += "<th> </th>" + CR; 
	            tableHead += "<th>Driver Name</th>" + CR; 
	            tableHead += "<th>Date</th>" + CR; 
	            tableHead += "<th>Product</th>" + CR; 
	            tableHead += "<th>Time</th>" + CR;
	            tableHead += "<th>Quantity</th>" + CR;
	            tableHead += "<th>Email</th>" + CR;
	            tableHead += "</tr>" + CR; 

	            // execute the query to get a list of the BillServlets 
	            ResultSet dataResultSet = displayStatement.executeQuery(); 

	            // build the table body 
	            String tableBody = ""; 

	            int rowNumber = 1; 
	            while (dataResultSet.next()) 
	            { 
	                aBillServlet = new BillServlet(dataResultSet); 
	                tableBody += aBillServlet.toTableString(rowNumber); 
	                rowNumber++; 
	            } 

	            dataResultSet.close(); 

	            // build the table bottom 
	            String tableBottom = "</table></center>"; 

	            // build html page bottom 
	            String htmlBottom = "</body></html>"; 

	            // build complete html page 
	            htmlBody += tableHead + tableBody + tableBottom; 
	            
	          //  htmlBody += "<center><a href=/Warehouse/index.html>Return to Course Home Page</a>"; 
	            
	            htmlBody += "</center>"; 
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

	    public void registerBillServlet(HttpServletRequest request, 
	                                HttpServletResponse response) 
	    { 
	        try { 
	            // create a new BillServlet based on the form data 
	            BillServlet aBillServlet = new BillServlet(request); 

	            //
	            Connection conn;
		   	     conn=DriverManager.getConnection(dbURL,userID,passwd);
		   	     System.out.println("connection successfulll");
		   	     Statement editStatement=(Statement)conn.createStatement();
		   	     Statement modify=(Statement)conn.createStatement();
		   	 //
	            // set sql parameters 
	            registerStatement.setString(NAME_POSITION, aBillServlet.getName()); 
	            registerStatement.setString(DATE_POSITION, aBillServlet.getDate()); 
	            registerStatement.setString(TIME_POSITION, aBillServlet.getTime()); 
	            registerStatement.setString(PRODUCT_POSITION, aBillServlet.getProduct()); 
	            registerStatement.setString(QUANTITY_POSITION, aBillServlet.getQuantity());
	            registerStatement.setString(EMAIL_POSITION, aBillServlet.getEmail());
	          
	            if (!(aBillServlet.getQuantity() == ""))
	      		 {
	      	    	String itemname = aBillServlet.getProduct();
	      	    	String volume = aBillServlet.getQuantity();
	      	    	String querynew = "select volume from itemstable where name='"+itemname+"'";
	      	    	System.out.println("querynew"+ querynew);
	      	    	ResultSet rs1=(ResultSet)modify.executeQuery(querynew);
	      	     while(rs1.next()){
	      	    	
	      	    	 int vol = Integer.valueOf(rs1.getString("volume"));
	      	    	 System.out.println(vol);
	      	    	 vol = vol - Integer.valueOf(volume);
	      	    	String query1="update itemstable set volume = '"+vol+"' where name = '"+itemname+"'";
	      	    	System.out.println("quer1"+query1);
	      	    	//ResultSet rs2=(ResultSet)modify.executeUpdate(query1);
	      	      if(!(query1  == "null"))
	         	    {
	         	    	
	         	    	editStatement.executeUpdate(query1);
	         	    }
	         	    
	         	    else{
	         	    	editStatement.executeUpdate(querynew); 
	         	    }
	      	     }
	      		 }
	            registerStatement.executeUpdate(); 

	            // build confirmation page 
	String htmlPage = "<html><head><title>Confirmation Page</title></head>"; 

	htmlPage += "<body>"; 
	htmlPage += "<center><h1>Confirmation Page</h1></center><hr>"; 
	htmlPage += "The following information was entered successfully"; 
	htmlPage += aBillServlet.toWebString(); 

	htmlPage += "<hr>"; 
	htmlPage += "<center><a href=/Warehouse/index.html>Return to Home Page</a> | "; 
	htmlPage += "<a href=Bill>View List</a>"; 
	htmlPage += "<p><i>" + this.getServletInfo() + "</i>"; 
	htmlPage += "</center></body></html>"; 

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
	        System.out.println("Bill: destroy"); 
	        cleanUp(); 
	    } 

	    public String getServletInfo() 
	    { 
	        return "<i>Bill Servlet, v.06</i>"; 
	    } 
	} 
