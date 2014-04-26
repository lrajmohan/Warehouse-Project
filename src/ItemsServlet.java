

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*; 

import javax.servlet.http.*; 


 
public class ItemsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	    // data members 
	    protected String Name; 
	    protected String Description; 
	    protected String Measure; 
	    protected String Volume; 
	    protected String courseTitle; 
	    protected String courseLocation; 
	    protected String expectations; 
	    protected String image; 
	    protected String courseDate;
	    //protected java.sql.Date courseDate; 
	    
	    protected final String CR = "\n";     // carriage return 

	    // constructors 
	    public ItemsServlet() 
	    { 
	    } 

	    public ItemsServlet(HttpServletRequest request) 
	    { 
	        Name = request.getParameter("Name"); 
	        Description = request.getParameter("Description"); 
	        Volume = request.getParameter("Measure"); 
	        Measure = request.getParameter("Volume"); 


	    } 

	    public ItemsServlet(ResultSet dataResultSet) 
	    { 

	        try { 
	            // assign data members 
	            Name = dataResultSet.getString("name"); 
	            Description = dataResultSet.getString("description"); 
	            Volume = dataResultSet.getString("unitofmeasure"); 
	            Measure = dataResultSet.getString("volume"); 
	       
	        } 
	        catch (SQLException e) 
	        { 
	            e.printStackTrace(); 
	        } 
	    } 

	    //  accessors 
	    public String getName() 
	    { 
	        return Name; 
	    } 

	    public String getDescription() 
	    { 
	        return Description; 
	    } 

	    public String getMeasure() 
	    { 
	        return Volume; 
	    } 

	    public String getVolume() 
	    { 
	        return Measure; 
	    } 

	   
	    //  methods 
	    //  normal text string representation 
	    public String toString() 
	    { 
	        String replyString = ""; 

	        replyString += "Name: " + Name + CR; 
	        replyString += "Decription: " + Description + CR; 
	        replyString += "Measure: " + Measure  + CR; 
	        replyString += "Volume " + Volume + CR; 
	

	        return replyString; 
	    } 

	    //  returns data as HTML formatted un-ordered list 
	    public String toWebString() 
	    { 

	        String replyString = "<ul>"; 

	        replyString += "<li><B>Name:</B> " + Name  + CR;
	        replyString += "<li><B>Description:</B> " + Description  + CR;
	        replyString += "<li><B>Measure:</B> " + Measure  + CR;
	        replyString += "<li><B>Volume:</B> " + Volume + CR; 
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
	        replyString += tdBegin + Name  + tdEnd; 	
	        replyString += tdBegin + Description + tdEnd;
	        replyString += tdBegin + Measure + tdEnd; 
	        replyString += tdBegin + Volume + tdEnd;
	        
	        replyString += "</tr>" + CR; 

	        return replyString; 
	    } 
	}
	 