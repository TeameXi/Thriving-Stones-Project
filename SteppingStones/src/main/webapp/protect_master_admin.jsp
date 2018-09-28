<%@page import="entity.Users"%>
<%
    Users user1 = (Users)session.getAttribute("user");
    String role1 = (String)session.getAttribute("role");
    if(user1 ==null || !"admin".equals(role1)){
        response.sendRedirect("Login.jsp");
        return;
    }else if(user1.getBranchId() != 0 && "admin".equals(role1)){
        response.sendRedirect("Dashboard.jsp");
        return;
    }
%>