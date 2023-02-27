package model.member;

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
				int num = rs.getInt("num")!=0?rs.getInt("num"):0;
				
				return new MemberDTO(num, rs.getString("m_id"), rs.getString("name"), 
						rs.getString("email"), rs.getInt("age"), rs.getString("gender"), rs.getString("reg_date"),
						rs.getInt("level"));
			}
		};
	}
	
	public List<MemberDTO> selectAll(int currentPageNum, int countDataInPage, int countInPageGroup, String searchColumn, String searchValue) throws SQLException{
		List<MemberDTO> list = new ArrayList<>();
		List<Object> objs = new ArrayList<>();
		
		int startPage = currentPageNum*countDataInPage;
		
		String sql="";
		String sqlWhere=" where 1=1 ";
		if(!searchColumn.equals("") && !searchValue.equals("")) {
			objs.add(searchValue);
			objs.add(startPage);
			objs.add(countDataInPage);
			
			 if(searchColumn.equals("name")) sqlWhere+="and mem.name like ? ";
			 if(searchColumn.equals("gender")) sqlWhere+="and mem.gender like ? ";
			 	
		}else {
			objs.add(startPage);
			objs.add(countDataInPage); 
		}
		
		sql="select * from (select mem.*, @rownum:=@rownum+1 as num from member mem, (select @rownum:=0 from dual) a "+sqlWhere+" order by mem.reg_date) b order by num asc limit ?, ?";
		list = mysqlTemplate.selectQuery(sql, objs);
		
		return list;
	}
	
	public int selectAllCount(String searchColumn, String searchValue) throws SQLException{
		int count = 0;
		List<Object> objs = new ArrayList<>();
		
		String sql="";
		String sqlWhere=" where 1=1 ";
		if(!searchColumn.equals("") && !searchValue.equals("")) {
			 if(searchColumn.equals("name")) sqlWhere+="and mem.name like ? ";
			 if(searchColumn.equals("gender")) sqlWhere+="and mem.gender like ? ";
			 	
			 objs.add(searchValue);
		}
		
		sql="select count(*) as count from member mem "+sqlWhere;
		count = Integer.parseInt(mysqlTemplate.selectQueryOne(sql, objs).toString());
		
		return count;
	}
	
	public MemberDTO selectOne(String m_id) throws SQLException{
		List<MemberDTO> list = new ArrayList<>();
		List<Object> objs = new ArrayList<>();
		
		objs.add(m_id);
		
		String sql="select * from member where m_id=?";
		list = mysqlTemplate.selectQuery(sql, objs);
		
		if(list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
	
	public int insertOne(String m_id,String password,String name,String email, int age, String gender) throws SQLException{
		int result=0;
		List<Object> objs = new ArrayList<>();
		
		String sql="insert into member(m_id,password,name,email,age,gender,reg_date,level) values(?,md5(?),?,?,?,?,now(),10)";
		objs.add(m_id);
		objs.add(password);
		objs.add(name);
		objs.add(email);
		objs.add(age);
		objs.add(gender);
		
		result = mysqlTemplate.updateQuery(sql, objs);
		
		return result;
	}
	
	public int updateOne(String m_id,String password,String name,String email, int age, String gender) throws SQLException{
		int result=0;
		List<Object> objs = new ArrayList<>();
		
		String sql="";
		if(password==null) {
			objs.add(name);
			objs.add(email);
			objs.add(age);
			objs.add(gender);
			objs.add(m_id);
			
			sql="update member set name=?, email=?, age=?, gender=?, reg_date=now() where m_id=?";
		}else {
			objs.add(name);
			objs.add(password);
			objs.add(email);
			objs.add(age);
			objs.add(gender);
			objs.add(m_id);
			
			sql="update member set name=?, password=md5(?), email=?, age=?, gender=?, reg_date=now() where m_id=?";
		}
		
		result = mysqlTemplate.updateQuery(sql,objs);

		return result;
	}
}
