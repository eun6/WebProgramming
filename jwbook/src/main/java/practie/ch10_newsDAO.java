package practie;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ch10_newsDAO {
	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:tcp://localhost/~/jwbookdb";
	
	
	//DB 연결
	public Connection open() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, "jwbook", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/*DB 종료 -> try-with-resources 문 사용하면서 안 씀.
	public void close() {
		if(pstmt != null) {
			try {
				pstmt.close();
				conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}*/
	
	//뉴스 등록
	public void insert(ch10_news n) throws SQLException {
		Connection conn = open();
		String sql = "INSERT INTO NEWS(TITLE, IMG, DATE, CONTENT) VALUES (?, ?, CURRENT_TIMESTAMP(), ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try(conn; pstmt) {
			pstmt.setString(1, n.getTitle());
			pstmt.setString(2, n.getImg());
			pstmt.setString(3, n.getContent());
			pstmt.executeUpdate();
		}
	}
	
	//뉴스 목록
	public List<ch10_news> getAll() throws SQLException {
		Connection conn = open();
		List<ch10_news> newsList = new ArrayList<>();
		String sql = "SELECT aid, title, date FROM NEWS";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try(conn; pstmt; rs) {
			while(rs.next()) {
				//do 객체 선언, 세팅
				ch10_news n = new ch10_news();
				n.setAid(rs.getInt("aid"));
				n.setTitle(rs.getString("title"));
				n.setDate(rs.getString("date"));
				
				//리스트에 추가
				newsList.add(n);
			}
		}
		return newsList;
	}
	//뉴스 하나만 가져오기
	public ch10_news getNews(int aid) throws SQLException {
		Connection conn = open();
		ch10_news n = new ch10_news();
		
		String sql = "select aid, title, img, PARSEDATETIME(date, 'yyyy-MM-dd hh:mm:ss') as cdate, content from news where aid=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, aid);
		
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		
		try (conn; pstmt; rs) {
			n.setAid(rs.getInt("aid"));
			n.setTitle(rs.getString("title"));
			n.setImg(rs.getString("img"));
			n.setContent(rs.getString("content"));
			return n;
		}
	}
	
	//삭제
	public void delNews(int aid) throws SQLException {
		Connection conn = open();
		PreparedStatement pstmt = conn.prepareStatement("delete from news where aid=?");
		
		try(conn; pstmt;) {
			pstmt.setInt(1, aid);
			if (pstmt.executeUpdate() == 0) {
				throw new SQLException("DB 에러");
			}
		}
	}
}
