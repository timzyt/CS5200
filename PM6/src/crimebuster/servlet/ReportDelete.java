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

import crimebuster.dal.CrimeReportsDao;
import crimebuster.model.CrimeReports;

/**
 * Servlet implementation class ReportDelete
 */
@WebServlet("/reportdelete")
public class ReportDelete extends HttpServlet {
	public CrimeReportsDao crimeReportsDao;
	
   @Override
   public void init() throws ServletException {
	   crimeReportsDao = CrimeReportsDao.getInstance();
   }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Map<String, String> messages = new HashMap<String, String>();
	     request.setAttribute("messages", messages);
	     messages.put("title", "Delete Report");
	     request.getRequestDispatcher("/ReportDelete.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  Map<String, String> messages = new HashMap<String, String>();
	      request.setAttribute("messages", messages);
	      
	      String report = request.getParameter("reportId");
	      if(report == null || report.trim().isEmpty()) {
	    	  	messages.put("title", "Invalid ReportId");
	        messages.put("disableSubmit", "true");
	      } else {
	    	  	long reportId = Long.parseLong(report);  	  	
	    	  	try {
	    	  		CrimeReports crimeReport = new CrimeReports(reportId);
	    	  		crimeReport = crimeReportsDao.delete(crimeReport);
	    	  		if(crimeReport == null) {
	    	  		   messages.put("title", "Successfully deleted " + reportId);
			       messages.put("disableSubmit", "true");
	    	  		} else {
	    	  			messages.put("title", "Failed to delete " + crimeReport);
			        	messages.put("disableSubmit", "false");
	    	  		}
	    	  	} catch (SQLException e) {
					e.printStackTrace();
					throw new IOException(e);
				}
	      }
	     
	      request.getRequestDispatcher("/ReportDelete.jsp").forward(request, response);
	}

}
