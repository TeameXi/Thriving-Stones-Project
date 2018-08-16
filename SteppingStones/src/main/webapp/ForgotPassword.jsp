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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>


        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/css/main.css">
        <title>Forgot Password</title>
    </head>
    <body>
       
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
                
        <div class="col-lg-10 col-lg-offset-2">
            <div style="text-align: center;margin: 20px;">Forgot Password </a></h5></div>
        <form class="form-horizontal" method="post" action="ForgotPasswordServlet">
            <div class="form-group">
                            <label class="col-lg-3 control-label">Username</label>  
                            <div class="col-lg-5 inputGroupContainer">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                    <input id="username"  name="email" placeholder="Username" class="form-control"  type="text">
                                </div>
                            </div>
                        </div>
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
            <div class="form-group">
                            <div class="col-lg-9 col-lg-offset-3">
                                <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                                <button type="submit" class="btn btn-default">Get Password</button> 
                            </div>
                        </div>
            
        </form>
        </div>
    </body>
</html>
