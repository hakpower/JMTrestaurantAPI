package controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import framework.handler.Controller;
import model.restaurant.RestaurantDAO;
import model.restaurant.RestaurantDTO;

public class RestaurantController implements Controller {

	@Override
	public String execute(HttpServletRequest request, String url) {
		String status = "fail";
		String path="select";
		
		if(url.equals("/restaurant/list")) {
			int currentPageNum = request.getParameter("currentPageNum")!=null?(Integer.parseInt(request.getParameter("currentPageNum"))-1):1;
			int countDataInPage = request.getParameter("countDataInPage")!=null?Integer.parseInt(request.getParameter("countDataInPage")):10;
			int countInPageGroup = request.getParameter("countInPageGroup")!=null?Integer.parseInt(request.getParameter("countInPageGroup")):5;
			String searchColumn = request.getParameter("searchColumn")!=null?request.getParameter("searchColumn"):"";
			String searchValue = request.getParameter("searchValue")!=null?request.getParameter("searchValue"):"";
			
			RestaurantDAO dao = new RestaurantDAO();
			String data=null;
			int totalDataCount=0;
			try {
				data = dao.selectAll(currentPageNum, countDataInPage, countInPageGroup, searchColumn, searchValue).toString();
				totalDataCount = dao.selectAllCount(searchColumn, searchValue);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("data", data);
			request.setAttribute("totalDataCount", totalDataCount);
			status="success";
			path="select";
		}else if(url.equals("/restaurant/detail")) {
			String m_id=request.getParameter("m_id");
			
			RestaurantDAO dao = new RestaurantDAO();
			String data=null;
			try {
				RestaurantDTO restaurant = dao.selectOne(m_id);
				data = restaurant!=null?restaurant.toString():null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("data", data);
			status="success";
			path="select";
		}else if(url.equals("/restaurant/add")) {
			String m_id=request.getParameter("m_id")!=null?request.getParameter("m_id"):null;
			String name=request.getParameter("name")!=null?request.getParameter("name"):null;
			String addr=request.getParameter("addr")!=null?request.getParameter("addr"):null;
			String content=request.getParameter("content")!=null?request.getParameter("content"):null;
			String img1=request.getParameter("img1")!=null?request.getParameter("img1"):null;
			String img2=request.getParameter("img2")!=null?request.getParameter("img2"):null;
			String img3=request.getParameter("img3")!=null?request.getParameter("img3"):null;
			String img4=request.getParameter("img4")!=null?request.getParameter("img4"):null;
			String img5=request.getParameter("img5")!=null?request.getParameter("img5"):null;
			double loc_x=request.getParameter("loc_x")!=null?Double.parseDouble(request.getParameter("img5")):0;
			double loc_y=request.getParameter("loc_y")!=null?Double.parseDouble(request.getParameter("loc_y")):0;
			
			RestaurantDAO dao = new RestaurantDAO();
			int resultCode = 0;
			try {
				resultCode = dao.insertOne(m_id,name,addr,content,img1,img2,img3,img4,img5,loc_x,loc_y);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("resultCode", resultCode);
			status="success";
			path="update";
		}else if(url.equals("/restaurant/edit")) {
			int r_id=request.getParameter("r_id")!=null?Integer.parseInt(request.getParameter("r_id")):null;
			String m_id=request.getParameter("m_id")!=null?request.getParameter("m_id"):null;
			String name=request.getParameter("name")!=null?request.getParameter("name"):null;
			String addr=request.getParameter("addr")!=null?request.getParameter("addr"):null;
			String content=request.getParameter("content")!=null?request.getParameter("content"):null;
			String img1=request.getParameter("img1")!=null?request.getParameter("img1"):null;
			String img2=request.getParameter("img2")!=null?request.getParameter("img2"):null;
			String img3=request.getParameter("img3")!=null?request.getParameter("img3"):null;
			String img4=request.getParameter("img4")!=null?request.getParameter("img4"):null;
			String img5=request.getParameter("img5")!=null?request.getParameter("img5"):null;
			double loc_x=request.getParameter("loc_x")!=null?Double.parseDouble(request.getParameter("img5")):0;
			double loc_y=request.getParameter("loc_y")!=null?Double.parseDouble(request.getParameter("loc_y")):0;
			
			RestaurantDAO dao = new RestaurantDAO();
			int resultCode = 0;
			try {
				resultCode = dao.updateOne(r_id, m_id,name,addr,content,img1,img2,img3,img4,img5,loc_x,loc_y);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("resultCode", resultCode);
			status="success";
			path="update";
		}
		
		request.setAttribute("status", status);
		
		return path;
	}

}
