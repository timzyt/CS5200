package crimebuster.servlet;

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

import crimebuster.dal.CrimeReportsDao;
import crimebuster.model.CrimeReports;

/**
 * Servlet implementation class FindReport
 */
@WebServlet("/findreport")
public class FindReport extends HttpServlet {
	public CrimeReportsDao crimeReportsDao;
       
	@Override
	public void init() throws ServletException {
		crimeReportsDao = crimeReportsDao.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
        request.setAttribute("messages", messages);
        
        List<CrimeReports> crimeReports = new ArrayList<>();
        String zipcode = request.getParameter("zipcodeId");
        if(zipcode == null || zipcode.trim().isEmpty()) {
        		messages.put("success", "please enter a valid zipcode");
        } else {
        		int zipcodeId = Integer.parseInt(zipcode);
        		try {
        			crimeReports = crimeReportsDao.getReportByZipcode(zipcodeId);
        		} catch (SQLException e) {
					e.printStackTrace();
					throw new IOException(e);
			}
        		messages.put("success", "Displaying results for " + zipcode);
        }
        request.setAttribute("crimeReports", crimeReports);
		request.getRequestDispatcher("/FindReport.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
        request.setAttribute("messages", messages);
        List<CrimeReports> crimeReports = new ArrayList<>();
        
        String zipcode = request.getParameter("zipcodeId");
        if(zipcode == null || zipcode.trim().isEmpty()) {
        	messages.put("success", "please enter a valid zipcode");
        } else {
        	int zipcodeId = Integer.parseInt(zipcode);
        	try {
        		crimeReports = crimeReportsDao.getReportByZipcode(zipcodeId);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        	messages.put("success", "Displaying results for " + zipcode);
        }
        request.setAttribute("crimeReports", crimeReports);
        request.getRequestDispatcher("/FindReports.jsp").forward(request, response);
	}

}
