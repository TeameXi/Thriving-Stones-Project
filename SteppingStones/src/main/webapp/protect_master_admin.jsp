<%@page import="entity.Users"%>
<%
    Users user1 = (Users)session.getAttribute("user");
    if(user1 ==null){
        response.sendRedirect("Login.jsp");
    }else if(user1.getBranchId() != 0){
        response.sendRedirect("dashboard.jsp");
    }
%>