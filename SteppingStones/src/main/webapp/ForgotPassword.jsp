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

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/fonts/iconic/css/material-design-iconic-font.min.css">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/css/main.css">
        <title>Forgot Password</title>
    </head>
    <body>

        <%
            List<String> errorList = (List<String>) request.getAttribute("error");
            String success = (String) request.getAttribute("status");
            String msg = "";
            if (errorList != null && !errorList.isEmpty()) {
                for (String a : errorList) {
                    msg = msg + " " + a;
                }
        %>
        <script> alert('<%=msg%>');</script>
        <%
            }
            if (success != null) {
        %>
        <script> alert('<%=success%>');</script>
        <%
            }

        %>


        <div style="text-align: center;margin: 20px;">Forgot Password</div>
        <div class="col-lg-10 col-lg-offset-2">
            <form id="forgotPasswordForm" class="form-horizontal" method="post" action="ForgotPasswordServlet">
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
                    <label class="col-lg-3 control-label">Email</label>  
                    <div class="col-lg-5 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                            <input name="email" placeholder="E-Mail Address" class="form-control"  type="text">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label">Phone </label>  
                    <div class="col-lg-5 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-phone"></i></span>
                            <input name="phone" class="form-control" type="text" placeholder="Phone number">
                        </div>
                    </div>
                </div>
                <!--<div class="form-group">
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
                </div>-->
                <div class="form-group">
                    <div class="col-lg-9 col-lg-offset-3">
                        <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                        <button type="submit" class="btn btn-default">Send Email</button> 
                    </div>
                </div>

            </form>
        </div>
    </body>
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>
    <script>
        $(function () {
    $('#forgotPasswordForm').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username:{
                validators:{
                    notEmpty: {
                        message: 'Please enter username'
                    }
                }
            },
            phone: {
                validators: {
                    integer: {
                        message: 'Integer Only'
                    },
                    between: {
                        min: 80000000,
                        max: 99999999,
                        message: 'Please enter valid phone number'
                    }
                }
            },
            email: {
                validators: {
                    emailAddress: {
                        message: 'Please enter valid email address'
                    }
                }
            }
//            birthDate:{
//                validators:{
//                    notEmpty: {
//                        message: 'BirthDate cannot be empty'
//                    }
//                }
//            },
//            tutorPassword:{
//                validators:{
//                    notEmpty: {
//                        message: 'Password cannot be empty'
//                    }
//                }
//            }
           
        }
    });
        });
    </script>
</html>
