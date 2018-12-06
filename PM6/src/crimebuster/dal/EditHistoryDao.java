package crimebuster.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import crimebuster.model.*;

public class EditHistoryDao {
	protected ConnectionManager connectionManager;
	
	private static EditHistoryDao instance = null;
	protected EditHistoryDao() {
		connectionManager = new ConnectionManager();
	}
	public static EditHistoryDao getInstance() {
		if (instance == null) {
			instance = new EditHistoryDao();
		}
		return instance;
	}
	
	public EditHistory create(EditHistory editHistory) throws SQLException{
		String insertEditHistory = 
				"INSERT INTO EditHistory(editHistoryId, userName, reportId, editTime, editComment) "
				+ "VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertEditHistory, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, editHistory.getEditHistoryId());
			insertStmt.setString(2, editHistory.getUser().getUserName());
			insertStmt.setTimestamp(3, new Timestamp(editHistory.getEditTime().getTime()));
			insertStmt.setString(4, editHistory.getEditComment());
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int editHistoryId = -1;
			if (resultKey.next()) {
				editHistoryId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			editHistory.setEditHistoryId(editHistoryId);
			return editHistory;
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
}
