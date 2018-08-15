<%-- 
    Document   : resetPassword
    Created on : 12 Jul, 2018, 8:29:58 PM
    Author     : Zang Yu
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password Page</title>
    </head>
    <body>
        <h1>Forgot Password</h1>
        <%
            List<String> errorList = (List<String>)request.getAttribute("error");
            String success = (String) request.getAttribute("status");
            String msg = "";
            if(errorList != null && !errorList.isEmpty()){
                for(String a:errorList){
                    msg = msg+" " + a;
                }
                %>
        <script> alert('<%=msg%>');</script>
        <%
            }
            if(success != null){
               %>
               <script> alert('<%=success%>');</script>
        <%
            }
            
                %>
                
        
        <form id="register-form" role="form" class="form" method="post" action="ForgotPasswordServlet">
            <h3>Enter Your Email Below</h3>
            <input id="email" name="email" placeholder="Email address" class="form-control"  type="text" required autofocus>
            <div class="form-group">
                <label class="col-lg-3 control-label">Your Role</label>  
                <div class="col-lg-5 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-tag"></i></span>
                        <select name="type" class="form-control" >
                            <option value="" >Select Role</option>
                            <option value="admin">Admin</option>
                            <option value="tutor">Tutor</option>
                            <option value="parent">Parent</option>
                            <option value="student">Student</option>
                        </select>
                    </div>
                </div>
            </div>
            <input name="recover-submit" class="btn btn-lg btn-primary btn-block" value="Get Password" type="submit">
        </form>
    </body>
</html>
