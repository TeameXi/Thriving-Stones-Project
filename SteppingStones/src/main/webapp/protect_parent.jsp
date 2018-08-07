<%
if(session.getAttribute("user")==null){
    response.sendRedirect("Login.jsp");
    return;
}else if(session.getAttribute("role") != "parent"){
    response.sendRedirect("dashboard.jsp");
    return;
}
%>