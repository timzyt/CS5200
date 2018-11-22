package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dal.PersonDao;
import model.Person;

/**
 * Servlet implementation class PersonCreate
 */
@WebServlet("/personcreate")
public class PersonCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected PersonDao personDao;
       
   @Override
   public void init() throws ServletException {
	   personDao = PersonDao.getInstance();
   }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		request.setAttribute("messages", messages);
		// render the JSP
		request.getRequestDispatcher("/PersonCreate.jsp").forward(request,  response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Map for storing messages
	Map<String, String> messages = new HashMap<>();
	request.setAttribute("messages", messages);
	
	String userName = request.getParameter("username");
	if (userName == null || userName.trim().isEmpty()) {
		messages.put("success", "Invalid UserName");
	} else {
		// Create a Person
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		
		try {
			Person person = new Person(userName, firstName, lastName, password, email, phone);
			person = personDao.create(person);
			messages.put("success", "Successfully created " + userName);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
	}
	
	request.getRequestDispatcher("/PersonCreate.jsp").forward(request, response);
	
	}

}
