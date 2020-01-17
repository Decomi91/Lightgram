package net.info.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class UserId_likeDAO {
	private Connection conn;
	private DataSource ds;
	private ResultSet rs;
	private PreparedStatement pstmt;
	int result;
	
	public UserId_likeDAO() {
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
				e.printStackTrace();
			}
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	public JsonArray getLikes(String loginId, String postId, int postNum) {
		JsonArray result = new JsonArray();
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from " + postId + "_post_like where itemNum = ? and likes = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postNum);
			pstmt.setString(2, loginId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result.add(1);
			} else {
				result.add(0);
			}
			
			sql = "select count(*) from " + postId + "_post_like where itemNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result.add(rs.getInt(1));
			}
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			close();
		}
		
		return result;
	}

	public int setLike(String loginId, String postId, int postNum) {
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String sql = "select * from " + postId + "_post_like where itemNum = ? and likes = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postNum);
			pstmt.setString(2, loginId);
			
			rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				sql = "insert into " + postId + "_post_like (itemNum, likes) values(?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, postNum);
				pstmt.setString(2, loginId);
				result = pstmt.executeUpdate();
				
				if(result == 1) {
					conn.commit();
					return 1;
				}
			}else {
				sql = "delete from " + postId + "_post_like where itemNum = ? and likes = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, postNum);
				pstmt.setString(2, loginId);
				result = pstmt.executeUpdate();
				
				if(result == 1) {
					conn.commit();
					return 0;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			close();
		}
		return -1;
	}
}
