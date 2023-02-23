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
}
