package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import framework.handler.Controller;
import model.member.MemberDAO;
import model.member.MemberDTO;

/**
 * Servlet implementation class MemberController
 */
public class MemberController implements Controller {

	@Override
	public String execute(HttpServletRequest request, String url) {
		String status = "fail";
		String path="select";
		
		if(url.equals("/member/list")) {
			int currentPageNum = request.getParameter("currentPageNum")!=null?(Integer.parseInt(request.getParameter("currentPageNum"))-1):1;
			int countDataInPage = request.getParameter("countDataInPage")!=null?Integer.parseInt(request.getParameter("countDataInPage")):10;
			int countInPageGroup = request.getParameter("countInPageGroup")!=null?Integer.parseInt(request.getParameter("countInPageGroup")):5;
			String searchColumn = request.getParameter("searchColumn")!=null?request.getParameter("searchColumn"):"";
			String searchValue = request.getParameter("searchValue")!=null?request.getParameter("searchValue"):"";
			
			MemberDAO dao = new MemberDAO();
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
		}else if(url.equals("/member/detail")) {
			String m_id=request.getParameter("m_id");
			
			MemberDAO dao = new MemberDAO();
			String data=null;
			try {
				MemberDTO member = dao.selectOne(m_id);
				data = member!=null?member.toString():null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("data", data);
			status="success";
			path="select";
		}else if(url.equals("/member/add")) {
			String m_id=request.getParameter("m_id");
			String password=request.getParameter("password");
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			int age=Integer.parseInt(request.getParameter("age"));
			String gender=request.getParameter("gender");
			
			MemberDAO dao = new MemberDAO();
			int resultCode = 0;
			try {
				resultCode = dao.insertOne(m_id,password,name,email,age,gender);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("resultCode", resultCode);
			status="success";
			path="update";
		}else if(url.equals("/member/edit")) {
			String m_id=request.getParameter("m_id");
			String password=request.getParameter("password");
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			int age=Integer.parseInt(request.getParameter("age"));
			String gender=request.getParameter("gender");
			
			MemberDAO dao = new MemberDAO();
			int resultCode = 0;
			try {
				resultCode = dao.updateOne(m_id,password,name,email,age,gender);
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
