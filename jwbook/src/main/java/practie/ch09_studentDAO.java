package practie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ch09_studentDAO {
	Connection conn = null;
	PreparedStatement pstmt;
	
	//JDBC 드라이버 호출
	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:tcp://localhost/~/jwbookdb";
	
	//DB 연결
	public void open() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, "jwbook", "1234");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//DB 종료
	public void close() {
		try {
			pstmt.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//학생 정보 추가 메서드
	public void insert(ch09_student s) {
		open();
		//statement 생성, sql문 전송
		String sql = "INSERT INTO STUDENT(USERNAME, UNIV, BIRTH, EMAIL) VALUES(?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s.getUsername());
			pstmt.setString(2, s.getUniv());
			pstmt.setString(3, s.getBirth());
			pstmt.setString(4, s.getEmail());
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	//학생 정보 전부 불러오기 메소드
	public List<ch09_student> getAll() {
		open();
		List<ch09_student> students = new ArrayList<>();
		try {
			//statement 생성, sql문 전송
			pstmt = conn.prepareStatement("SELECT * FROM STUDENT");
			//resultset 받기
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ch09_student s = new ch09_student();
				s.setId(rs.getInt("ID"));
				s.setUsername(rs.getString("USERNAME"));
				s.setUniv(rs.getString("UNIV"));
				s.setBirth(rs.getString("BIRTH"));
				s.setEmail(rs.getString("EMAIL"));
				students.add(s);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
			System.out.println("새로고침 완료");
		}
		return students;
	}

}
