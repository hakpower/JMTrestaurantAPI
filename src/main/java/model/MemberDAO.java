package model;

import java.sql.Connection;

import database.SimpleMysql;

public class MemberDAO {
	private Connection conn;
	
	public MemberDAO() {
		conn = SimpleMysql.getConn();
	}
}
