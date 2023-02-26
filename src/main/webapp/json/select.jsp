<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String status = (String)request.getAttribute("status");
    String data = (String)request.getAttribute("data");
    %>
{
	"status":"<%=status%>",
	"data":<%=data %>
}