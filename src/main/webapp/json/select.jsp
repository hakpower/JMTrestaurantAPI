<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String status = (String)request.getAttribute("status");
    String data = (String)request.getAttribute("data");
    Integer totalDataCount = (Integer)request.getAttribute("totalDataCount");
    %>
{
	"status":"<%=status%>",
	"data":<%=data %>,
	"totalDataCount":<%=totalDataCount%>
}