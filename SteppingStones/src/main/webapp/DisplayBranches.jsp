<%@page import="entity.Branch"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.BranchDAO"%>
<%@include file="protect_master_admin.jsp"%>
<%@include file="header.jsp"%>
<style>
    .grid .survey-item {
        position: relative;
        display: inline-block;
        vertical-align: top;
        height: 160px;
        width: 290px;
        margin: 10px;
    }
</style>

<div class="col-md-10">
    <%
        String status = (String) request.getAttribute("status");
        if (status != null) {
            out.println("<div id='errorMsg' class='alert alert-success col-md-12'><strong>"+status+"</strong></div>");
        }
    %>
    <div style="margin: 20px;"><h3>Branch Lists</h3></div>
    <div class="row" id="errorMsg"></div>
    <ul class="surveys grid">
        <%
            BranchDAO branchDAO = new BranchDAO();
            ArrayList<Branch> branches = branchDAO.retrieveAllBranches();
            if(branches != null && !branches.isEmpty()){
                for(Branch branch: branches){
                    int id = branch.getBranchId();
                    out.println("<li class='survey-item' id='sid_" + id + "'><span class='survey-country list-only'>SG</span>");
                    out.println("<span class='survey-name'><i class='zmdi zmdi-city'>&nbsp;&nbsp;</i><span id='name_"+id+"'>");
                    out.println(branch.getName() + "</span></span><br>");
                    out.println("<span class='survey-country'><i class='zmdi zmdi-graduation-cap'>&nbsp;&nbsp;</i><span id='startDate_"+id+"'>");
                    out.println("Starting Year: " + branch.getStartDate() + "</span></span><br>");
                    out.println("<span class='survey-country grid-only'><i class='zmdi zmdi-pin'>&nbsp;&nbsp;</i><span id='address_"+id+"'>");
                    out.println(branch.getAddress() + "</span></span><br/>");    
                    out.println("<span class='survey-country grid-only'><i class='zmdi zmdi-phone'>&nbsp;&nbsp;</i><span id='phone_"+id+"'>");
                    out.println(branch.getPhone() + "</span></span><br/>");
                }
        %>
        <div class="pull-right">
        <%          
                

            } else {
                out.println("No Branches!");
            }

            %>
    </ul>
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
    if($('#errorMsg').length){
       $('#errorMsg').fadeIn().delay(3000).fadeOut();
    }
 });
</script>