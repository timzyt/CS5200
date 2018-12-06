package crimebuster.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import crimebuster.dal.UsersDao;
import crimebuster.model.CrimeReports;
import crimebuster.model.Users;
import crimebuster.model.*;

public class CrimeReportsDao {
	protected ConnectionManager connectionManager;
	
	private static CrimeReportsDao instance = null;
	protected CrimeReportsDao() {
		connectionManager = new ConnectionManager();
	}
	public static CrimeReportsDao getInstance() {
		if (instance == null) {
			instance = new CrimeReportsDao();
		}
		return instance;
	}
	
	public CrimeReports create(CrimeReports crimeReport) throws SQLException{
		String insertCrimeReport = 
				"INSERT INTO CrimeReports(reportId, userName, occurredTimeStamp, reportedTimeStamp, initialCallTypeId, finalCallType, beat, neighborhood, zipcode) "
				+ "VALUES(?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCrimeReport, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setLong(1, crimeReport.getReportId());
			insertStmt.setString(2, crimeReport.getUser().getUserName());
			insertStmt.setTimestamp(3, new Timestamp(crimeReport.getOccurredTimeStamp().getTime()));
			insertStmt.setTimestamp(4, new Timestamp(crimeReport.getReportedTimeStamp().getTime()));
			insertStmt.setString(5, crimeReport.getInitialCallType().getPrimaryOffenseDescription());
			insertStmt.setString(6, crimeReport.getInitialCallType().getPrimaryOffenseDescription());
			insertStmt.setString(7, crimeReport.getBeat().getBeat());
			insertStmt.setString(8, crimeReport.getNeighborhood().getNeighborhoodName());
			insertStmt.setString(9, crimeReport.getZipcode().getZipcode());
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			long reportId = -1;
			if (resultKey.next()) {
				reportId = resultKey.getLong(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			crimeReport.setReportId(reportId);
			return crimeReport;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	/**
	 * 
	 * Delete the crimeReports instance.
	 * This runs a DELETE statement.
	 */
	public CrimeReports delete(CrimeReports crimeReport) throws SQLException {
		String deleteCrimeReport = "DELETE FROM CrimeReports WHERE reportId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCrimeReport);
			deleteStmt.setLong(1, crimeReport.getReportId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the BlogPosts instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
	/**
	 * Get the CrimeReport record by report id.
	 * @param reportId
	 * @return CrimeReport
	 * @throws SQLException
	 */
	public CrimeReports getReportById(long reportId) throws SQLException {
		String selectCrimeReport =
			"SELECT ReportId, UserName, OccurredTimeStamp, ReportedTimeStamp, InitialCallType, FinalCallType, Beat, NeighborhoodName, Zipcode " +
			"FROM CrimeReports " +
			"WHERE ReportId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCrimeReport);
			selectStmt.setLong(1, reportId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				long resultReportId = results.getLong("ReportId");
				String userName = results.getString("UserName");
				Date occurredTimeStamp = new Date(results.getTimestamp("OccurredTimeStamp").getTime());
				Date reportedTimeStamp = new Date(results.getTimestamp("ReportedTimeStamp").getTime());
				int initialCallTypeId = results.getInt("InitialCallType");
				int finalCallTypeId = results.getInt("FinalCallType");
				int beatId = results.getInt("BeatId");
				int neighborhoodId = results.getInt("Neighborhood");
				int zipcodeId = results.getInt("Zipcode");		
				
				Users user = usersDao.getUserFromUserName(userName);
				CrimeCategory initialCallType = new CrimeCategory(initialCallTypeId);
				CrimeCategory finalCallType = new CrimeCategory(finalCallTypeId);
				BeatSector beatSector = new BeatSector(beatId);
				Neighborhood neighborhood = new Neighborhood(neighborhoodId);
				Zipcode zipcode = new Zipcode(zipcodeId);
				CrimeReports crimeReport = new CrimeReports(resultReportId, user, occurredTimeStamp, reportedTimeStamp, initialCallType, finalCallType, beatSector, neighborhood, zipcode);
				return crimeReport;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	
	/**
	 * Get the CrimeReport record by userName.
	 * @param userName
	 * @return CrimeReport
	 * @throws SQLException
	 */	
	public List<CrimeReports> getReportByUserName(String userName) throws SQLException {
		List<CrimeReports> crimeReports = new ArrayList<CrimeReports>();
		String selectCrimeReport =
			"SELECT ReportId, UserName, OccurredTimeStamp, ReportedTimeStamp, InitialCallType, FinalCallType, Beat, NeighborhoodName, Zipcode " +
			"FROM CrimeReports " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCrimeReport);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				Long reportId = results.getLong("ReportId");
				String resultUserName = results.getString("UserName");
				Date occurredTimeStamp = new Date(results.getTimestamp("OccurredTimeStamp").getTime());
				Date reportedTimeStamp = new Date(results.getTimestamp("ReportedTimeStamp").getTime());
				int initialCallTypeId = results.getInt("InitialCallType");
				int finalCallTypeId = results.getInt("FinalCallType");
				int beatId = results.getInt("BeatId");
				int neighborhoodId = results.getInt("Neighborhood");
				int zipcodeId = results.getInt("Zipcode");		
				
				Users user = usersDao.getUserFromUserName(resultUserName);
				CrimeCategory initialCallType = new CrimeCategory(initialCallTypeId);
				CrimeCategory finalCallType = new CrimeCategory(finalCallTypeId);
				BeatSector beatSector = new BeatSector(beatId);
				Neighborhood neighborhood = new Neighborhood(neighborhoodId);
				Zipcode zipcode = new Zipcode(zipcodeId);
				CrimeReports crimeReport = new CrimeReports(reportId, user, occurredTimeStamp, reportedTimeStamp, initialCallType, finalCallType, beatSector, neighborhood, zipcode);
				crimeReports.add(crimeReport);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return crimeReports;
	}
	
		public List<CrimeReports> getReportByZipcode(int zipcodeId) throws SQLException {
			List<CrimeReports> crimeReports = new ArrayList<CrimeReports>();
			String selectCrimeReport =
				"SELECT ReportId, UserName, OccurredTimeStamp, ReportedTimeStamp, InitialCallType, FinalCallType, Beat, NeighborhoodName, Zipcode " +
				"FROM CrimeReports " +
				"WHERE zipcodeId=?;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectCrimeReport);
				selectStmt.setInt(1, zipcodeId);
				results = selectStmt.executeQuery();
				UsersDao usersDao = UsersDao.getInstance();
				if(results.next()) {
					Long reportId = results.getLong("ReportId");
					String userName = results.getString("UserName");
					Date occurredTimeStamp = new Date(results.getTimestamp("OccurredTimeStamp").getTime());
					Date reportedTimeStamp = new Date(results.getTimestamp("ReportedTimeStamp").getTime());
					int initialCallTypeId = results.getInt("InitialCallType");
					int finalCallTypeId = results.getInt("FinalCallType");
					int beatId = results.getInt("BeatId");
					int neighborhoodId = results.getInt("Neighborhood");
					int resultZipcodeId = results.getInt("ZipcodeId");		
					
					Users user = usersDao.getUserFromUserName(userName);
					CrimeCategory initialCallType = new CrimeCategory(initialCallTypeId);
					CrimeCategory finalCallType = new CrimeCategory(finalCallTypeId);
					BeatSector beatSector = new BeatSector(beatId);
					Neighborhood neighborhood = new Neighborhood(neighborhoodId);
					Zipcode zipCode = new Zipcode(resultZipcodeId);
					CrimeReports crimeReport = new CrimeReports(reportId, user, occurredTimeStamp, reportedTimeStamp, initialCallType, finalCallType, beatSector, neighborhood, zipCode);
					crimeReports.add(crimeReport);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(connection != null) {
					connection.close();
				}
				if(selectStmt != null) {
					selectStmt.close();
				}
				if(results != null) {
					results.close();
				}
			}
		return crimeReports;
	}
}
