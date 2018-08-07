<%@page import="entity.Parent"%>
<%@page import="model.ParentDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@include file="protect_parent.jsp"%>
<%@include file="header.jsp"%>

<div class="col-md-10">
    <div id="update_status"></div>
    <%               
        int parentId = 0;
        if(user != null){
            parentId = user.getUserId();
        }
        
        ParentDAO parentDao = new ParentDAO();
        Parent parent = parentDao.retrieveSpecificParentById(parentId);
        String phone = "";
        if(parent.getPhone() != 0){
            phone = ""+parent.getPhone();
        }
        
    %> 
    <div style="text-align: center;margin: 20px;"><h4>Manage Account</h4></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form class="form-horizontal" id="manageParentAccount">
                        
                <div class="form-group">
                    <label class="col-lg-2 control-label">Username</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input class="form-control" value="<%=parent.getName()%>" readonly  type="text">
                            <input type="hidden" value="<%=parent.getParentId()%>" id="parent_id"/>
                        </div>
                    </div>
                </div>
                        
                <div class="form-group">
                    <label class="col-lg-2 control-label">Nationality</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-flag"></i></span>
                            <% if (parent.getNationality().length() != 0){
                                    out.println("<input id='nationality' class='form-control' value='"+parent.getNationality()+"' readonly  type='text'>");
                               }else{
                                    out.println("<input id='nationality' class='form-control' value='' type='text'>");
                               }
                            %>
                           
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-lg-2 control-label">Company</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-city"></i></span>
                            <input id="company" placeholder="company" class="form-control" value="<%=parent.getCompany()%>" />
                        </div>
                    </div>
                </div>
                        
                <div class="form-group">
                    <label class="col-lg-2 control-label">Designation</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-case"></i></span>
                            <input id="designation" placeholder="designation" class="form-control" value="<%=parent.getDesignation()%>" />
                        </div>
                    </div>
                </div>
                            
                            
                <div class="form-group">
                    <label class="col-lg-2 control-label">Mobile </label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-phone"></i></span>
                            <input name='phone' id="phone" class="form-control" type="text" value="<%=phone%>">
                        </div>
                    </div>
                </div>
                        
                <div class="form-group">
                    <label class="col-lg-2 control-label">Email</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                            <input name='email' id="email" placeholder="E-Mail Address" class="form-control"  type="text" value="<%=parent.getEmail() %>">
                        </div>
                    </div>
                </div>
                
                        
                <div class="form-group">
                    <div class="col-lg-2 col-lg-offset-2">
                        <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
                        <button type="button" class="btn btn-default" onclick="return updateParentAccount();">Save Changes</button>
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
function updateParentAccount() {
    event.preventDefault();
    id = $("#parent_id").val();
    nationality = $("#nationality").val();
    company = $("#company").val();
    designation = $("#designation").val();
    phone = $("#phone").val();
    email = $("#email").val();

    $.ajax({
        type: 'POST',
        url: 'UpdateParentServlet',
        dataType: 'JSON',
        data: {parentID: id,nationality:nationality,company:company,designation:designation,phone:phone,email:email},
        success: function (data) {
            if (data === 1) {
                html = '<div class="alert alert-success col-md-12"><strong>Success!</strong> Updated successfully</div>';
            } else {
                html = '<div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';
            }

            $("#update_status").html(html);
            $('#update_status').fadeIn().delay(2000).fadeOut();
        }
    });
    return false;
}


$(function () {
    $('#manageParentAccount').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            phone: {
                validators: {
                    integer: {
                        message: 'Please enter valid phone number'
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
        }
    });
});
</script>




