<%-- 
    Document   : SelectClass
    Created on : 22 Jul, 2018, 11:35:41 PM
    Author     : 
--%>

<%@page import="model.ClassDAO"%>
<%@page import="entity.Class"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Grade Creation</title>
    </head>
    
    <form action="SelectClassServlet">
        <div>
            <span class="label-input100">Classes</span>
            <select id="class" name="class" class="cd-select">
                <%
                    ArrayList<String> classes = ClassDAO.getAllClassesNames();
                    for (String tempClass : classes) {
                        out.println("<option value=\"" + tempClass + "\">" + tempClass + "</option>");
                    }
                %>
            </select> 
        </div>  
        <div class="container-login100-form-btn">
            <div class="wrap-login100-form-btn">
                <div class="login100-form-bgbtn"></div>
                <button class="login100-form-btn" type="submit" value = "insert" name = "insert">
                    Select Class
                </button>
            </div>
        </div>	
    </form>
    <%
        String status = (String) request.getAttribute("status");
        if (status != null) {
            out.println(status);
        }
    %>
</html>
