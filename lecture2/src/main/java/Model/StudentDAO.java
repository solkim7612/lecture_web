package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DBConnection.DBConnection;

public class StudentDAO {

	
	
 
    public boolean isValidUser(int studentId, String password) {
    	
        String SQL_SELECT_USER = "SELECT * FROM Student WHERE student_id = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(SQL_SELECT_USER)) {

            pst.setInt(1, studentId);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    //
    
}