<%@page import="java.util.Arrays"%>
<%@page import="java.lang.reflect.Array"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String[] data = (String[])request.getAttribute("data");
    %>
{
	"data":<%=Arrays.toString(data) %>
}