package framework.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlConnector {
	private static Connection conn;
	private static String schema;
	private static String url;
	private static String user;
	private static String password;
	
	public static Connection getConn() {
		url="jdbc:mysql://localhost:3306/";
		schema="jmt";
		user="scott";
		password="tiger";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn=DriverManager.getConnection(url,user,password);
			conn.setSchema(schema);
			if(conn.getSchema()==null) {
				initSchema();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(conn!=null) conn.close();
				conn=DriverManager.getConnection(url+schema,user,password);
				initTables();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return conn;
	}
	
	private static void initSchema() {
		Statement stmt=null;
		
		try {
			String sql="create database if not exists jmt default character set utf8 ";
			
			stmt = conn.createStatement();

			stmt.execute(sql);
			
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(stmt!=null) stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static void initTables() {
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql="";
		try {		
			sql="CREATE TABLE IF NOT EXISTS member(";
			sql+="m_id varchar(50) primary key,";
			sql+="password varchar(100) not null,";
			sql+="name varchar(20) not null,";
			sql+="email varchar(50) not null,";
			sql+="age int not null default(8),";
			sql+="gender varchar(2) not null default('M') check (gender in ('M','F')),";
			sql+="level int not null default(10) check (level in (1,10)),";
			sql+="reg_date datetime not null default(now())";
			sql+=")";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
			pstmt.close();
			
			sql="select * from member where m_id='admin'";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			boolean isAdminExists = rs.next();
			pstmt.close();
			
			if(!isAdminExists) {
				sql="insert into member (m_id,password,name,email,age,gender,level,reg_date)"
						+ "values('admin',md5('1234qwer'),'최고관리자','admin@admin.com',20,'M',1,now())";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.execute();
				pstmt.close();
			}

			sql="CREATE TABLE IF NOT EXISTS restaurant(";
			sql+="r_id int primary key auto_increment,";
			sql+="m_id varchar(50) not null,";
			sql+="name varchar(100) not null,";
			sql+="addr text not null default(''),";
			sql+="content text default(''),";
			sql+="img1 text default(''),";
			sql+="img2 text default(''),";
			sql+="img3 text default(''),";
			sql+="img4 text default(''),";
			sql+="img5 text default(''),";
			sql+="loc_x double not null default(0),";
			sql+="loc_y double not null default(0),";
			sql+="reg_date datetime not null default(now()),";
			sql+="FOREIGN KEY (m_id) REFERENCES member(m_id) ON UPDATE CASCADE";
			sql+=")";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
