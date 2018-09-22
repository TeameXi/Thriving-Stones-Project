<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@page import="entity.Admin"%>
<%@page import="model.AdminDAO"%>
<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp"%>
<%@include file="protect_master_admin.jsp"%>
<style>/* Listing */

    * {
        box-sizing: border-box;
    }

    .toggler {
        color: #A1A1A4;
        font-size: 1.25em;
        margin-left: 8px;
        text-align: center;
        cursor: pointer;
    }
    .toggler.active {
        color: #000;
    }

    .surveys {
        list-style: none;
        margin: 0;
        padding: 0;
    }

    .survey-item {
        display: block;
        margin-top: 10px;
        padding: 20px;
        border-radius: 2px;
        background: white;
        box-shadow: 0 2px 1px rgba(170, 170, 170, 0.25);
    }

    .survey-name {
        font-weight: 400;
    }

    .list .survey-item {
        position: relative;
        padding: 0;
        font-size: 14px;
        line-height: 40px;
    }
    .list .survey-item .pull-right {
        position: absolute;
        right: 0;
        top: 0;
    }
    @media screen and (max-width: 800px) {
        .list .survey-item .stage:not(.active) {
            display: none;
        }
    }
    @media screen and (max-width: 700px) {
        .list .survey-item .survey-progress-bg {
            display: none;
        }
    }
    @media screen and (max-width: 600px) {
        .list .survey-item .pull-right {
            position: static;
            line-height: 20px;
            padding-bottom: 10px;
        }
    }
    .list .survey-country,
    .list .survey-progress,
    .list .survey-completes,
    .list .survey-end-date {
        color: #A1A1A4;
    }
    .list .survey-country,
    .list .survey-completes,
    .list .survey-end-date,
    .list .survey-name{
        margin: 0 10px;
    }
    .list .survey-country {
        margin-right: 0;
    }

    .list .survey-country,
    .list .survey-name {
        vertical-align: middle;
    }


    .survey-stage .stage {
        display: inline-block;
        vertical-align: middle;
        width: 16px;
        height: 16px;
        overflow: hidden;
        border-radius: 50%;
        padding: 0;
        margin: 0 2px;
        background: #f2f2f2;
        text-indent: -9999px;
        color: transparent;
        line-height: 16px;
    }
    .survey-stage .stage.active {
        background: #A1A1A4;
    }

    .list .list-only {
        display: auto;
    }
    .list .grid-only {
        display: none !important;
    }

    .grid .grid-only {
        display: auto;
    }
    .grid .list-only {
        display: none !important;
    }

    .grid .survey-item {
        position: relative;
        display: inline-block;
        vertical-align: top;
        height: 145px;
        width: 240px;
        margin: 10px;
    }
    @media screen and (max-width: 600px) {
        .grid .survey-item {
            display: block;
            width: auto;
            height: 150px;
            margin: 10px auto;
        }
    }
    .grid .survey-name {
        display: block;
        max-width: 80%;
        font-size: 16px;
        line-height: 20px;
    }
    .grid .survey-country {
        font-size: 11px;
        line-height: 16px;
        text-transform: uppercase;
    }
    .grid .survey-country,
    .grid .survey-end-date {
        color: #A1A1A4;
    }

    .grid .survey-progress {
        display: block;
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        width: 100%;
        padding: 20px;
        border-top: 1px solid #eee;
        font-size: 13px;
    }


    .grid .survey-progress-labels {
        position: absolute;
        right: 20px;
        top: 0;
        line-height: 55px;
    }
    @media screen and (max-width: 200px) {
        .grid .survey-progress-labels {
            right: auto;
            left: 10px;
        }
    }
    .grid .survey-progress-label {
        line-height: 21px;
        font-size: 13px;
        font-weight: 400;
    }
    .grid .survey-completes {
        line-height: 21px;
        font-size: 13px;
        vertical-align: middle;
    }
    .grid .survey-stage {
        position: absolute;
        top: 20px;
        right: 20px;
    }
    .grid .survey-stage .stage {
        display: none;
    }
    .grid .survey-stage .active {
        display: block;
    }
    .grid .survey-end-date {
        font-size: 12px;
        line-height: 20px;
    }

    .survey-progress-label a{
        vertical-align: middle;
        margin: 0 10px;
        color: #8DC63F !important;
    }

    .survey-completes a{
        vertical-align: middle;
        margin: 0 10px;
        color: red !important;
    }


    .view_more{
        color: #8DC63F !important;
        height: 20px;
    }

    .survey-progress-view a{
        vertical-align: middle;
        margin: 0 10px;
        color: orange !important;
    }
</style>

<div class="col-md-10">
    <div style="margin: 20px;"><h4>Branch Admin Lists</h4></div>
    <div class="row" id="errorMsg"></div>
    <ul class="surveys grid">
        <%
            BranchDAO branchDAO = new BranchDAO();
            AdminDAO adminDAO = new AdminDAO();
            ArrayList<Admin> admins = adminDAO.retrieveAllAdmins();
            List<Branch> branch_lists = branchDAO.retrieveBranches();       
            if(admins != null && !admins.isEmpty()){
                for(Admin admin: admins){
                    out.println("<li class='survey-item' id='admin_id_"+admin.getAdmin_id()+"'><span class='survey-country list-only'>SG</span>");
                    out.println("<span class='survey-name'><i class='zmdi zmdi-account'>&nbsp;&nbsp;</i><span id='name_"+admin.getAdmin_id()+"'>");
                    out.println(admin.getAdmin_username() + "</span></span>");
                    out.println("<span class='survey-country'><i class='zmdi zmdi-city'>&nbsp;&nbsp;</i><span id='branch_"+admin.getAdmin_id()+"'>");
                    out.println(branchDAO.retrieveBranchById(admin.getBranch_id()).getName() + "</span></span><br>");
                    out.println("<span class='survey-country grid-only'><i class='zmdi zmdi-email'>&nbsp;&nbsp;</i><span id='email_"+admin.getAdmin_id()+"'>");
                    out.println(admin.getEmail() + "</span></span><br/>");    

                

        %>
        <div class="pull-right">

            <span class="survey-progress">
                <span class="survey-progress-labels">
                    <span class="survey-progress-label">
                        <a href="#editBranchAdmin" data-toggle="modal" data-target-id="<%=admin.getAdmin_id()%>"><i class="zmdi zmdi-edit"></i></a>
                    </span>

                    <span class="survey-completes">
                        <a href="#small" onclick="deleteBranchAdmin('<%=admin.getAdmin_id()%>')" data-toggle="modal"><i class="zmdi zmdi-delete"></i></a>
                    </span>
                </span>
            </span>
            <%
                    }

                } else {
                    out.println("<div class='alert alert-warning col-md-5'>No Admin Yet! <strong> <a href='CreateAdmin.jsp'>Create One</a></strong> </div>");
                }
            %>
    </ul>
</div>
</div>
</div>

<!-- Delete Dialog -->
<div class="modal fade bs-modal-sm" id="small" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <span class="pc_title centered">Alert</span>
            </div>
            <div class="modal-body smaller-fonts centered">Are you sure you want to delete this item?</div>
            <div class="modal-footer centered">
                <a id="confirm_btn"><button type="button" class="small_button pw_button del_button autowidth">Yes, Remove</button></a>
                <button type="button" class="small_button del_button pw_button autowidth" data-dismiss="modal">Close</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- End of Delete -->


<div class="modal fade" id="editBranchAdmin" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <span class="pc_title centered">Edit Branch Admin</span>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Select Branch : </p>
                    </div>
                    <div class = "col-sm-8">
                        <select name="branch" class="form-control" id="admin_branch" >
                            <% for(Branch branch: branch_lists){
                                    out.println("<option value='"+branch.getBranchId()+"'>"+branch.getName()+"</option>");
                               }
                            %>
                        </select>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Username :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type="hidden" id="admin_id" value="" /><input type ="text" class = "form-control" id="admin_username" value =""/></p>
                    </div>
                </div><br/>

                <div class="row">
                    <div class = "col-sm-4">
                        <p class = "form-control-label">Email :</p>
                    </div>
                    <div class = "col-sm-8">
                        <p><input type ="text" class = "form-control" id="admin_email"/></p>
                    </div>
                </div><br/>
            </div>  

            <div class="modal-footer spaced-top-small centered">
                <button type="button" class="btn btn-success"  onclick="editBranchAdmin()">Save Changes</button>
            </div>
        </div>       
    </div>
</div>
<%@include file="footer.jsp"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet">


<script>
$(document).ready(function () {
    if($('#errorMsg').length){
       $('#errorMsg').fadeIn().delay(3000).fadeOut();
    }
    
    $("#editBranchAdmin").on("show.bs.modal", function (e) {
        $("#admin_username").val();
        $("#admin_email").val();
        $("#admin_id").val();
        admin_id = $(e.relatedTarget).data('target-id');
        $.ajax({
            url: 'BranchAdminServlet',
            dataType: 'JSON',
            data: {admin_id:admin_id,action:"retrieve"},
            success: function (data) {
                $("#admin_id").val(admin_id);
                $("#admin_username").val(data["name"]);
                $("#admin_email").val(data["email"]);
                $("#admin_branch").val(data["branchId"]);
            }
        });

    });
 });
 
    function editBranchAdmin() {
        admin_id = $("#admin_id").val();
        name = $("#admin_username").val();
        email = $("#admin_email").val();
        branch = $("#admin_branch").val();
      
        $('#editBranchAdmin').modal('hide');

        $.ajax({
            type: 'POST',
            url: 'BranchAdminServlet',
            dataType: 'JSON',
            data: {admin_id:admin_id,action:"update",name:name,email:email,branch:branch},
            success: function (data) {
               console.log(data);
                if (data !== -1) {
                    $("#name_"+admin_id).text(name);
                    $("#email_"+admin_id).text(email);
                   
                    $("#branch_"+admin_id).text(data["branch"]);

                    html = '<div class="alert alert-success col-md-12"><strong>Success!</strong> Update Tutor record successfully</div>';
                } else {
                    html = '<div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';
                }

                $("#errorMsg").html(html);
                $('#errorMsg').fadeIn().delay(2000).fadeOut();
            }
        });
    }
    
    function deleteBranchAdmin(admin_id) {
        $("#confirm_btn").prop('onclick', null).off('click');
        $("#confirm_btn").click(function () {
            deleteBranchAdminQueryAjax(admin_id);
        });
    }


    function deleteBranchAdminQueryAjax(admin_id) {
        $('#small').modal('hide');
       
        $.ajax({
            type: 'POST',
            url: 'BranchAdminServlet',
            dataType: 'JSON',
            data: {admin_id:admin_id,action:"delete"},
            success: function (data) {
                if (data === 1) {
                    $("#admin_id_"+admin_id).remove();
                    html = '<div class="alert alert-success col-md-12"><strong>Success!</strong> Deleted Tutor record successfully</div>';
                } else {
                    html = '<div class="alert alert-danger col-md-12"><strong>Sorry!</strong> Something went wrong</div>';
                }

                $("#errorMsg").html(html);
                $('#errorMsg').fadeIn().delay(1000).fadeOut();
            }
        });
    }

</script>