package framework.database;

import java.sql.*;
import java.util.*;

public abstract class MysqlTemplate {
	static Connection conn;
	static PreparedStatement pstmt;
	static ResultSet rs;
	
	public MysqlTemplate() {
		conn=MysqlConnector.getConn();
	}
	
	/**
	 * insert, update, delete 쿼리를 템플릿화 시켜서 실행
	 * 
	 * @param sql : 사용하는 sql
	 * @param objs : PreparedStatement 객체에 바인딩하는 객체들
	 * @return result : 정수 형태의 결과값
	 * @throws SQLException
	 */
	public int updateQuery(String sql, Object ... objs) throws SQLException {
		int result=0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int idx=1;
			for(Object obj:objs) {
				pstmt.setObject(idx++, obj);
			}
			
			result = pstmt.executeUpdate();
		}finally {
			close();
		}
		
		return result;
	}
	
	/**
	 * select 쿼리를 템플릿화 시켜서 실행
	 * 
	 * @param sql : 사용하는 sql
	 * @param objs : PreparedStatement 객체에 바인딩하는 객체들
	 * @return list : ArrayList를 반환
	 * @throws SQLException
	 */
	public List selectQuery(String sql, Object ... objs) throws SQLException{
		List list = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int idx=1;
			for(Object obj:objs) {
				pstmt.setObject(idx++, obj);
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(getModel(rs));
			}
			
		}finally {
			close();
		}
		
		return list;
	}
	
	/**
	 * list에 담을 객체의 모델
	 * 
	 * @return selectQuery의 반환으로 사용되는 ArrayList에 담을 객체
	 * @throws SQLException 
	 */
	public abstract Object getModel(ResultSet rs) throws SQLException;
	
	public void close() throws SQLException {
		if(rs!=null) rs.close();
		if(pstmt!=null) pstmt.close();
		if(conn!=null) conn.close();
	}
}
