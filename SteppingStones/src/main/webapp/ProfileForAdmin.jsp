<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@include file="protect_branch_admin.jsp"%>
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
        String existingAdmin = (String) request.getAttribute("existingAdmin");
        if (existingAdmin != null) {
            out.println("<div id='creation_status' class='alert alert-danger col-md-12'>Admin : <strong>"+existingAdmin+"</strong> is already added. Try another admin again. </div>");
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
    <div style="text-align: center;margin: 20px;"><h4>Your Profile</h4></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <div class="form-horizontal">
                <%  
                    BranchDAO branchDao = new BranchDAO();
                    Branch branch = branchDao.retrieveBranchById(branch_id);
                    String branchName = branch.getName();
                           
                %>
                
             
                            
                <div class="form-group">
                    <label class="col-lg-2 control-label">Branch</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-city"></i></span>
                            <input class="form-control" value="<%=branchName%>" readonly  type="text">
                        </div>
                    </div>
                </div>
                        
                            
                            
                <div class="form-group">
                    <label class="col-lg-2 control-label">Username</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input class="form-control" value="<%=account%>" readonly  type="text">
                        </div>
                    </div>
                </div>
				
            </div>
        </div>
    </div>
</div>
</div>
</div>

<%@include file="footer.jsp"%>



