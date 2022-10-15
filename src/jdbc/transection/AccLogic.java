package jdbc.transection;

//#################################################################
//	테이블명 : account
//	account_num		계좌번호		varchar2(20)
//	customer		고객명		varchar2(20)
//	amount			계좌금액		number

//고객명    계좌번호      금액
//홍길동 111-11-111 10000
//맹자 999-99-9999 5000



import java.sql.*;
public class AccLogic 
{
	// 연결 객체 생성시 필요한 변수 선언
	String url;
	String user;
	String pass;

	//===============================================
	// 드라이버를 드라이버매니저에 등록
	public AccLogic() throws Exception{
		/////////////////////////////////////////////////////////
		// 1. 드라이버를 드라이버 매니저에 등록
		Class.forName("oracle.jdbc.driver.OracleDriver");
		url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		user = "scott";
		pass = "tiger";
	}


	//====================================================
	//  보내는 계좌번호와 받는 계좌번호와 계좌금액을 넘겨받아 
	//	보내는계좌에서 계좌금액을 빼고 받는계좌에서 계좌금액을 더한다
	public int moveAccount(String sendAcc, String recvAcc, int amount) 
	{	Connection con = null;
	    PreparedStatement ps = null; 
	    PreparedStatement ps2 = null; 
	    
	
		try {
        ///////////////////////////////////////////////////////////
        //	 1. Connection 객체 생성
			con = DriverManager.getConnection(url, user, pass);
			
		//   @@ 2. Auto-commit을 해제
			con.setAutoCommit(false);
		
		//	 3. 출금계좌에서 이체금액을 뺀다
			String sqlsend = "UPDATE account SET amount=amount-? WHERE account_num=?";
			ps =con.prepareStatement(sqlsend);
			ps.setInt(1, amount);
			ps.setString(2, sendAcc);
		    int send = ps.executeUpdate();
		    if( send == 0) {
		    	return -1;
		    }
			
		//	 4. 입금계좌에 이체금액을 더한다
			String sqlRecv = "UPDATE account SET amount=amount+? WHERE account_num=?";
			ps2 = con.prepareStatement(sqlRecv);
			ps2.setInt(1, amount);
			ps2.setString(2, recvAcc);
		    int receive = ps2.executeUpdate();
			if( receive ==0) { 
				con.rollback();   //돈 뺀거 원상복귀
				return -1;
			}
			con.commit();         // 수동 커밋
			
		} catch(Exception e) {
			System.out.println("이체 실패:" + e.getMessage());
			return -1;
		//   @@ 5. commit을 전송한다
		
		//	 6. 객체 닫기
		} finally {
			// 6. 닫기
			try {con.close(); ps.close();}catch(Exception e) {}
			
			
		}
		//	 - 만일 정상적인 경우는 0을 리턴하고 도중에 잘못되었으면 트랜잭션을 롤백시키고 -1을 리턴

		    return 0;
	}

	//=======================================================
	//	보내는계좌번호와 받는계좌번호를 넘겨받아
	//  필요한 정보 검색
	public void confirmAccount(String sendAcc) throws Exception{


	}

}


