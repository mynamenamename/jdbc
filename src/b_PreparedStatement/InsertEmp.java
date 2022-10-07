package b_PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertEmp {

   
   
   public static void main(String[] args) {
      try {
         // 1. 드라이버 로딩
         Class.forName("oracle.jdbc.driver.OracleDriver");
         System.out.println("드라이버 로딩 성공");
         
         // 2. 연결객체 얻어오기
         String url = "jdbc:oracle:thin:@192.168.0.55:1521:xe";
         String user = "scott";
         String pass = "tiger";
         
         Connection con = DriverManager.getConnection(url, user, pass);
         System.out.println("디비 연결 성공");
         
         
         String bonmyeong = "본명";
         int welgup = 10000;
         String jikup = "IT";
         
         // 3. SQL 문장
         // 10 번 부서의 사원 월급 10% 인상
          String sql = "Insert into emp(empno, ename, sal, job)  "
                + " values(5555, ?,?,?) ";
         
         // 월급 10000이상 삭제
         //String sql = "I";
         
         
         // 4. SQL 전송 객체
         PreparedStatement stmt = con.prepareStatement(sql);
         stmt.setString(1,bonmyeong);
         stmt.setInt(2,welgup);
         stmt.setString(3, jikup);;
         
         // 5. SQL 전송
         /*
          *    - ResultSet executeQuery() : SELECT
          *  - int executeUpdate() : INSERT/DELETE/UPDATE
          * 
          */
         
         int result = stmt.executeUpdate();
         System.out.println(result + " 행 실행");
         
         // 6. 닫기
         stmt.close();
         con.close();
         
      } catch (Exception e) {
            System.out.println("DB 실패 : " + e);
      }
   }

}