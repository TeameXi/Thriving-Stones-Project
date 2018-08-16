<%-- 
    Document   : ChangePassword
    Created on : Aug 16, 2018, 12:14:28 AM
    Author     : Riana
--%>

<%@page import="entity.Users"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/fonts/iconic/css/material-design-iconic-font.min.css">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styling/css/main.css">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="col-md-10">
    <%  
        Users user = (Users) request.getAttribute("user");
        String role = (String) request.getAttribute("role");
        
        String errorMsg = (String) request.getAttribute("errorMsg");
        if (errorMsg != null) {
            out.println("<div id='errorMsg' class='alert alert-danger col-md-12'><strong>"+errorMsg+"</strong></div>");
        }
    %> 
    <div class="col-lg-10 col-lg-offset-2">
    <div style="text-align: center;margin: 20px;">Change Password </a></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form id="resetPasswordForm" method="POST" class="form-horizontal" action="UpdatePasswordServlet">

                <div class="form-group">
                    <label class="col-lg-2 control-label">New Password</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-key"></i></span>

                            <input name="newPassword" placeholder="Password" id="newPassword" class="form-control"  type="password" required>
                        </div>
                    </div>
                </div>
                <input type='hidden' name='role' value="<%=role%>">
                <input type='hidden' name='id' value="<%=user.getUserId()%>">
                <input type='hidden' name='username' value="<%=user.getEmail()%>">
                <div class="form-group">
                    <label class="col-lg-2 control-label">Confirm New Password</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-key"></i></span>

                            <input name="cnfNewPassword" placeholder="Password" id="cnfNewPassword" class="form-control"  type="password" required>
                        </div>
                    </div>
                </div>
				
				

                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                        <button type="submit" class="btn btn-default">Reset Password</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
        </div>






    </body>
    <script src="https://code.jquery.com/jquery.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/styling/js/main.js"></script>
<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>
<script>

$(function () {    
    $('#resetPasswordForm').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            newPassword: {
                validators: {
                    notEmpty: {
                        message: 'Please enter password'
                    }
                }
            },
            cnfNewPassword: {
                validators: {
                    identical: {
                        field: 'newPassword',
                        message: 'The password and its confirm are not the same'
                    },
                    notEmpty: {
                        message: 'Please enter new password'
                    }
                }
            }
        }
    });
});
</script>
</html>
