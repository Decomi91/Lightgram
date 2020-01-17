package net.info.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class HashTagsDAO {
	private Connection conn;
	private DataSource ds;
	private ResultSet rs;
	private PreparedStatement pstmt;
	int result;
	
	public HashTagsDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean insertHashTag(String id, int itemNum, String hashTag) {
		try {
			conn = ds.getConnection();
			
			String sql = "insert into hashTags (hashTag, usedWhere) values (?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hashTag);
			pstmt.setString(2, id + "_" + itemNum);
			pstmt.executeUpdate();
			
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return false;
	}
	
	public boolean deleteHashTag(String id, int itemNum) {
		try {
			result = 0;
			conn = ds.getConnection();
			
			String sql = "delete from hashTags where usedWhere = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id+"_"+itemNum);
			
			rs = pstmt.executeQuery();
			
			result = pstmt.executeUpdate();
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
}
