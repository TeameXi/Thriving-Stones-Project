<%-- 
    Document   : ResetPassword
    Created on : 14 Aug, 2018, 7:06:49 PM
    Author     : DEYU
--%>
<%@page import="entity.Users"%>
<%@page import="java.util.ArrayList"%>
<%
    Users user1 = (Users)session.getAttribute("user");

    if(user1 == null){
        response.sendRedirect("Login.jsp");
        return;
    }
%>
<%@include file="header.jsp"%>


<div class="col-md-10">
    <%  
        String errorMsg = (String) request.getAttribute("errorMsg");
        if (errorMsg != null) {
            out.println("<div id='errorMsg' class='alert alert-danger col-md-12'><strong>"+errorMsg+"</strong></div>");
        }
    %> 
    <div style="text-align: center;margin: 20px;"><a class="tab_active" href="CreateBranch.jsp">Reset Password </a></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form id="resetPasswordForm" method="POST" class="form-horizontal" action="ResetPasswordServlet">

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
                <input type='hidden' name='id' value="<%=user1.getUserId()%>">
                
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
</div>

<%@include file="footer.jsp"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">

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

