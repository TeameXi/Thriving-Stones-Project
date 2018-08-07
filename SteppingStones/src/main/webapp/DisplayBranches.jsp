<%@page import="entity.Branch"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.BranchDAO"%>
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