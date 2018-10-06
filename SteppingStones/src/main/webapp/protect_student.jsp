<%
if(session.getAttribute("user")==null){
    response.sendRedirect("Login.jsp");
    return;
}else if(session.getAttribute("role") != "student"){
    response.sendRedirect("Dashboard.jsp");
    return;
}
%>