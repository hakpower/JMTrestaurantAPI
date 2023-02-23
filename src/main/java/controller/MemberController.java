package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import framework.handler.Controller;
import model.MemberDAO;

/**
 * Servlet implementation class MemberController
 */
public class MemberController implements Controller {

	@Override
	public String execute(HttpServletRequest request) {

		MemberDAO dao = new MemberDAO();
		String data=null;
		try {
			data = dao.selectAll().toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("data", data);
		
		return "member/list";
	}

}
