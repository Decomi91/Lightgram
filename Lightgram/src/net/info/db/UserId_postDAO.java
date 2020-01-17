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

public class UserId_postDAO {
	private Connection conn;
	private DataSource ds;
	private ResultSet rs;
	private PreparedStatement pstmt;
	int result;
	
	public UserId_postDAO() {
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

	public int countItemNum(String id) {
		try {
			conn = ds.getConnection();
			String sql = "select nvl(max(itemNum),0)+1 from "+id+"_post";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				return rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		return 0;
	}

	public boolean addItem(UserId_post uid) { //result=1, ture  반환
		result = 0;
		try {
			conn = ds.getConnection();
			String sql = "insert into " + uid.getId() + "_post (id, itemNum, openLevel, pic_url, dataText, posted_time, location)"
					+ " values(?, ?, ?, ?, ?, sysdate, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid.getId());
			pstmt.setInt(2, uid.getItemNum());
			pstmt.setInt(3, uid.getLevel());
			pstmt.setString(4, uid.getPic_url());
			pstmt.setString(5, uid.getDataText());
			pstmt.setString(6, uid.getLocation());
			result = pstmt.executeUpdate();
			
			if(result == 1)
				return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return false;
	}
	
	public List<UserId_post> getList(String id, String search, String search_option){
		List<UserId_post> list=new ArrayList<UserId_post>();
		List<String> id_list = new ArrayList<String>();
		List<String> id_follow_list = new ArrayList<String>();
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String sql = "select id from All_users where " + search_option;
			if(search_option.equals("name")) {
				sql  += " like ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+search+"%");
			} else {
				sql  += " = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, search);
			}
			rs=pstmt.executeQuery();
			ResultSet rs2;
			
			while(rs.next()) {
				id_list.add(rs.getString("id"));
			}
			
			if(id_list.isEmpty())
				return null;			//검색 결과가 없을 경우 바로 null로 리턴
			
			sql = "select id from " + id + "_follow";
			pstmt = conn.prepareStatement(sql);
			rs2=pstmt.executeQuery();
			
			while(rs2.next()) {
				id_follow_list.add(rs2.getString("id"));
			}
			 
			
			sql = "select b.* "
					+ " from (select a.* "
							+ " from (";
				for(int i=0; i<id_list.size(); i++) {
					sql += "select * from " + id_list.get(i)+"_post where openlevel <";
					if(id_list.get(i).equals(id))
						sql += "4 union ";
					else if(id_follow_list.contains(id_list.get(i)))
						sql += "3 union ";
					else
						sql += "2 union ";
				}
			sql = sql.substring(0, sql.length()-6);
			sql +=			 ") a "
						+ "order by a.posted_time desc "
					+ ")b";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			
			while(rs.next()) {
				UserId_post member=new UserId_post();
				member.setId(rs.getString("id"));
				member.setItemNum(rs.getInt("itemNum"));
				member.setLevel(rs.getInt("openLevel"));
				member.setPic_url(rs.getString("pic_url"));
				member.setDataText(rs.getString("dataText"));
				member.setPosted_time(rs.getDate("posted_time"));
				member.setAlter_time(rs.getDate("alter_time"));
				member.setLocation(rs.getString("location"));
				list.add(member);
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			close();
		}
		return list;
	}

	public JsonArray newsFeed(String id, int scrollSize) {
		JsonArray list = new JsonArray();
		JsonObject full = new JsonObject();
		JsonArray res = new JsonArray();
		Queue<String> hashtagTarget = new LinkedList<String>();
		Queue<String> follow_id = new LinkedList<String>();
		Queue<String> follow_hashTag = new LinkedList<String>();
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String sql = "select id, hashTag from "+id+"_Follow";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getString(1) != null) {
					follow_id.add(rs.getString(1));
				}
				if(rs.getString(2) != null) {
					follow_hashTag.add(rs.getString(2));
				}
			}
			
//			select e.*
//			from (select rownum r, d.* 
//					from(	select c.* 
//							from (select b.profileImg, a.* 
//									from ( select * from show1_post
//											union 
//											select * from show2_post where openlevel < 3
//											union 
//											select * from show3_post where itemNum = 2 and openLevel = 1
//											) a 
//										inner join All_users b
//										on a.id = b.id
//									) c
//							order by c.posted_time desc 
//						)d
//					)e
//			where e.r<4;
			
			if(!follow_hashTag.isEmpty()) {
				sql = "select usedWhere from hashTags where hashTag = '";
				while(!follow_hashTag.isEmpty()) {
					sql += "#" + follow_hashTag.poll() + "' or hashTag = '";
				}
				sql = sql.substring(0,sql.length()-14);
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					hashtagTarget.add(rs.getString(1));
				}
			}
			
			sql = "select e.* "
				+ " from (select rownum r, d.*, count(*) over (partition by 1) ff "
						+ " from (select c.* "
								+ " from (select b.profileImg, a.* "
										+ " from ( select * from " + id + "_post "
												+ " union ";
			while(!follow_id.isEmpty()) {
				sql += "select * from "+follow_id.poll()+"_post where openlevel < 3 union ";
			}
			while(!hashtagTarget.isEmpty()) {
				System.out.println(hashtagTarget.peek());
				sql += "select * from " + hashtagTarget.peek().split("_")[0];
				sql += "_post where itemNum = " + Integer.parseInt(hashtagTarget.poll().split("_")[1]) + " and openLevel = 1 union ";
			}
			sql = sql.substring(0, sql.length()-6);
			sql +=			 				") a"
										+ " inner join All_users b "
										+ " on a.id = b.id "
									+ ") c "
								+ " order by c.posted_time desc "
							+ ")d "
						+ ") e "
				+ " where e.r<"+(scrollSize+2);
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			/*id varchar2(20),
			 *itemNum number(5) primary key,"
					+ "openLevel number(1),"
					+ "pic_url varchar2(50) not null,"
					+ "dataText varchar2(4000) default '',"
					+ "posted_time date default sysdate,"
					+ "alter_time date,"
					+ "location varchar2(50),"
					+ "hashTag varchar2(600))";
			 */
			int cnt = 0;
			while(rs.next()) {
				JsonArray reply = new JsonArray();	//댓글 저장용
				
				ResultSet rs2;
				JsonObject uid = new JsonObject();
				uid.addProperty("profileImg", rs.getString("profileImg"));
				uid.addProperty("id", rs.getString("id"));
				uid.addProperty("itemNum", rs.getInt("itemNum"));
//				uid.addProperty("Level",rs.getInt("openLevel"));	//필요없어보임
				uid.addProperty("pic_url",rs.getString("pic_url"));
				uid.addProperty("dataText", rs.getString("dataText"));
				uid.addProperty("posted_Time", rs.getDate("posted_Time").toString());
				if(rs.getDate("Alter_Time") != null)
					uid.addProperty("alter_Time",rs.getDate("Alter_Time").toString());
				uid.addProperty("location",rs.getString("location"));
				cnt = rs.getInt("ff");
				sql = "select a.*, b.profileImg from " + rs.getString("id") + "_post_replied a inner join All_users b on a.reply_id = b.id where itemNum = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, rs.getInt("itemNum"));
				
				rs2 = pstmt.executeQuery();
				
				while(rs2.next()) {
					JsonObject jobj = new JsonObject();
					jobj.addProperty("profileImg", rs2.getString("profileImg"));
					jobj.addProperty("itemNum", rs2.getString("itemNum"));
					jobj.addProperty("comments", rs2.getString("comments"));
					jobj.addProperty("reply_no", rs2.getInt("reply_no"));
					jobj.addProperty("ref_no", rs2.getInt("ref_no"));
					jobj.addProperty("reply_id", rs2.getString("reply_id"));
					jobj.addProperty("reply_time", rs2.getDate("reply_time").toString());
					reply.add(jobj);
				}
				
				
				UserId_likeDAO uidao = new UserId_likeDAO();
				
				uid.add("replied", reply);
				uid.add("likes", uidao.getLikes(id, rs.getString("id"), rs.getInt("itemNum")));
				list.add(uid);
				
			}
			
			/*
			 * "(itemNum Number(5) references " +member.getId() + "_post(itemNum) on delete cascade,"
					+ "comments varchar2(1500), "
					+ "reply_no Number(3) primary key,"
					+ "ref_no Number(3) references " +member.getId() + "_post_replied(reply_no) on delete cascade,"
					+ "reply_id varchar2(20) references All_users(id) on delete cascade,"
			 * 
			 */
			full.addProperty("full", cnt);
			res.add(list);
			res.add(full);
			res.add(id);
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			close();
		}
		return res;
	}

	public boolean deletePost(String id, int itemNum) {
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String sql = "delete from hashTags where usedWhere = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id+"_"+itemNum);
			result = pstmt.executeUpdate();
			
			sql = "delete from locations where usedWhere = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id+"_"+itemNum);
			result = pstmt.executeUpdate();
			
			sql = "delete from " + id + "_post where itemNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, itemNum);
			result = pstmt.executeUpdate();
			
			if(result == 1) {
				conn.commit();
				return true;
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
		return false;
	}

	public boolean modifyPost(UserId_post uid) {
		result = 0;
		try {
			conn = ds.getConnection();
			String sql = "update " + uid.getId() + "_post "
					+ "set openLevel = ?, dataText = ?, alter_time = sysdate, location = ?"
					+ "where itemNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uid.getLevel());
			pstmt.setString(2, uid.getDataText());
			pstmt.setString(3, uid.getLocation());
			pstmt.setInt(4, uid.getItemNum());
			result = pstmt.executeUpdate();
			
			if(result == 1)
				return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return false;
	}

	public List<UserId_post> getList(String id) {
		List<UserId_post> list = new ArrayList<UserId_post>();
		try {
			conn = ds.getConnection();
			String sql = "select * from " + id + "_post order by posted_time desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				UserId_post uid = new UserId_post();
				uid.setId(rs.getString("id"));
				uid.setItemNum(rs.getInt("itemNum"));
				uid.setLevel(rs.getInt("openLevel"));
				uid.setPic_url(rs.getString("pic_url"));
				uid.setDataText(rs.getString("dataText"));
				uid.setPosted_time(rs.getDate("posted_Time"));
				uid.setAlter_time(rs.getDate("Alter_Time"));
				uid.setLocation(rs.getString("location"));
				list.add(uid);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	public List<UserId_post> getList2(String id, String search, String search_option) {
		List<UserId_post> list=new ArrayList<UserId_post>();
		
		List<String> id_follow_list = new ArrayList<String>();
		Queue<String> target = new LinkedList<String>();
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String sql = "select usedWhere from " + search_option + "s where " + search_option + " like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");

			rs = pstmt.executeQuery();
			while(rs.next()) {
				target.add(rs.getString(1));
			}
			if(target.isEmpty())
				return null;
			
			sql = "select id from " + id + "_follow";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				id_follow_list.add(rs.getString("id"));
			}
			
			sql = "select b.* "
					+ " from (select a.* "
							+ " from (";
				while(!target.isEmpty()){
					String targetId = "";
					if(target.peek().split("_").length > 2 ) {
						for(int i=0; i<target.peek().split("_").length-1; i++) {
							targetId += target.peek().split("_")[i]+"_";
						}
						targetId = targetId.substring(0, targetId.length()-1);
					}else {
						targetId = target.peek().split("_")[0];
					}
					String targetItem = target.peek().split("_")[target.peek().split("_").length-1];
					
					sql += "select * from " + targetId + "_post where itemNum = " + targetItem + " and openlevel <";
					if(targetId.equals(id)) {
						sql += "4 union ";
						target.poll();
					}else if(id_follow_list.contains(targetId)) {
						sql += "3 union ";
					}else {
						sql += "2 union ";
					}
					target.poll();
				}
			sql = sql.substring(0, sql.length()-6);
			sql +=			 ") a "
						+ " order by a.posted_time desc "
					+ ")b";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			
			while(rs.next()) {
				UserId_post member=new UserId_post();
				member.setId(rs.getString("id"));
				member.setItemNum(rs.getInt("itemNum"));
				member.setLevel(rs.getInt("openLevel"));
				member.setPic_url(rs.getString("pic_url"));
				member.setDataText(rs.getString("dataText"));
				member.setPosted_time(rs.getDate("posted_time"));
				member.setAlter_time(rs.getDate("alter_time"));
				member.setLocation(rs.getString("location"));
				list.add(member);
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			close();
		}
		return list;
	}
	
	public UserId_post board(String id, String itemNum) {
		UserId_post uid = null;
		try {
			conn = ds.getConnection();
			String sql = "select * from " + id + "_post "
					+ "where itemNum = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, itemNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				uid = new UserId_post();
				uid.setId(rs.getString("id"));
				uid.setItemNum(rs.getInt("itemNum"));
				uid.setLevel(rs.getInt("openLevel"));
				uid.setPic_url(rs.getString("pic_url"));
				uid.setDataText(rs.getString("dataText"));
				uid.setPosted_time(rs.getDate("posted_Time"));
				uid.setAlter_time(rs.getDate("Alter_Time"));
				uid.setLocation(rs.getString("location"));
			}
			return uid;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}
}
