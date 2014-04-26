

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*; 

import javax.servlet.http.*; 


public class E1Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	    // data members 
	    protected String name; 
	    protected String date; 
	    protected String time; 
	    protected String product; 
	    protected String quantity; 
	
	    //protected java.sql.Date courseDate; 
	    
	    protected final String CR = "\n";     // carriage return 

	    // constructors 
	    public E1Servlet() 
	    { 
	    } 

	    public E1Servlet(HttpServletRequest request) 
	    { 
	        name = request.getParameter("name"); 
	        date = request.getParameter("date"); 
	        time = request.getParameter("time"); 
	        product = request.getParameter("product"); 
	        quantity = request.getParameter("quantity"); 
	     
	    } 

	    public E1Servlet(ResultSet dataResultSet) 
	    { 

	        try { 
	            // assign data members 
	            name = dataResultSet.getString("name"); 
	            date = dataResultSet.getString("date"); 
	            time = dataResultSet.getString("time"); 
	            product = dataResultSet.getString("product"); 
	            quantity = dataResultSet.getString("quantity"); 
	      
	        } 
	        catch (SQLException e) 
	        { 
	            e.printStackTrace(); 
	        } 
	    } 

	    //  accessors 
	    public String getName() 
	    { 
	        return name; 
	    } 

	    public String getDate() 
	    { 
	        return date; 
	    } 

	    public String getTime() 
	    { 
	        return time; 
	    } 

	    public String getProduct() 
	    { 
	        return product; 
	    } 

    public String getQuantity() 
	    { 
	        return quantity; 
	    } 

	    
	  
	    //  methods 
	    //  normal text string representation 
	    public String toString() 
	    { 
	        String replyString = ""; 

	        replyString += "Name: " + name  + CR; 
	        replyString += "Date: " + date  + CR;
	        replyString += "time: " + time  + CR; 
	        replyString += "product: " + product + CR; 
	        replyString += "Quantity " + quantity + CR; 
	      

	        return replyString; 
	    } 

	    //  returns data as HTML formatted un-ordered list 
	    public String toWebString() 
	    { 

	        String replyString = "<ul>"; 

	        replyString += "<li><B>Name:</B> " + name + ", " + date + CR; 
	        replyString += "<li><B>Date:</B> " + date  + CR;  
	        replyString += "<li><B>time:</B> " + time  + CR; 
	        replyString += "<li><B>product:</B> " + product + CR; 
	        replyString += "<li><B>quantity:</B> " + quantity + CR; 
	         
	        
	        replyString += "</ul>" + CR; 




	        return replyString; 
	    } 

	    // returns data formatted for an HTML table row 
	    public String toTableString(int rowNumber) 
	    { 
	        String replyString = ""; 
	        String tdBegin = "<td>"; 
	        String tdEnd = "</td>" + CR; 

	        replyString += "<tr>" + CR; 
	        replyString += tdBegin + rowNumber + tdEnd; 
	        replyString += tdBegin + name + tdEnd; 
	        replyString += tdBegin  + date + tdEnd;
	        replyString += tdBegin + time + tdEnd;
	        replyString += tdBegin  + product + tdEnd;
	        replyString += tdBegin + quantity + tdEnd; 
	        replyString += "</tr>" + CR; 

	        return replyString; 
	    } 
	}
	 