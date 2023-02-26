package model;

import java.sql.*;
import java.util.*;

import framework.database.MysqlConnector;
import framework.database.MysqlTemplate;

public class MemberDAO {
	MysqlTemplate mysqlTemplate;
	
	public MemberDAO() {
		mysqlTemplate = new MysqlTemplate() {
			
			@Override
			public Object getModel(ResultSet rs) throws SQLException {
				return new MemberDTO(rs.getString("m_id"), rs.getString("name"), 
						rs.getString("email"), rs.getInt("age"), rs.getString("gender"), rs.getString("reg_date"),
						rs.getInt("level"));
			}
		};
	}
	
	public List<MemberDTO> selectAll() throws SQLException{
		List<MemberDTO> list = new ArrayList<>();
		
		String sql="select * from member";
		list = mysqlTemplate.selectQuery(sql);
		
		return list;
	}
	
	public MemberDTO selectOne(String m_id) throws SQLException{
		List<MemberDTO> list = new ArrayList<>();
		
		String sql="select * from member where m_id=?";
		list = mysqlTemplate.selectQuery(sql, m_id);
		
		if(list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
	
	public int insertOne(String m_id,String password,String name,String email, int age, String gender) throws SQLException{
		int result=0;
		
		String sql="insert into member(m_id,password,name,email,age,gender,reg_date,level) values(?,md5(?),?,?,?,?,now(),10)";
		result = mysqlTemplate.updateQuery(sql, m_id,password,name,email,age,gender);
		
		return result;
	}
	
	public int updateOne(String m_id,String password,String name,String email, int age, String gender) throws SQLException{
		int result=0;
		
		String sql="";
		if(password==null) {
			sql="update member set name=?, email=?, age=?, gender=?, reg_date=now() where m_id=?";
			result = mysqlTemplate.updateQuery(sql,name,email,age,gender,m_id);
		}else {
			sql="update member set name=?, password=md5(?), email=?, age=?, gender=?, reg_date=now() where m_id=?";
			result = mysqlTemplate.updateQuery(sql,name,password,email,age,gender,m_id);
		}
		System.out.println(sql);

		return result;
	}
}
