package model.restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import framework.database.MysqlTemplate;

public class RestaurantDAO {
	MysqlTemplate mysqlTemplate;
	
	public RestaurantDAO() {
		mysqlTemplate = new MysqlTemplate() {
			
			@Override
			public Object getModel(ResultSet rs) throws SQLException {
				int num = rs.getInt("num")!=0?rs.getInt("num"):0;
				
				return new RestaurantDTO(num, rs.getInt("r_id"), rs.getString("m_id"), rs.getString("name"), rs.getString("addr"), 
						rs.getString("content"), rs.getString("img1"), rs.getString("img2"), rs.getString("img3"), rs.getString("img4"), rs.getString("img5"),
						rs.getDouble("loc_x"), rs.getDouble("loc_y"), rs.getString("reg_date"));
			}
		};
	}
	
	public List<RestaurantDTO> selectAll(int currentPageNum, int countDataInPage, int countInPageGroup, String searchColumn, String searchValue) throws SQLException{
		List<RestaurantDTO> list = new ArrayList<>();
		List<Object> objs = new ArrayList<>();
		
		int startPage = currentPageNum*countDataInPage;
		
		String sql="";
		String sqlWhere=" where 1=1 ";
		if(!searchColumn.equals("") && !searchValue.equals("")) {
			 if(searchColumn.equals("name")) sqlWhere+="and rest.name like ? ";
			 if(searchColumn.equals("addr")) sqlWhere+="and rest.addr like ? ";
			 
			 objs.add("%"+searchValue+"%");
			 objs.add(startPage);
			 objs.add(countDataInPage);
		}else {	 
			 objs.add(startPage);
			 objs.add(countDataInPage);
		}
		
		sql="select * from (select rest.*, @rownum:=@rownum+1 as num from restaurant rest, (select @rownum:=0 from dual) a "+sqlWhere+" order by rest.reg_date) b order by num asc limit ?, ?";
		list = mysqlTemplate.selectQuery(sql, objs);
		
		return list;
	}
	
	public int selectAllCount(String searchColumn, String searchValue) throws SQLException{
		int count = 0;
		List<Object> objs = new ArrayList<>();
		
		String sql="";
		String sqlWhere=" where 1=1 ";
		if(!searchColumn.equals("") && !searchValue.equals("")) {
			 if(searchColumn.equals("name")) sqlWhere+="and rest.name like ? ";
			 if(searchColumn.equals("addr")) sqlWhere+="and rest.addr like ? ";
			 	
			 objs.add("%"+searchValue+"%");
		}
		
		sql="select count(*) as count from restaurant rest "+sqlWhere;
		 count = Integer.parseInt(mysqlTemplate.selectQueryOne(sql, objs).toString());
		
		return count;
	}
	
	public RestaurantDTO selectOne(String r_id) throws SQLException{
		List<RestaurantDTO> list = new ArrayList<>();
		List<Object> objs = new ArrayList<>();
		
		objs.add(r_id);
		
		String sql="select *, 1=1 as num from restaurant where r_id=?";
		list = mysqlTemplate.selectQuery(sql, objs);
		
		if(list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
	
	public int insertOne(String m_id,String name,String addr,String content,String img1,String img2,String img3,String img4,String img5,double loc_x, double loc_y) throws SQLException{
		int result=0;
		List<Object> objs = new ArrayList<>();
		
		objs.add(m_id);
		objs.add(name);
		objs.add(addr);
		objs.add(content);
		objs.add(img1);
		objs.add(img2);
		objs.add(img3);
		objs.add(img4);
		objs.add(img5);
		objs.add(loc_x);
		objs.add(loc_y);
		
		String sql="insert into restaurant(m_id,name,addr,content,img1,img2,img3,img4,img5,loc_x,loc_y,reg_date) values(?,?,?,?,?,?,?,?,?,?,?,now())";
		result = mysqlTemplate.updateQuery(sql, objs);
		
		return result;
	}
	
	public int updateOne(int r_id, String m_id, String name,String addr,String content,String img1,String img2,String img3,String img4,String img5,double loc_x, double loc_y) throws SQLException{
		int result=0;
		List<Object> objs = new ArrayList<>();

		objs.add(m_id);
		objs.add(name);
		objs.add(addr);
		objs.add(content);
		objs.add(img1);
		objs.add(img2);
		objs.add(img3);
		objs.add(img4);
		objs.add(img5);
		objs.add(loc_x);
		objs.add(loc_y);
		objs.add(r_id);
		
		String sql="update restaurant set m_id=?, name=?, addr=?, content=?, img1=?, img2=?, img3=?, img4=?, img5=?, loc_x=?, loc_y=?, reg_date=now() where r_id=?";
		result = mysqlTemplate.updateQuery(sql,objs);

		return result;
	}
	
	public int removeOne(int r_id) throws SQLException {
		int result=0;
		List<Object> objs = new ArrayList<>();
		
		objs.add(r_id);
		
		String sql="delete from restaurant where r_id = ?";
		result = mysqlTemplate.updateQuery(sql,objs);
		
		return result;
	}
}
