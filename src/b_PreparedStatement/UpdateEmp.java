package b_PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UpdateEmp {

   
   
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
         
         int sabun = 7698;
         String saname = "아무개";
         int salary = 15000;
        		 
         //7698 사원의 이름과 월급 변경
    
         
         // 3. SQL 문장
  
          String sql = "UPDATE emp SET ename=?, sal=? "
                + " WHERE empno=? ";
         
        
         
         // 4. SQL 전송 객체 
         PreparedStatement stmt = con.prepareStatement(sql); //이렇게 많이씀
    
         stmt.setString(1,saname);
         stmt.setInt(2, salary);
         stmt.setInt(3,sabun);
         
         // 내가한 방식 >이렇게 해도 들어는 간다..
         //stmt.setString(1,"아무개");
         //stmt.setInt(2, 15000);
         //stmt.setInt(3,7698);
         
    
         
         // 5. SQL 전송
         /*
          *    - ResultSet executeQuery() : SELECT
          *  - int executeUpdate() : INSERT/DELETE/UPDATE
          * 
          */
         
         int result = stmt.executeUpdate();   /// 이미 SQL 연결함
         System.out.println(result + " 행 실행");
         
         // 6. 닫기
         stmt.close();
         con.close();
         
      } catch (Exception e) {
            System.out.println("DB 실패 : " + e);
      }
   }

}