package edu.sjsu.db;
/*
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import edu.sjsu.models.DepartmentDocument;
/*
import org.json.JSONObject;
import org.json.JSONException;
*/
import edu.sjsu.models.Document;
import edu.sjsu.models.SchoolMember;
import edu.sjsu.models.Student;
import edu.sjsu.models.StudentDocument;
import edu.sjsu.models.Major;
import edu.sjsu.utils.DBUtils;

public class DocumentDao {
	
	
	//This method is for storing new Documents that do not have previous versions associated with them as it assigns new DocID values. 
	//This method is a transaction that will fail and rollback if a write to either documents or userdocuments fails. 
	public static boolean storeUserDocument(Document doc, SchoolMember owner){
		try{
			Connection conn = null;
			PreparedStatement stuDocPstmt = null;
			PreparedStatement docPstmt = null;
			Student student = (Student)owner;
			try {
				conn = DBUtils.getConn();
				String documentInsert = "INSERT INTO `documents` (`DocID`,`Date`, `DocLocation`, `DocName`, `AdvisorEmail`) VALUES (?, ?, ?, ?, ?)";
				String stuDocInsert = "INSERT INTO `userdocuments` (`UserEmail`, `DocID`) VALUES (?, ?);";
				conn.setAutoCommit(false);
				stuDocPstmt = conn.prepareStatement(stuDocInsert);
				docPstmt = conn.prepareStatement(documentInsert);
				doc.setDocID(getNextAvailableDocID());
				docPstmt.setInt(1, doc.getDocID());
				docPstmt.setTimestamp(2, doc.getCurrentDate());
				docPstmt.setString(3, doc.getLocation());
				docPstmt.setString(4, doc.getName());
				if(student.getAdvisor() != null){
					docPstmt.setString(5, student.getAdvisor().getEmail());
				}else{
					docPstmt.setString(5, null);
				}
				docPstmt.executeUpdate();
				
				stuDocPstmt.setString(1, owner.getEmail());
				stuDocPstmt.setInt(2, doc.getDocID());
				stuDocPstmt.executeUpdate();
				
				conn.commit();
				conn.close();
				return true;
			} catch (SQLException se) {
				if(conn != null){
					conn.rollback();
					conn.close();
				}
				se.printStackTrace();
			} catch (Exception e) {
				if(conn != null){
					conn.rollback();
					conn.close();
				}
				
				e.printStackTrace();
			} finally{
				if(stuDocPstmt != null){
					stuDocPstmt.close();
				}
				if(docPstmt != null){
					docPstmt.close();
				}
				
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	//This method is explicitly for updating existing documents that already exist within the system. 
	public static boolean updateUserDocument(Document doc){
		try{
			Connection conn = DBUtils.getConn();
			String docUpdate = "INSERT INTO `documents` (`DocID`, `Date`, `DocLocation`, `PreviousDate`, `DocName`) VALUES (?, ?, ?, ?, ?); ";
			PreparedStatement updatePstmt = conn.prepareStatement(docUpdate);
			updatePstmt.setInt(1, doc.getDocID());
			updatePstmt.setTimestamp(2, doc.getCurrentDate());
			updatePstmt.setString(3, doc.getLocation());
			updatePstmt.setTimestamp(4, doc.getPreviousDate());
			updatePstmt.setString(5,  doc.getName());
			updatePstmt.executeUpdate();
			return true;
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static Document getStudentDocumentVersion(int docID, Timestamp versionDate, Student member){
		try{
			Connection conn = DBUtils.getConn();
			String specificDocSQL = "SELECT `DocID`, `Date`, `DocLocation`, `PreviousDate`, `DocName`, `Status`, `AdvisorEmail` "+
									"FROM `documents` WHERE `DocID` = ? AND `Date` = ?; ";
			PreparedStatement specificPstmt = conn.prepareStatement(specificDocSQL);
			specificPstmt.setInt(1, docID);
			specificPstmt.setTimestamp(2, versionDate);
			ResultSet result = specificPstmt.executeQuery();
			if(!result.next()){
				conn.close();
				specificPstmt.close();
				return null;
			}else{
				Document stuDoc = new StudentDocument(result.getInt("DocID"), result.getTimestamp("Date"), result.getString("DocLocation"),
						result.getTimestamp("PreviousDate"), result.getString("DocName"),member, result.getString("Status"),
						AdvisorDao.getAdvisor(result.getString("AdvisorEmail")));
				return stuDoc;
			}
		}catch (SQLException se){
			se.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static Document getStudentDocument(int docID, Student member){
		try{
			Connection conn = DBUtils.getConn();
			String sqlStr = "SELECT `DocID`, MAX(`Date`) AS `Latest`, `DocLocation`, `PreviousDate`, "
					+ "`DocName`, `Status`, `AdvisorEmail` FROM `documents` WHERE `DocID` = ? GROUP BY `DocLocation`, `PreviousDate`, DocName, Status, AdvisorEmail;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, docID);
			ResultSet result = pstmt.executeQuery();
			if(!result.next()){
				conn.close();
				return null;
			}else{
				Document currDoc = new StudentDocument(docID, result.getTimestamp("Latest"), result.getString("DocLocation"), 
						result.getTimestamp("PreviousDate"), result.getString("DocName"), member, result.getString("Status"),
						AdvisorDao.getAdvisor(result.getString("AdvisorEmail")));
				return currDoc;
			}
		}catch (SQLException se) {
			se.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Document getStudentDocumentByDate(int docID, Student member, String date){
		try{
			Connection conn = DBUtils.getConn();
			String sqlStr = "SELECT `DocID`, `Date`, `DocLocation`, `PreviousDate`, "
					+ "`DocName`, `Status`, `AdvisorEmail` FROM `documents` WHERE `DocID` = ? AND `Date` = ? GROUP BY `DocLocation`, `PreviousDate`, DocName, Status, AdvisorEmail;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, docID);
			pstmt.setString(2, date);
			ResultSet result = pstmt.executeQuery();
			if(!result.next()){
				conn.close();
				return null;
			}else{
				Document currDoc = new StudentDocument(docID, result.getTimestamp("Date"), result.getString("DocLocation"), 
						result.getTimestamp("PreviousDate"), result.getString("DocName"), member, result.getString("Status"),
						AdvisorDao.getAdvisor(result.getString("AdvisorEmail")));
				return currDoc;
			}
		}catch (SQLException se) {
			se.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	public static ArrayList<Document> getAllStudentDocs(Student student){
		try{
			Connection conn = DBUtils.getConn();
			String docIDStr = "SELECT `DocID` FROM `userdocuments` WHERE `UserEmail` = ? GROUP BY `DocID`;";
			System.out.println(docIDStr);
			PreparedStatement pstmt = conn.prepareStatement(docIDStr);
			System.out.println(student.getHashMap().toString());
			pstmt.setString(1, student.getEmail());
			ResultSet docIDs = pstmt.executeQuery();
			if(!docIDs.next()){
				conn.close();
				return null;
			}else{
				ArrayList<Document> docList = new ArrayList<Document>();
				do{
					System.out.println(docIDs.getInt("DocID"));
					docList.add(getStudentDocument(docIDs.getInt("DocID"), student));
				}while(docIDs.next()); 
				return docList;
			}
		}catch (SQLException se){
			se.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	public static Document getMajorDocument(int docID, Major major){
		try{
			Connection conn = DBUtils.getConn();
			String sqlMajorDoc = "SELECT `DocID`, MAX(Date) AS `Latest`, `DocLocation`, `PreviousDate`, `DocName` "+
								 "FROM (SELECT * FROM documents WHERE `DocID` = ?; ";
			PreparedStatement majDocPstmt = conn.prepareStatement(sqlMajorDoc);
			majDocPstmt.setInt(1, docID);
			ResultSet result = majDocPstmt.executeQuery();
			if(!result.next()){
				conn.close();
				return null;
			}else{
				DepartmentDocument majordoc = new DepartmentDocument(docID, result.getTimestamp("Latest"), result.getString("Location"), 
						result.getTimestamp("PreviousDate"), result.getString("DocName"), major);
				conn.close();
				majDocPstmt.close();
				return majordoc;
			}
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static Document getMajorDocumentVersion(int docID, Timestamp date, Major major){
		try{
			Connection conn = DBUtils.getConn();
			String sqlMajorVersionDoc = "SELECT `DocID, `Date`, `DocLocation`, `PreviousDate`, `DocName` " +
										"FROM `documents` WHERE `DocID` = ? AND `Date` = ?;";
			PreparedStatement majDocPstmt = conn.prepareStatement(sqlMajorVersionDoc);
			majDocPstmt.setInt(1, docID);
			majDocPstmt.setTimestamp(2, date);
			ResultSet result = majDocPstmt.executeQuery();
			
			if(!result.next()){
				conn.close();
				return null;
			}else{
				DepartmentDocument majorDoc = new DepartmentDocument(docID, date, result.getString("Location"),
						result.getTimestamp("PreviousDate"), result.getString("DocName"), major);
				conn.close();
				majDocPstmt.close();
				return majorDoc;
			}
		}catch (SQLException se){
			se.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<Document> getAllMajorDocuments(Major major){
		try{
			Connection conn = DBUtils.getConn();
			String sqlMajorDocTable = "SELECT `DocID` FROM `majordocuments` WHERE `MajorID` = ?; ";
			PreparedStatement majorDocsPstmt = conn.prepareStatement(sqlMajorDocTable);
			majorDocsPstmt.setInt(1, major.getMajorID());
			ResultSet result = majorDocsPstmt.executeQuery();
			if(!result.next()){
				conn.close();
				return null;
			}else{
				ArrayList<Document> majorDocs = new ArrayList<Document>();
				do{
					majorDocs.add(getMajorDocument(result.getInt("DocID"), major));
				}while(result.next());
				return majorDocs;
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	//TODO: Implement
	public static boolean storeMajorDocument(Document doc, Major major){
		try{
			Connection conn = null;
			PreparedStatement majorDocPstmt = null;
			PreparedStatement docPstmt = null;
			try{
				conn = DBUtils.getConn();
				conn.setAutoCommit(false);
				String insertDocSQL = "INSERT INTO `documents` (`DocID`, `Date`, `DocLocation`, `DocName`) VALUES (?,?,?,?);";
				String insertMajorDocSQL = "INSERT INTO `majordocuments` (`MajorID`, `DocID`) VALUES (?,?);";
				docPstmt = conn.prepareStatement(insertDocSQL);
				doc.setDocID(getNextAvailableDocID());
				docPstmt.setInt(1,  doc.getDocID());
				docPstmt.setTimestamp(2, doc.getCurrentDate());
				docPstmt.setString(3,  doc.getLocation());
				docPstmt.setString(4, doc.getName());
				docPstmt.executeUpdate();
				
				majorDocPstmt = conn.prepareStatement(insertMajorDocSQL);
				majorDocPstmt.setInt(1, major.getMajorID());
				majorDocPstmt.setInt(2, doc.getDocID());
				majorDocPstmt.executeUpdate();
				conn.commit();
				conn.close();
				return true;
			}catch (SQLException se){
				se.printStackTrace();
				if(conn != null){
					conn.rollback();
					conn.close();
				}
			}catch (Exception e){
				e.printStackTrace();
				if(conn != null){
					conn.rollback();
					conn.close();
				}
			}finally{
				if(majorDocPstmt != null){
					majorDocPstmt.close();
				}
				if(docPstmt != null){
					docPstmt.close();
				}
			}
		}catch (SQLException se){
			se.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	//TODO: implement
	public static boolean updateMajorDocument(Document doc){
		try{
			Connection conn = DBUtils.getConn();
			String insertSQL = "INSERT INTO `documents` (`DocID`, `Date`, `DocLocation`, `PreviousDate`, `DocName`) VALUES (?,?,?,?,?);";
			PreparedStatement insertPstmt = conn.prepareStatement(insertSQL);
			insertPstmt.setInt(1, doc.getDocID());
			insertPstmt.setTimestamp(2, doc.getCurrentDate());
			insertPstmt.setString(3, doc.getLocation());
			insertPstmt.setTimestamp(4, doc.getPreviousDate());
			insertPstmt.setString(5, doc.getName());
			insertPstmt.executeUpdate();
			return true;
		}catch (SQLException se){
			se.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static int getNextAvailableDocID(){
		try{
			Connection conn = DBUtils.getConn();
			String docIDStr = "SELECT MAX(DocID) AS ID FROM documents;";
			PreparedStatement pstmt = conn.prepareStatement(docIDStr);
			ResultSet result = pstmt.executeQuery();
			if(!result.next()){
				return 1;
			}else{
				int docID = result.getInt("ID");
				docID++;
				return docID;
			}
		}catch (SQLException se){
			se.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}	
		return -1;
	}
	
	

}
