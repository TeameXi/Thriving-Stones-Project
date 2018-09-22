<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@include file="protect_master_admin.jsp"%>
<%@include file="header.jsp"%>
<div class="col-md-10">
    <%        
        String existingAdmin = (String) request.getAttribute("existingAdmin");
        if (existingAdmin != null) {
            out.println("<div id='creation_status' class='alert alert-danger col-md-12'>Admin : <strong>"+existingAdmin+"</strong> is already added. Try another admin again. </div>");
        }

        String errorMsg = (String) request.getAttribute("errorMsg");
        if (errorMsg != null) {
            out.println("<div id='errorMsg' class='alert alert-danger col-md-12'><strong>"+errorMsg+"</strong></div>");
        }
    %> 
    <div style="text-align: center;margin: 20px;"><span class="tab_active">Add Branch Admin</span></h5></div>
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <form id="createAdminForm" method="POST" class="form-horizontal" action="CreateAdminServlet">
                <%  
                    BranchDAO branchDao = new BranchDAO();
                    List<Branch> branch_lists = branchDao.retrieveBranches();          
                %>
                
                <div class="form-group">
                    <label class="col-lg-3 control-label">Branch</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-city"></i></span>
                            <select name="branch" class="form-control" >
                                <% for(Branch branch: branch_lists){
                                        out.println("<option value='"+branch.getBranchId()+"'>"+branch.getName()+"</option>");
                                   }
                                %>
                            </select>
                        </div>
                    </div>
                </div>
                        
                            
                            
                <div class="form-group">
                    <label class="col-lg-3 control-label">Admin Name</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input id="admin_name"  name="admin_name" placeholder="Username" class="form-control"  type="text">
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-lg-3 control-label">Email</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                            <input name="adminEmail" placeholder="E-Mail Address" class="form-control"  type="text">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                        <button type="submit" class="btn btn-default">Create Admin</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
</div>

<%@include file="footer.jsp"%>
<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>

<script>
$(function () {   
    if($('#creation_status').length){
       $('#creation_status').fadeIn().delay(3000).fadeOut();
    }
    
    if($('#errorMsg').length){
       $('#errorMsg').fadeIn().delay(3000).fadeOut();
    }
    
    $('#createAdminForm').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            admin_name: {
                validators: {
                    notEmpty: {
                        message: 'Please enter name'
                    }
                }
            },
            adminEmail:{
                validators: {
                    notEmpty: {
                        message: 'Please enter an email address'
                    },
                    emailAddress: {
                        message: 'Please enter a valid email address'
                    }
                }
            },
            adminPassword: {
                validators: {
                    notEmpty: {
                        message: 'Please enter password'
                    }
                }
            }
        }   
    });
});
</script>


