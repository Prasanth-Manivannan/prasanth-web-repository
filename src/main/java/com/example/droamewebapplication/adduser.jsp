<%@page import="com.javatpoint.dao.UserDao"%>
<%@ page import="com.example.droamewebapplication.UserDAO" %>
<jsp:useBean id="u" class="com.example.droamewebapplication.User"></jsp:useBean>
<jsp:setProperty property="*" name="u"/>

<%
    int i= UserDAO.save(u);
    if(i>0){
        response.sendRedirect("adduser-success.jsp");
    }else{
        response.sendRedirect("adduser-error.jsp");
    }
%>