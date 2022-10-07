package a_statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SelectEmpDept {

	public static void main(String[] args) {
		
		try { 
			//1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버로딩 성공2");	
			
			//2. 연결객체 얻어오기
			String url="jdbc:oracle:thin:@192.168.0.55:1521:xe";
			String user="scott";
			String pass="tiger";
			
			Connection con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공2");
			
			
			//3. SQL 문장 만들기 (중요!)
			// 20번 부서의 사원들의 정보- 사원, 사원명, 월급, 부서명, 근무지
			String sql = "select e.empno empno, e.ename ename, e.sal esal, d.dname dname, d.loc dloc "
					+ "from emp e, dept d "
					+ "where e.deptno= d.deptno "
					+ "and e.deptno=20 ";
			
			//4. 전송 객체 얻어오기
			Statement stmt = con.createStatement();
			
			//5. SQL 전송
			/*    - ResultSet exeucuteQuery() : SELECT 
			 *    - Int       executeUpdate() : INSERT/DELETE/UPDATE
			 */
			
			ResultSet rs =stmt.executeQuery(sql);
			while(rs.next()) {
				int empno    = rs.getInt("EMPNO");
				String ename = rs.getString("ENAME");
				int esal      = rs.getInt("ESAL");
				String dname   = rs.getString("DNAME");
				String dloc = rs.getString("DLOC");
				
				System.out.println(empno + "/" + ename + "/" + esal + "/" + dname + "/" + dloc);
			}
			
			//6. 닫기
			
			rs.close();
			stmt.close();
			con.close();
			
			
		} catch (Exception e) {
			System.out.println("DB 실패:"+ e);
		}


	}

}
