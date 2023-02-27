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
	public int updateQuery(String sql, List<Object> ... objs) throws SQLException {
		int result=0;
		
		try {
			if(conn.isClosed() || conn==null) conn=MysqlConnector.getConn();
			pstmt = conn.prepareStatement(sql);
			
			int idx=0;
			List<Object> bindValues = objs[0];
			for(int i=0;i<bindValues.size();i++) {
				idx = (i+1);
				pstmt.setObject(idx, bindValues.get(i));
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
	public List selectQuery(String sql, List<Object> ... objs) throws SQLException{
		List list = new ArrayList<>();
		
		try {
			if(conn.isClosed() || conn==null) conn=MysqlConnector.getConn();
			pstmt = conn.prepareStatement(sql);
			
			int idx=0;
			List<Object> bindValues = objs[0];
			for(int i=0;i<bindValues.size();i++) {
				idx = (i+1);
				pstmt.setObject(idx, bindValues.get(i));
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
	 * 하나의 결과를 반환받고 싶은 select 쿼리를 템플릿화 시켜서 실행
	 * 
	 * @param sql : 사용하는 sql
	 * @param objs : PreparedStatement 객체에 바인딩하는 객체들
	 * @return object : Object를 반환
	 * @throws SQLException
	 */
	public Object selectQueryOne(String sql, List<Object> ... objs) throws SQLException{
		Object object = new Object();
		
		try {
			if(conn.isClosed() || conn==null) conn=MysqlConnector.getConn();
			pstmt = conn.prepareStatement(sql);
			
			int idx=0;
			List<Object> bindValues = objs[0];
			for(int i=0;i<bindValues.size();i++) {
				idx = (i+1);
				pstmt.setObject(idx, bindValues.get(i));
			}
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				object = rs.getObject(1);
			}
			
		}finally {
			close();
		}
		
		return object;
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
