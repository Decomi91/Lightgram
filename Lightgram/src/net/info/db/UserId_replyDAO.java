package net.info.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class UserId_replyDAO {
	private Connection conn;
	private DataSource ds;
	private ResultSet rs;
	private PreparedStatement pstmt;
	int result;
	
	public UserId_replyDAO() {
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

	
	public JsonArray getList(String postId, int postNum) {
		JsonArray jArray = new JsonArray();
		/*
		 * "create table " + member.getId() + "_post_replied "
					+ "(itemNum Number(5) references " +member.getId() + "_post(itemNum) on delete cascade,"
					+ "reply_no Number(3) primary key,"
					+ "ref_no Number(3) references " +member.getId() + "_post_replied(reply_no) on delete cascade,"
					+ "reply_id varchar2(20) references All_users(id) on delete cascade,"
		 */
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String sql = "select b.profileImg, a.* "
					+ "from " + postId + "_post_replied a "
							+ "inner join All_users b "
							+ "on a.reply_id=b.id "
					+ "where itemNum = ? "
					+ "order by ref_no, reply_no";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postNum);
			
			rs = pstmt.executeQuery();
			
			SimpleDateFormat sf = new SimpleDateFormat("yyMMdd");
			while(rs.next()) {
				JsonObject jobj = new JsonObject();
				jobj.addProperty("profileImg", rs.getString("profileImg"));
				jobj.addProperty("itemNum", rs.getInt("itemnum"));
				jobj.addProperty("comments", rs.getString("comments"));
				jobj.addProperty("reply_no", rs.getInt("reply_no"));
				jobj.addProperty("ref_no", rs.getInt("ref_no"));
				jobj.addProperty("reply_id", rs.getString("reply_id"));
				jobj.addProperty("reply_time", sf.format(rs.getDate("reply_time")));
				
				jArray.add(jobj);
			}
			
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return jArray;
	}

	public boolean writeComment(UserId_reply ure, String postid) {
		try {
			conn = ds.getConnection();
			String sql = "insert into " + postid + "_post_replied "
					+ "(itemNum, comments, reply_no, ref_no, reply_id, reply_time) "
					+ "values(?, ?, (select nvl(max(reply_no),0)+1 from " + postid + "_post_replied)"
							+ ", (select nvl(max(reply_no),0)+1 from " + postid + "_post_replied),"
									+ "?, sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ure.getItemNum());
			pstmt.setString(2, ure.getComments());
			pstmt.setString(3, ure.getReply_id());
			
			int result = pstmt.executeUpdate();
			
			if(result == 1)
				return true;
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	
	public JsonObject writeComment(UserId_reply ure, String postid, String newsfeed) {
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String sql = "insert into " + postid + "_post_replied "
					+ "(itemNum, comments, reply_no, ref_no, reply_id, reply_time) "
					+ "values(?, ?, (select nvl(max(reply_no),0)+1 from " + postid + "_post_replied)"
							+ ", (select nvl(max(reply_no),0)+1 from " + postid + "_post_replied),"
									+ "?, sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ure.getItemNum());
			pstmt.setString(2, ure.getComments());
			pstmt.setString(3, ure.getReply_id());
			
			int result = pstmt.executeUpdate();
			
			if(result == 1) {
				sql = "select b.profileImg, a.* "
					+ "from " + postid + "_post_replied a inner join All_users b on b.id = a.reply_id "
					+ "where reply_no = (select max(reply_no) from " + postid + "_post_replied)";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				JsonObject jobj = new JsonObject();
				if(rs.next()) {
					jobj.addProperty("profileImg", rs.getString("profileImg"));
					jobj.addProperty("reply_id", rs.getString("reply_id"));
					jobj.addProperty("comments", rs.getString("comments"));
					jobj.addProperty("reply_time", rs.getDate("reply_time").toString());
				}
				conn.commit();
				return jobj;
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
		return null;
	}

	public boolean writeReComment(UserId_reply ure, String postid) {
		try {
			conn = ds.getConnection();
			String sql = "insert into " + postid + "_post_replied "
					+ "(itemNum, comments, reply_no, ref_no, reply_id, reply_time) "
					+ "values(?, ?, (select nvl(max(reply_no),0)+1 from " + postid + "_post_replied)"
							+ ", ?, ?, sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ure.getItemNum());
			pstmt.setString(2, ure.getComments());
			pstmt.setInt(3, ure.getRef_no());
			pstmt.setString(4, ure.getReply_id());
			
			int result = pstmt.executeUpdate();
			
			if(result == 1)
				return true;
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public boolean deleteComment(UserId_reply ure, String postid) {
		try {
			conn = ds.getConnection();
			String sql = "delete from " + postid + "_post_replied "
					+ "where itemNum = ? and reply_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ure.getItemNum());
			pstmt.setInt(2, ure.getReply_no());
			
			int result = pstmt.executeUpdate();
			
			if(result == 1)
				return true;
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
}
