<%
if(session.getAttribute("user")==null){
    response.sendRedirect("Login.jsp");
    return;
}else if(session.getAttribute("role") != "tutor"){
    response.sendRedirect("Dashboard.jsp");
    return;
}
%>