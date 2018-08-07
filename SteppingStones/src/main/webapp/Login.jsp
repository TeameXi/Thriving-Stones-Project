<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>


        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/css/main.css">
        <title>Login</title>
    </head>
    <body>
          <div class="limiter">
            <div class="signIn"><img style="max-width: 100%;" src="${pageContext.request.contextPath}/styling/img/signIn.png"></img></div>
          </div>
        
          <div class="container" style="margin-top:150px;">
            <div class="row">
                
                <%
                    if(request.getParameter("error") != null){
                        out.println("<div id='error' class='row' style='text-align:center;color:red;padding:10px;'><strong>Invalid username or password</strong>. Try again</div>");
                    }
                %>
                <div class="col-lg-10 col-lg-offset-2">
                    <!-- Change the "action" attribute to your back-end URL -->
                    <form id="signUpForm" method="POST" class="form-horizontal" action="LoginServlet">
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Username</label>  
                            <div class="col-lg-5 inputGroupContainer">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                    <input id="username"  name="username" placeholder="Username" class="form-control"  type="text">
                                </div>
                            </div>
                        </div>



                        <div class="form-group">
                            <label class="col-lg-3 control-label">Password</label>  
                            <div class="col-lg-5 inputGroupContainer">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                    <input id="password"  name="password" placeholder="Password" class="form-control"  type="password">
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
                                <button type="submit" class="btn btn-default">Login</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>


    </body>
</html>

<script src="https://code.jquery.com/jquery.js"></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>
<script>
$(document).ready(function() {

    
    if($('#error').length){
       $('#error').fadeIn().delay(3000).fadeOut();
    }
    
    $('#signUpForm').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                message: 'The username is not valid',
                validators: {
                    notEmpty: {
                        message: 'The username is required and cannot be empty'
                    }
                }
            },    
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required and cannot be empty'
                    }
                }
            },
            type:{
                validators: {
                    notEmpty: {
                        message: 'Select at least one role'
                    }
                } 
            }
           
        }
    });
});
</script>




