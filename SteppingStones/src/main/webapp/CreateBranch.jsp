<%-- 
    Document   : CreateBranch
    Created on : Jul 29, 2018, 2:11:01 PM
    Author     : MOH MOH SAN
--%>

<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp"%>
<style>
    #generate_btn{
        padding: 5px;
        margin-left : 50px;
        background-color:#f7a4a3;
        color:#fff;
        border-radius: 5px;
    }

    #generate_btn:hover{
        background:transparent;
        border: 1px solid #f7a4a3;
        color:#f7a4a3;
    }


</style>

<div class="col-md-10">
    <%  
        String existingBranch = (String) request.getAttribute("existingBranch");
        if (existingBranch != null) {
            out.println("<div class='alert alert-danger col-md-12'>Branch : <strong>"+existingBranch+"</strong> is already added. Try another branch again. </div>");
        }
        
        String errorMsg = (String) request.getAttribute("errorMsg");
        if (errorMsg != null) {
            out.println("<div id='errorMsg' class='alert alert-danger col-md-12'><strong>"+errorMsg+"</strong></div>");
        }
        
        String status = (String) request.getAttribute("status");
        if (status != null) {
            out.println("<div id='errorMsg' class='alert alert-success col-md-12'><strong>"+status+"</strong></div>");
        }
    %> 
    <div style="text-align: center;margin: 20px;"><a class="tab_active" href="CreateBranch.jsp">Add Branch </a></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form id="createBranchForm" method="POST" class="form-horizontal" action="CreateBranchServlet">
                <div class="form-group">
                    <label class="col-lg-2 control-label">Branch Name</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input id="branchName"  name="branchName" placeholder="Branch Name" class="form-control"  type="text">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label">Branch Address</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-home"></i></span>
                            <textarea name="branchAddress" placeholder="Branch Address" class="form-control"></textarea>
                        </div>
                    </div>
                </div>
				
                <div class="form-group">
                    <label class="col-lg-2 control-label">Phone Number</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-phone"></i></span>
                            <input name="phoneNumber" placeholder="Number" class="form-control" type="text">
                        </div>
                    </div>
                </div>
				
                <div class="form-group">
                    <label class="col-lg-2 control-label">Starting Date</label>  
                    <div class="col-lg-7 inputGroupContainer">
                  
                            <div class='input-group'>
                                <span class="input-group-addon">
                                    <i class="glyphicon glyphicon-calendar"></i>
                                </span>
                                <input type='text' class="form-control" id="startingDate" name="startingDate"/>
                                
                            </div>
                       
                    </div>
                </div>
				

                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                        <button type="submit" class="btn btn-default">Create Branch</button>
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
    $('#startingDate').datetimepicker({
        format: 'DD-MM-YYYY'
    });
    
    $('#createBranchForm').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            phoneNumber: {
                validators: {
                    between: {
                        min: 80000000,
                        max: 99999999,
                        message: 'Please enter valid number'
                    }
                }
            },
            branchName: {
                validators: {
                    notEmpty: {
                        message: 'Please enter branch name'
                    }
                }
            }
        }
    });
});
</script>

