<%@page import="java.util.Calendar"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<div class="col-lg-10">
    <div style="text-align: center;margin: 20px;"><span class="tab_active" style="font-size: 14px">Generate Financial Report</span></div><br/>

    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-10">
    <form action="GenerateFinancialReportServlet" method="post"  class="form-horizontal" autocomplete = "off">
        <div class="form-group">
            <label class="col-lg-2 control-label">Generate for : </label>  
            <div class="col-lg-3 inputGroupContainer">
                <select name="month" class="form-control">
                    <option value="1">January</option>
                    <option value="2">February</option>
                    <option value="3">March</option>
                    <option value="4">April</option>
                    <option value="5">May</option>
                    <option value="6">June</option>
                    <option value="7">July</option>
                    <option value="8">August</option>
                    <option value="9">September</option>
                    <option value="10">October</option>
                    <option value="11">November</option>
                    <option value="12">December</option>
                </select>
            </div>
            <div class="col-lg-2 inputGroupContainer">
                <select name="year" class="form-control">
                    <%
                        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                        for(int i = 2010; i<= currentYear;i++){
                            out.println("<option value = '" + i+ "'>" + i+ "</option>");
                        }
                    %>
                </select>
            </div>
                
            <button style="margin-left: 20px;" type="submit" class="btn btn1">Generate</button>
                
        </div>
       
        
    </form>
        </div>
</div>
</div>
</div>
<%@include file="footer.jsp"%>