<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String status = (String)request.getAttribute("status");
    Integer resultCode = (Integer)request.getAttribute("resultCode");
    %>
{
	"status":"<%=status%>",
	"resultCode":<%=resultCode %>
}