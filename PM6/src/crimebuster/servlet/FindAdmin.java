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

import crimebuster.dal.AdminDao;
import crimebuster.model.Admin;

/**
 * Servlet implementation class FindAdmin
 */
@WebServlet("/findadmin")
public class FindAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected AdminDao adminDao;  
    
	@Override
	public void init() throws ServletException {
		adminDao = AdminDao.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  Map<String, String> messages = new HashMap<String, String>();
	      request.setAttribute("messages", messages);
	      
	      List<Admin> admins = new ArrayList<Admin>();
	      String firstName = request.getParameter("firstname");
	      if (firstName == null || firstName.trim().isEmpty()) {
	    	  	messages.put("success", "Please enter a valid name.");
	      } else {
	    	  try {
	    		admins = adminDao.getAdminFromFirstName(firstName);
	    	  } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
			}
	    	 	messages.put("success", "Displaying results for " + firstName);
	    		messages.put("previousFirstName", firstName);
	      }
	      request.setAttribute("previousFirstName", firstName);
	      
	      request.getRequestDispatcher("/FindAdmin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Map<String, String> messages = new HashMap<String, String>();
	     request.setAttribute("messages", messages);
	     
	     List<Admin> admins = new ArrayList<>();
	     String firstName = request.getParameter("firstname");
	        if (firstName == null || firstName.trim().isEmpty()) {
	            messages.put("success", "Please enter a valid name.");
	        } else {
	        	// Retrieve BlogUsers, and store as a message.
	        	try {
	            	admins = adminDao.getAdminFromFirstName(firstName);
	            } catch (SQLException e) {
	    			e.printStackTrace();
	    			throw new IOException(e);
	            }
	        	messages.put("success", "Displaying results for " + firstName);
	        }
	        request.setAttribute("admins", admins);
	        
	        request.getRequestDispatcher("/FindAdmin.jsp").forward(request, response);
	}

}
