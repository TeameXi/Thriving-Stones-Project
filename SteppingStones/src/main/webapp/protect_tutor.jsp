<%
if(session.getAttribute("user")==null){
    response.sendRedirect("Login.jsp");
    return;
}else if(session.getAttribute("role") != "tutor"){
    response.sendRedirect("dashboard.jsp");
    return;
}
%>