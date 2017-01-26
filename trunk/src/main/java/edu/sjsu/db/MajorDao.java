package edu.sjsu.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.sjsu.utils.DBUtils;
import edu.sjsu.models.*;

public class MajorDao {
	public static boolean storeMajor(String majorName, String deanEmail, String location, String phone){
		try{
			Connection conn = DBUtils.getConn();
			if(conn == null) return false;
			String sqlstmt = "INSERT INTO `major` (`MajorName`,`DeanEmail`, `Location`, `Phone`) " +
							 "VALUES (? ,?, ?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sqlstmt);
			pstmt.setString(1, majorName);
			pstmt.setString(2, deanEmail);
			pstmt.setString(3, location);
			pstmt.setString(4, phone);
			pstmt.executeUpdate();
			conn.close();
			return true;
		}catch (SQLException se){
			se.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static Major getMajor(int majorID){
		try{
			Connection conn = DBUtils.getConn();
			String sqlstmt = "SELECT * FROM `major` WHERE `MajorID` = ?";
			PreparedStatement pstmt = conn.prepareStatement(sqlstmt);
			pstmt.setInt(1, majorID);
			ResultSet result = pstmt.executeQuery();
			if(!result.next()){
				conn.close();
				return null;
			} else {
				Major newMajor = new Major(majorID, result.getString("MajorName"), result.getString("DeanEmail"), result.getString("Location"), result.getString("Phone"));
				conn.close();
				return newMajor;
			}
		} catch (SQLException se){
			se.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static String getMajorString(int majorID){
		try{
			Connection conn = DBUtils.getConn();
			String sqlstmt = "SELECT * FROM `major` WHERE `MajorID` = ?";
			PreparedStatement pstmt = conn.prepareStatement(sqlstmt);
			pstmt.setInt(1, majorID);
			ResultSet result = pstmt.executeQuery();
			if(!result.next()){
				conn.close();
				return null;
			} else {
				String majorString = "MajorID " + majorID + " Major Name " + result.getString("MajorName") + 
						" Dean Email " + result.getString("DeanEmail") + " Location " +result.getString("Location") +
						" Phone " + result.getString("Phone");
				conn.close();
				return majorString;
			}
		} catch (SQLException se){
			se.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean setMajorAdvisor(Advisor advisor, int majorID){
		try{
			Connection conn = DBUtils.getConn();
			String sqlstmt = "Update `major` SET `DeanEmail` = ? WHERE `MajorID` = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlstmt);
			if(advisor == null){
				pstmt.setString(1, null);
			}else{
				pstmt.setString(1, advisor.getEmail());
			}
			pstmt.setInt(2, majorID);
			pstmt.executeUpdate();
			conn.close();
			return true;
		}catch (SQLException se){
			se.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
}