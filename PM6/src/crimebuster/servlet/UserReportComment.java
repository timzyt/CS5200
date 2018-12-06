package crimebuster.servlet;
import crimebuster.dal.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crimebuster.dal.PersonDao;
import crimebuster.model.Person;

/**
 * Servlet implementation class FindPerson
 */
@WebServlet("/findperson")
public class UserReportComment extends HttpServlet {
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
	      
	      List<Person> persons = new ArrayList<Person>();
	      String firstName = request.getParameter("firstname");
	      if (firstName == null || firstName.trim().isEmpty()) {
	    	  	messages.put("success", "Please enter a valid name.");
	      } else {
	    	  try {
	    		persons = personDao.getPersonsFromFirstName(firstName);
	    	  } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
			}
	    	 	messages.put("success", "Displaying results for " + firstName);
	    		messages.put("previousFirstName", firstName);
	      }
	      request.setAttribute("previousFirstName", firstName);
	      
	      request.getRequestDispatcher("/FindPerson.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Map<String, String> messages = new HashMap<String, String>();
	     request.setAttribute("messages", messages);
	     
	     List<Person> persons = new ArrayList<>();
	     String firstName = request.getParameter("firstname");
	        if (firstName == null || firstName.trim().isEmpty()) {
	            messages.put("success", "Please enter a valid name.");
	        } else {
	        	// Retrieve BlogUsers, and store as a message.
	        	try {
	            	persons = personDao.getPersonsFromFirstName(firstName);
	            } catch (SQLException e) {
	    			e.printStackTrace();
	    			throw new IOException(e);
	            }
	        	messages.put("success", "Displaying results for " + firstName);
	        }
	        request.setAttribute("persons", persons);
	        
	        request.getRequestDispatcher("/FindPerson.jsp").forward(request, response);
	}

}
