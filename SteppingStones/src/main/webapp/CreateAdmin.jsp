<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
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
<%        ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errorMsg");
    if (errors != null) {
        for (String error : errors) {
            out.println(error);
        }
    }    
%> 

<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><a class="tab_active" href="CreateAdmin.jsp">Add Admin</a></h5></div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-7">
            <form id="createAdminForm" method="POST" class="form-horizontal" action="">
                <%  BranchDAO branchDao = new BranchDAO();
                    List<Branch> branch_lists = branchDao.retrieveBranches();          
                %>
                
                <div class="form-group">
                    <label class="col-lg-2 control-label">Branch</label>  
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
                    <label class="col-lg-2 control-label">Username</label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-account"></i></span>
                            <input id="admin_name"  name="admin_name" placeholder="Username" class="form-control"  type="text">
                        </div>
                    </div>
                </div>
				
				<div class="form-group">
                    <label class="col-lg-1 control-label">Password</label>  
                    <div class="col-lg-2">
                        <input id="generate_btn" type="button" value="Generate" onClick="generatePassword(16);"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label"></label>  
                    <div class="col-lg-7 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="zmdi zmdi-key"></i></span>

                            <input name="adminPassword" placeholder="Password" id="adminPassword" class="form-control"  type="text">
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">

<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>

<script>
var Password = {

    _pattern: /[a-zA-Z0-9_\-\+\.]/,

    _getRandomByte: function ()
    {
        if (window.crypto && window.crypto.getRandomValues)
        {
            var result = new Uint8Array(1);
            window.crypto.getRandomValues(result);
            return result[0];
        } else if (window.msCrypto && window.msCrypto.getRandomValues)
        {
            var result = new Uint8Array(1);
            window.msCrypto.getRandomValues(result);
            return result[0];
        } else
        {
            return Math.floor(Math.random() * 256);
        }
    },

    generate: function (length)
    {
        return Array.apply(null, {'length': length})
                .map(function ()
                {
                    var result;
                    while (true)
                    {
                        result = String.fromCharCode(this._getRandomByte());
                        if (this._pattern.test(result))
                        {
                            return result;
                        }
                    }
                }, this)
                .join('');
    }
};


function generatePassword(len) {
    var pwd = Password.generate(len);
    $("#adminPassword").val(pwd);
}



$(function () {   
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
                        message: 'Please enter username'
                    }
                }
            }
        }
    });
});
</script>


