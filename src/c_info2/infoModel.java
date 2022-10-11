package c_info2;

import java.sql.SQLException;
import java.util.ArrayList;

public interface infoModel {
	// 여기는 인터페이스
	// 사용하는 이유 : 1 상속 (infoModel model; >> 이렇게 선언해서 다른곳에서 상속받아서 사용)
	//             2 DB - 확장성(다른 DB 쓸때 이렇게 해야 잘 보임!!) 
	

	 /*
	  * 사용자 입력값을 받아서 DB에 저장하는 역할
	  */
	void insertInfo(infoVO vo) throws SQLException; //end of insertInfo()
	
	
	/*
	 * 전체 Info_tab의 레코드 검색
	 */
	ArrayList<infoVO> selectAll() throws SQLException; //end of selectAll()
	
	
	/*
	 * 전화번호를 넘겨받아서 해당하는 사람의 정보를 검색
	 */
	infoVO selectByTel(String tel) throws SQLException; 
	
	/*
	 * 전화번호를 넘겨받아서 해당하는 사람의 정보를 삭제
	 */
    int delete(String tel) throws SQLException;
 	
    /*
	 *   사용자 정보 수정
	 */
    public void modify(infoVO vo) throws SQLException;
    
    
    
    
    
}