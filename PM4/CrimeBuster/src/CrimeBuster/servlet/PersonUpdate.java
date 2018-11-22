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
 * Servlet implementation class PersonUpdate
 */
@WebServlet("/personupdate")
public class PersonUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
    protected PersonDao personDao; 
    
    @Override
    public void init() throws ServletException {
    		personDao = PersonDao.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
        request.setAttribute("messages", messages);
        String userName = request.getParameter("username");
        if (userName == null || userName.trim().isEmpty()) {
        		messages.put("success", "Please enter a valid UserName.");
        } else {
        	
        	try {
        		Person person = personDao.getPersonFromUserName(userName);
        		if (person == null) {
        			messages.put("success", "UserName does not exist");
        		}
        		request.setAttribute("person", person);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        }
        request.getRequestDispatcher("/PersonUpdate.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
        request.setAttribute("messages", messages);
        String userName = request.getParameter("username");
        
        if(userName == null || userName.trim().isEmpty()) {
        	  messages.put("success", "Please enter a valid UserName.");
        } else {
        	try {
			Person person = personDao.getPersonFromUserName(userName);
			if (person == null) {
				messages.put("success", "UserName does not exist. No update to perform.");
			} else {
				String newFirstName = request.getParameter("firstname");
				String newLastName = request.getParameter("lastname");
				String newPassword = request.getParameter("password");
				String newEmail = request.getParameter("email");
				String newPhone = request.getParameter("phone");
				if (newFirstName == null || newFirstName.trim().isEmpty()) {
    	            messages.put("success", "Please enter a valid FirstName.");
				}
				if (newLastName == null || newLastName.trim().isEmpty()) {
    	            messages.put("success", "Please enter a valid LastName.");
				}
    	            if (newPassword == null || newPassword.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid Password.");
    	            }
        	        	if (newEmail == null || newEmail.trim().isEmpty()) {
       	            messages.put("success", "Please enter a valid Email.");
        	        	}
            	    	if (newPhone == null || newPhone.trim().isEmpty()) {
                    messages.put("success", "Please enter a valid Phone.");
            	    	}
            	    	person = personDao.updateByUserName(person, newFirstName, newLastName, newPassword, newEmail, newPhone);
            	    	messages.put("success", "Successfully updated " + userName);
			}
			request.setAttribute("person", person);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        }
        request.getRequestDispatcher("/PersonUpdate.jsp").forward(request, response);
	}

}
