package net.member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.info.db.UserId_post;

public class MemberDAO {
	private Connection conn;
	private DataSource ds;
	private ResultSet rs;
	private PreparedStatement pstmt;
	int result;
	
	public MemberDAO() {
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
	
	
	public int join(Member member) {
		result=0;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String sql = "insert into All_users (id, password, name, email, profileImg) values(?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getId()+".png");
			
			result = pstmt.executeUpdate();
			
			sql = "create table " + member.getId() +"_post "
					+ " (id varchar2(20) references All_users(id),"
					+ "itemNum number(5) primary key,"
					+ "openLevel number(1),"
					+ "pic_url varchar2(50) not null,"
					+ "dataText varchar2(4000) not null,"
					+ "posted_time date default sysdate,"
					+ "alter_time date,"
					+ "location varchar2(50))";
			pstmt=conn.prepareStatement(sql);
			pstmt.execute();
			
			sql = "create table " + member.getId() + "_Follow "
					+ "(id varchar2(20) references All_users(id) on delete cascade,"
					+ "hashTag varchar2(80),"
					+ "id_follower varchar2(20) references All_users(id) on delete cascade)";
			pstmt=conn.prepareStatement(sql);
			pstmt.execute();
			
			sql = "create table " + member.getId() + "_post_like "
					+ "(itemNum Number(5) references " +member.getId() + "_post(itemNum) on delete cascade,"
					+ "likes varchar2(20) references All_users(id) on delete cascade)";
			pstmt=conn.prepareStatement(sql);
			pstmt.execute();
			
			sql = "create table " + member.getId() + "_post_replied "
					+ "(itemNum Number(5) references " +member.getId() + "_post(itemNum) on delete cascade,"
					+ "comments varchar2(1500) not null, "
					+ "reply_no Number(3) primary key,"
					+ "ref_no Number(3) references " +member.getId() + "_post_replied(reply_no) on delete cascade,"
					+ "reply_id varchar2(20) references All_users(id) on delete cascade,"
					+ "reply_time date default sysdate)";
			pstmt=conn.prepareStatement(sql);
			pstmt.execute();
			
			if(result == 1) {
				System.out.println("회원가입 성공");
				conn.commit();
			}
			
		} catch (SQLException e) {
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

	public int isId(String checkId) {	// id체크시 없으면 0, 있으면 -1
		try {
			conn = ds.getConnection();
			
			String sql = "select id from All_users where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, checkId);
			rs = pstmt.executeQuery();
			
			if(rs.next())
				return -1;
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return 0;
	}
	
	public int isBanned(String checkId, String checkEmail) {
		try {
			conn = ds.getConnection();
			
			String sql = "select * from banned where id = ? or email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, checkId);
			pstmt.setString(2, checkEmail);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				return -1;
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return 0;
	}

	public int delete(String id) {
		result=0;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			System.out.println(id);
			
			String sql = "delete from hashtags where usedWhere like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id+"_%");
			result = pstmt.executeUpdate();
			
			sql = "delete from locations where usedWhere like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id+"_%");
			result = pstmt.executeUpdate();
			
			sql = "drop table "+id+"_post_like";
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
			
			sql = "drop table "+id+"_post_replied";
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
			
			sql = "drop table "+id+"_Follow";
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
			
			sql = "drop table "+id+"_post";
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
			
			sql = "delete from All_users where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
			
			if(result == 1) {
				System.out.println("회원 탈퇴 성공");
				conn.commit();
				return 0;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.commit();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			close();
		}
		return -1;
	}

	public int isId(String id, String pass) {
		try {
			conn = ds.getConnection();
			
			String sql = "select * from All_users where id = ? and password = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			rs = pstmt.executeQuery();
			
			if(rs.next())
				return 0;
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return -1;
	}

	public boolean profileImgUpdate(String id, String fileName) {
		try {
			conn = ds.getConnection();
			
			String sql = "update All_users set profileImg = ? where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fileName);
			pstmt.setString(2, id);
			
			int result = pstmt.executeUpdate();
			if(result == 1)
				return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public int followercount(String id) {
		int x = 0;
		try {
			String sql = "select count(id_follower) from " + id + "_follow";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				x = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return x;
	}
	public int followingcount(String id) {
		int x = 0;
		try {
			String sql = "select count(id), count(hashtag) from " + id + "_follow";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				x = rs.getInt(1);
				x += rs.getInt(2);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return x;
	}
	public Member getprofile(String id) {
		Member m = null;
		String sql = "select name , email from All_users where id = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				m = new Member();
				m.setName(rs.getString("name"));
				m.setEmail(rs.getString("email"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return m;
	}
	public boolean membermodify(Member m) {
		result = 0;
		try {
			conn = ds.getConnection();
			String sql = "update All_users set email = ? , name = ? where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getEmail());
			pstmt.setString(2, m.getName());
			pstmt.setString(3, m.getId());
			result = pstmt.executeUpdate();
			if(result == 1) {
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	public int passcheck(String id , String pass) {
		result = 0;
		try {
			conn = ds.getConnection();
			String sql = "select password from all_users where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(pass)) {
					result = 1;
				}	
				else {
					result = 0;
				}
			} 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	public boolean passmodify(String id , String newpass) {
		result = 0;
		try {
			conn = ds.getConnection();
			String sql = "update All_users set password = ? where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newpass);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
			if(result == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	public String getProfileImg(String id) {
		
		try {
			conn = ds.getConnection();
			String sql = "select profileImg from all_users where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	public List<UserId_post> getMyProfileList(String id) {
		List<UserId_post> list = new ArrayList<UserId_post>();
		try {
			conn = ds.getConnection();
			
			String sql = "select * from " + id + "_post order by posted_time desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				UserId_post uid = new UserId_post();
				uid.setId(rs.getString("id"));
				uid.setLevel(rs.getInt("openLevel"));
				uid.setDataText(rs.getString("dataText"));
				uid.setItemNum(rs.getInt("itemNum"));
				uid.setPosted_time(rs.getDate("posted_time"));
				uid.setAlter_time(rs.getDate("alter_time"));
				uid.setLocation(rs.getString("location"));
				uid.setPic_url(rs.getString("pic_url"));
				list.add(uid);
			}
			return list;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}
	
	public int getListCount() { //admin용
		try {
			conn = ds.getConnection();
			System.out.println("getConnection");
			String sql = "select count(id) from All_users "
						+ "where id != 'admin'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	public int getListCount(String field, String value) { //admin용
		int x = 0;
		try {
			conn = ds.getConnection();
			String sql = "select count(*) from All_users "
						+ "where id != 'admin' "
						+ "and " + field + " like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+value+"%");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return x;
	}
	public List<Member> searchAll(String field, String value, int page, int limit) { //admin용
		String sql = "select * "
				+ "from (select rownum rnum, b.* "
						+ "from (select * from All_users "
								+ "where id != 'admin' "
								+ "and " + field + " like ? "
								+ "order by id ) b"
						+ ") "
				+ "where rnum>=? and rnum<=?";
		List<Member> list = new ArrayList<Member>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + value + "%");
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, endrow);
			rs = pstmt.executeQuery();
			// DB에서 가져온 데이터를 VO객체에 담습니다.
			while (rs.next()) {
				Member m = new Member();
				m.setId(rs.getString("id"));
				m.setPassword(rs.getString("password"));
				m.setName(rs.getString("name"));
				m.setEmail(rs.getString("email"));
				list.add(m);
			}
			return list; // 값을 담을 객체를 저장한 리스트를 호출한 곳으로 가져갑니다.
		} catch (SQLException ex) {
			System.out.println("searchAll(4) 에러 : " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}
		return null;
	}
	public Member member_info(String id) {
		Member m = null;
		try {
			conn = ds.getConnection();
			String sql = "select * from All_users where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				m = new Member();
				m.setId(rs.getString("id"));
				m.setPassword(rs.getString("password"));
				m.setName(rs.getString("name"));
				m.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return m;
	}
	
	public List<Member> searchAll(int page, int limit) { //admin용
		String sql = "select * "
					+ "from (select rownum rnum, b.* "
					+ "from (select * from All_users "
					+ "where id != 'admin' "
					+ "order by id ) b"
					+ ") "
				+ "where rnum>=? and rnum<=?";
		List<Member> list = new ArrayList<Member>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Member m = new Member();
				m.setId(rs.getString("id"));
				m.setPassword(rs.getString("password"));
				m.setName(rs.getString("name"));
				m.setEmail(rs.getString("email"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	
	public Member id(String email){
		Member m = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(
						"select id from All_users "
						+ "where email = ?");
			pstmt.setString(1,email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				m = new Member();
				System.out.println(rs.getString(1));
				m.setId(rs.getString(1));	
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
		return m;
	}
	public Member pass(String id, String email){
		Member m = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(
						"select password from All_users "
						+ " where id = ? and email = ?");
			pstmt.setString(1,id);
			pstmt.setString(2,email);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				m = new Member();
				m.setPassword(rs.getString(1));		
				System.out.println(rs.getString(1));			
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
		return m;
	}
	public int Postcount(String id) {
		int x = 0;
		try {
			String sql = "select count(*) from " + id + "_post";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				x = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return x;
	}

	public int isEmail(String email) {
		try {
			conn = ds.getConnection();
			
			String sql = "select email from All_users where email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if(rs.next())
				return -1;
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return 0;
	}
}
