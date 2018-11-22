package crimebuster.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crimebuster.dal.PersonDao;
import crimebuster.model.Person;

/**
 * Servlet implementation class PersonDelete
 */
@WebServlet("/persondelete")
public class PersonDelete extends HttpServlet {
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
        messages.put("title", "Delete Person");
        request.getRequestDispatcher("/PersonDelete.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Map<String, String> messages = new HashMap<String, String>();
	     request.setAttribute("messages", messages);
	     
	     String userName = request.getParameter("username");
	     String password = request.getParameter("password");
	     if (userName == null || userName.trim().isEmpty()) {
	    	 	messages.put("title", "Invalid UserName");
	    	 	messages.put("disableSubmit", "true");
	     } else {
	    	 	Person person = new Person(userName, password);
	    	 	try {
	    	 		person = personDao.delete(person);
	    	 		if (person == null) {
		    	 		messages.put("title", "Successfully deleted " + userName);
		    	 		messages.put("diasableSubmit", "false");
		    	 	} else {
		    	 	 	messages.put("title", "Failed to delete " + userName);
			        	messages.put("disableSubmit", "false");
		    	 	}
	    	 	} catch (SQLException e) {
					e.printStackTrace();
					throw new IOException(e);
				}
	    	 	
	     }
	     request.getRequestDispatcher("/PersonDelete.jsp").forward(request, response);
	}

}
