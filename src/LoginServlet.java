

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*; 
import java.io.*; 

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected Connection dbConnection; 
    protected PreparedStatement displayStatement; 
    protected PreparedStatement registerStatement; 
 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/items","root","root");
			System.out.println("LoginServlet success");
			
			String username = request.getParameter("username");
			String pwd = request.getParameter("password");
			String utype = request.getParameter("usertype");
			System.out.println("Name"+username);
			

			
			//Scanner in = new Scanner(System.in);
			/*System.out.println("Enter new user name");
			String newuser= in.nextLine();
			System.out.println("Enter the password");
			String newpwd = in.nextLine();*/
			
			String searchQuery="select * from login where username='"+username+"' AND password='"+pwd+"'";
			//String searchQuery= "insert into t values ('"+newuser+"','"+newpwd+"')";
			//"INSERT INTO course" + "VALUES (course_code, course_desc, course_chair)";
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery(searchQuery);
			//stmt.executeUpdate(searchQuery);
			
			while(rs.next()){
				String dbUser = rs.getString("username");
				String dbPassword= rs.getString("password");
				String dbusertype= rs.getString("usertype");
				System.out.println(dbusertype);
				boolean entrance;
				entrance=false;

				if ((username.equals(dbUser)) && (pwd.equals(dbPassword)) && (dbusertype.equals("admin")) ){
					request.getRequestDispatcher("index.html").include(request, response);
					//entrance=true;
				}
				else if((username.equals(dbUser)) && (pwd.equals(dbPassword)) && (dbusertype.equals("driver"))) {
				//entrance=false;
					request.getRequestDispatcher("index1.html").include(request, response); 
				}
				
				else
				{
					
				}
				/*if (entrance==true){
				
			//		request.getRequestDispatcher("/StudentRegistration.html").include(request, response);
					request.getRequestDispatcher("index.html").include(request, response);
					}
				else{
				}
				request.getRequestDispatcher("index1.html").include(request, response); */
				}
			
		} 
		catch (ClassNotFoundException e) {
		System.err.println("Couldn't find the mm " + "database driver: "+ e.getMessage());
		} 
		/*catch (InstantiationException e) {
		System.err.println(e.getMessage());
		} catch (IllegalAccessException e) {
		System.err.println(e.getMessage());
		} */
		catch (SQLException e) {
		System.err.println("SQL problem: " + e.getMessage());
		System.err.println("SQL state: " + e.getSQLState());
		System.err.println("Vendor error: " + e.getErrorCode());
		} 
		/*finally {
		try {
		if (con != null) {
			con.close();
		}
		} catch (SQLException e) {
		System.err.println(e.getMessage());
		}
		}*/
		
	}

}
