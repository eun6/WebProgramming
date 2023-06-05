package practie;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ch08_calculatorDAO {
	Connection conn = null;
	PreparedStatement pstmt;
	
	//드라이버
	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:tcp://localhost/~/jwbookdb";

	//connection
	public void open() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, "jwbook", "1234");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//종료시 수행할 함수
	public void close() {
		try {
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//계산한 숫자 넣는 메서드
	public void insert(ch08_calculator c) {
		open();
		String sql = "INSERT INTO CALCULATOR(num1, num2, result, op) values (?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, c.getFirst());
			pstmt.setString(2, c.getSecond());
			pstmt.setString(3, c.getResult());
			pstmt.setString(4, c.getOp());
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	//목록 불러오는 메서드
	public List<ch08_calculator> getAll() {
		open();
		List<ch08_calculator> calc = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM CALCULATOR");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ch08_calculator c = new ch08_calculator();
				c.setId(rs.getInt("id"));
				c.setFirst(rs.getString("NUM1"));
				c.setSecond(rs.getString("NUM2"));
				c.setResult(rs.getString("RESULT"));
				c.setOp(rs.getString("OP"));
				calc.add(c);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
			System.out.println("완료");
		}
		return calc;
	}
}
